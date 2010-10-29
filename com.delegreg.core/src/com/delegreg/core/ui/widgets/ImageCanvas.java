//* Copyright (c) 2004 Chengdong Li : cdli@ccs.uky.edu
//* Modifications (c) 2009 Gregoire CASSOU ! delegreg@yahoo.com
package com.delegreg.core.ui.widgets;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.Activator;
import com.delegreg.core.ui.IImageKeys;
import com.delegreg.core.util.SWT2Dutil;

/**
 * A scrollable image canvas that extends org.eclipse.swt.graphics.Canvas.
 * <p/>
 * It requires Eclipse (version >= 2.1) on Win32/win32; Linux/gtk; MacOSX/carbon.
 * <p/>
 * This implementation using the pure SWT, no UI AWT package is used. For 
 * convenience, I put everything into one class. However, the best way to
 * implement this is to use inheritance to create multiple hierarchies.
 * 
 * @author Chengdong Li: cli4@uky.edu
 */
public class ImageCanvas extends Canvas {
	/* zooming rates in x and y direction are equal.*/
	public static final float ZOOM_RATE = 1.1f; /* zoomin rate */
	private Image sourceImage; /* original image */
	private Image screenImage; /* screen image */
	private AffineTransform transform = new AffineTransform();
	private IAction actZoomOut;
	private IAction actZoomIn;	
	private Combo scaleCombo;
	private ControlContribution scaleComboContainer;
	private NumberFormat df=new DecimalFormat("0.00");
	
	private IToolBarManager toolbar; 
	private IMenuManager menu;
	
	private String currentDir=""; /* remembering file open directory */ //$NON-NLS-1$
	public ImageCanvas(final Composite parent) {
		this(parent, SWT.NULL);
	}

	/**
	 * Constructor for ScrollableCanvas.
	 * @param parent the parent of this control.
	 * @param style the style of this control.
	 */
	public ImageCanvas(final Composite parent, int style) {
		super( parent, style|SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL
				            | SWT.NO_BACKGROUND | SWT.INHERIT_FORCE );
		addControlListener(new ControlAdapter() { /* resize listener. */
			public void controlResized(ControlEvent event) {
				syncScrollBars();
			}
		});
		addPaintListener(new PaintListener() { /* paint listener. */
			public void paintControl(final PaintEvent event) {
				paint(event.gc);
			}
		});
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {}
			
			@Override
			public void mouseDown(MouseEvent e) {
				setFocus();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});
		initScrollBars();
		
	}

	private final class ZoomOutAction extends Action {
		public ZoomOutAction() {
			setText("Zoom out");
			setToolTipText("Zoom out");
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.ZOOMOUT));
		}
		@Override
		public void run() {	
			zoomOut();
		}
	}
	private final class ZoomInAction extends Action {
		public ZoomInAction() {
			setText("Zoom in");
			setToolTipText("Zoom in");
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.ZOOMIN));
		}
		@Override
		public void run() {	
			zoomIn();
		}
	}	
	
	public void contribute(IViewSite frm){
		IActionBars bars = frm.getActionBars();
		toolbar=bars.getToolBarManager();
		menu=bars.getMenuManager();
		makeActions();
		hookContextMenu();
		contributeToActionBars();
	}
	public void contribute(ScrolledForm frm){
		toolbar=frm.getToolBarManager();
		makeActions();
		hookContextMenu();
		contributeToActionBars();
	}
	
	private void makeActions() {
		actZoomOut = new ZoomOutAction();			
		scaleComboContainer=new ControlContribution("Custom") { //$NON-NLS-1$
		    protected Control createControl(Composite parent) {
				scaleCombo = new Combo(parent, SWT.NONE);
				scaleCombo.add(df.format(10) + "%" ); //$NON-NLS-1$
				scaleCombo.add(df.format(25) + "%" ); //$NON-NLS-1$
				scaleCombo.add(df.format(33) + "%" ); //$NON-NLS-1$
				scaleCombo.add(df.format(50) + "%" ); //$NON-NLS-1$
				scaleCombo.add(df.format(66) + "%" ); //$NON-NLS-1$
				scaleCombo.add(df.format(75) + "%" ); //$NON-NLS-1$
				scaleCombo.add(df.format(100) + "%" ); //$NON-NLS-1$
				scaleCombo.add(df.format(125) + "%" ); //$NON-NLS-1$
				scaleCombo.add(df.format(133) + "%" ); //$NON-NLS-1$
				scaleCombo.add(df.format(150) + "%" ); //$NON-NLS-1$
				scaleCombo.select(6);
				scaleCombo.addSelectionListener(new SelectionListener(){
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						float ratio;
						try {
							ratio = df.parse(((Combo)e.widget).getText()).floatValue();
						} catch (ParseException e1) {
							ratio=1f;
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (!(ratio<=0 || ratio >3600)){
							float scale=ratio/100;
							if (scale!=getScale()){
								scale(scale);
							}
						}
					}
					@Override
					public void widgetSelected(SelectionEvent e) {
						float select;
						try {
							select = df.parse(((Combo)e.widget).getText()).floatValue();
						} catch (ParseException e1) {
							select=1f;
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						float scale=select/100;
						if (scale!=getScale() && scale>0){
							scale(scale);
						}
					}					
				});
				scaleCombo.pack(true);
				return scaleCombo;
		    }
		};
		actZoomIn = new ZoomInAction();			
	}


	private void contributeToActionBars() {
		fillLocalPullDown(menu);
		fillLocalToolBar(toolbar);
	}
	
	private void fillLocalPullDown(IContributionManager manager) {
		fillMenu( manager,true);
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		fillMenu(manager,false);
	}

	private void fillContextMenu(IMenuManager manager) {
		fillMenu(manager,true);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}	

	private void fillMenu(IContributionManager manager,boolean noCombos) {
		if (manager==null){return;}
		if (manager.getItems().length>0){
			manager.add(new Separator());
		}
		manager.add(actZoomOut);
		if (!noCombos){
			manager.add(scaleComboContainer);
		}
		manager.add(actZoomIn);
		manager.update(true);
	}		

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(this);
		this.setMenu(menu);
		//getSite().registerContextMenu(menuMgr, );
	}	
	/**
	 * Dispose the garbage here
	 */
	public void dispose() {
		if (sourceImage != null && !sourceImage.isDisposed()) {
			sourceImage.dispose();
		}
		if (screenImage != null && !screenImage.isDisposed()) {
			screenImage.dispose();
		}
	}

	/* Paint function */
	private void paint(GC gc) {
		Rectangle clientRect = getClientArea(); /* Canvas' painting area */
		if (sourceImage != null) {
			Rectangle imageRect =
				SWT2Dutil.inverseTransformRect(transform, clientRect);
			int gap = 2; /* find a better start point to render */
			imageRect.x -= gap; imageRect.y -= gap;
			imageRect.width += 2 * gap; imageRect.height += 2 * gap;

			Rectangle imageBound = sourceImage.getBounds();
			imageRect = imageRect.intersection(imageBound);
			Rectangle destRect = SWT2Dutil.transformRect(transform, imageRect);

			if (screenImage != null)
				screenImage.dispose();
			screenImage =
				new Image(getDisplay(), clientRect.width, clientRect.height);
			GC newGC = new GC(screenImage);
			newGC.setClipping(clientRect);
			newGC.drawImage(
				sourceImage,
				imageRect.x,
				imageRect.y,
				imageRect.width,
				imageRect.height,
				destRect.x,
				destRect.y,
				destRect.width,
				destRect.height);
			newGC.dispose();

			gc.drawImage(screenImage, 0, 0);
		} else {
			gc.setClipping(clientRect);
			gc.fillRectangle(clientRect);
			initScrollBars();
		}
	}

	/* Initalize the scrollbar and register listeners. */
	private void initScrollBars() {
		ScrollBar horizontal = getHorizontalBar();
		horizontal.setEnabled(false);
		horizontal.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				scrollHorizontally((ScrollBar) event.widget);
			}
		});
		ScrollBar vertical = getVerticalBar();
		vertical.setEnabled(false);
		vertical.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				scrollVertically((ScrollBar) event.widget);
			}
		});
	}

	/* Scroll horizontally */
	private void scrollHorizontally(ScrollBar scrollBar) {
		if (sourceImage == null)
			return;

		AffineTransform af = transform;
		double tx = af.getTranslateX();
		double select = -scrollBar.getSelection();
		af.preConcatenate(AffineTransform.getTranslateInstance(select - tx, 0));
		transform = af;
		syncScrollBars();
	}

	/* Scroll vertically */
	private void scrollVertically(ScrollBar scrollBar) {
		if (sourceImage == null)
			return;

		AffineTransform af = transform;
		double ty = af.getTranslateY();
		double select = -scrollBar.getSelection();
		af.preConcatenate(AffineTransform.getTranslateInstance(0, select - ty));
		transform = af;
		syncScrollBars();
	}

	/**
	 * Source image getter.
	 * @return sourceImage.
	 */
	public Image getSourceImage() {
		return sourceImage;
	}

	/**
	 * Synchronize the scrollbar with the image. If the transform is out
	 * of range, it will correct it. This function considers only following
	 * factors :<b> transform, image size, client area</b>.
	 */
	public void syncScrollBars() {
		if (sourceImage == null) {
			redraw();
			return;
		}

		AffineTransform af = transform;
		double sx = af.getScaleX(), sy = af.getScaleY();
		double tx = af.getTranslateX(), ty = af.getTranslateY();
		if (tx > 0) tx = 0;
		if (ty > 0) ty = 0;

		ScrollBar horizontal = getHorizontalBar();
		horizontal.setIncrement((int) (getClientArea().width / 100));
		horizontal.setPageIncrement(getClientArea().width);
		Rectangle imageBound = sourceImage.getBounds();
		int cw = getClientArea().width, ch = getClientArea().height;
		if (imageBound.width * sx > cw) { /* image is wider than client area */
			horizontal.setMaximum((int) (imageBound.width * sx));
			horizontal.setEnabled(true);
			if (((int) - tx) > horizontal.getMaximum() - cw)
				tx = -horizontal.getMaximum() + cw;
		} else { /* image is narrower than client area */
			horizontal.setEnabled(false);
			tx = (cw - imageBound.width * sx) / 2; //center if too small.
		}
		horizontal.setSelection((int) (-tx));
		horizontal.setThumb((int) (getClientArea().width));

		ScrollBar vertical = getVerticalBar();
		vertical.setIncrement((int) (getClientArea().height / 100));
		vertical.setPageIncrement((int) (getClientArea().height));
		if (imageBound.height * sy > ch) { /* image is higher than client area */
			vertical.setMaximum((int) (imageBound.height * sy));
			vertical.setEnabled(true);
			if (((int) - ty) > vertical.getMaximum() - ch)
				ty = -vertical.getMaximum() + ch;
		} else { /* image is less higher than client area */
			vertical.setEnabled(false);
			ty = (ch - imageBound.height * sy) / 2; //center if too small.
		}
		vertical.setSelection((int) (-ty));
		vertical.setThumb((int) (getClientArea().height));

		/* update transform. */
		af = AffineTransform.getScaleInstance(sx, sy);
		af.preConcatenate(AffineTransform.getTranslateInstance(tx, ty));
		transform = af;

		redraw();
		
	}

	/**
	 * Reload image from a file
	 * @param filename image file
	 * @return swt image created from image file
	 */
	public Image loadImage(String filename) {
		if (sourceImage != null && !sourceImage.isDisposed()) {
			sourceImage.dispose();
			sourceImage = null;
		}
		try {
			sourceImage = new Image(getDisplay(), filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showOriginal();
		return sourceImage;
	}

	/**
	 * load image from memory
	 * @param filename image file
	 * @return swt image created from image file
	 * @throws Exception 
	 */
	public Image loadImage(BufferedImage awtimage) throws Exception {
		if (sourceImage != null && !sourceImage.isDisposed()) {
			sourceImage.dispose();
			sourceImage = null;
		}
		sourceImage = makeSWTImage(getDisplay(), awtimage);
		showOriginal();
		return sourceImage;
	}

	/**
	 * load image from awt buffered image
	 * @param filename image file
	 * @return swt image created from image file
	 */
	public Image loadImage(Image newimg) {
		if (sourceImage != null && !sourceImage.isDisposed()) {
			sourceImage.dispose();
			sourceImage = null;
		}
		
		sourceImage = newimg;
		
		showOriginal();
		return sourceImage;
	}

	/**
	 * Call back funtion of button "open". Will open a file dialog, and choose
	 * the image file. It supports image formats supported by Eclipse.
	 */
	public void onFileOpen() {
		FileDialog fileChooser = new FileDialog(getShell(), SWT.OPEN);
		fileChooser.setText("Open image file"); //$NON-NLS-1$
		fileChooser.setFilterPath(currentDir);
		fileChooser.setFilterExtensions(
			new String[] { "*.gif; *.jpg; *.png; *.ico; *.bmp" }); //$NON-NLS-1$
		fileChooser.setFilterNames(
			new String[] { "SWT image" + " (gif, jpeg, png, ico, bmp)" }); //$NON-NLS-1$ //$NON-NLS-2$
		String filename = fileChooser.open();
		if (filename != null){
			loadImage(filename);
			currentDir = fileChooser.getFilterPath();
		}
	}

	/**
	 * Get the image data. (for future use only)
	 * @return image data of canvas
	 */
	public ImageData getImageData() {
		return sourceImage.getImageData();
	}

	public Image getScreenImage() {
		return screenImage;
	}

	
	/**
	 * Reset the image data and update the image
	 * @param data image data to be set
	 */
	public void setImageData(ImageData data) {
		if (sourceImage != null)
			sourceImage.dispose();
		if (data != null)
			sourceImage = new Image(getDisplay(), data);
		syncScrollBars();
	}

	/**
	 * Fit the image onto the canvas
	 */
	public void fitCanvas() {
		if (sourceImage == null)
			return;
		Rectangle imageBound = sourceImage.getBounds();
		Rectangle destRect = getClientArea();
		double sx = (double) destRect.width / (double) imageBound.width;
		double sy = (double) destRect.height / (double) imageBound.height;
		double s = Math.min(sx, sy);
		double dx = 0.5 * destRect.width;
		double dy = 0.5 * destRect.height;
		centerZoom(dx, dy, s, new AffineTransform());
	}

	/**
	 * Show the image with the original size
	 */
	public void showOriginal() {
		if (sourceImage == null)
			return;
		transform = new AffineTransform();
		syncScrollBars();
	}

	/**
	 * Perform a zooming operation centered on the given point
	 * (dx, dy) and using the given scale factor. 
	 * The given AffineTransform instance is preconcatenated.
	 * @param dx center x
	 * @param dy center y
	 * @param scale zoom rate
	 * @param af original affinetransform
	 */
	public void centerZoom(
		double dx,
		double dy,
		double scale,
		AffineTransform af) {
		af.preConcatenate(AffineTransform.getTranslateInstance(-dx, -dy));
		af.preConcatenate(AffineTransform.getScaleInstance(scale, scale));
		af.preConcatenate(AffineTransform.getTranslateInstance(dx, dy));
		transform = af;
		syncScrollBars();
	}

	/**
	 * Zoom in around the center of client Area.
	 */
	public void zoomIn() {
		if (sourceImage == null)
			return;
		Rectangle rect = getClientArea();
		int w = rect.width, h = rect.height;
		double dx = ((double) w) / 2;
		double dy = ((double) h) / 2;
		centerZoom(dx, dy, ZOOM_RATE, transform);
		scaleCombo.setText( df.format(getScale()*100) + "%" ); //$NON-NLS-1$
	}

	/**
	 * Zoom out around the center of client Area.
	 */
	public void zoomOut() {
		if (sourceImage == null)
			return;
		Rectangle rect = getClientArea();
		int w = rect.width, h = rect.height;
		double dx = ((double) w) / 2;
		double dy = ((double) h) / 2;
		centerZoom(dx, dy, 1/ZOOM_RATE, transform);
		scaleCombo.setText( df.format(getScale()*100) + "%" ); //$NON-NLS-1$
	}

	/**
	 * Zoom out around the center of client Area.
	 */
	public void scale(float scale) {
		if (sourceImage == null)
			return;
		showOriginal();
		Rectangle rect = getClientArea();
		int w = rect.width, h = rect.height;
		double dx = ((double) w) / 2;
		double dy = ((double) h) / 2;
		centerZoom(dx, dy, scale, transform);
		scaleCombo.setText( df.format(scale*100) + "%" ); //$NON-NLS-1$
	}


	
	
	private static Image makeSWTImage(Display display, BufferedImage bufferedImage)
	    throws Exception
	  {
		int width;
		int height;
		width=bufferedImage.getWidth();
		height=bufferedImage.getHeight();
	    int[] data =
	      ((DataBufferInt)bufferedImage.getData().getDataBuffer())
	      .getData();
	    ImageData imageData =
	      new ImageData(width, height, 24,
	        new PaletteData(0xFF0000, 0x00FF00, 0x0000FF));
	    imageData.setPixels(0, 0, data.length, data, 0);
	    Image swtImage = new Image(display, imageData);
		return swtImage;
	  }	

	public double getScale(){
		return transform.getScaleX();
	}
	public double getOffsetX(){
		return transform.getTranslateX();
	}
	public double getOffsetY(){
		return transform.getTranslateY();
	}
	
}
