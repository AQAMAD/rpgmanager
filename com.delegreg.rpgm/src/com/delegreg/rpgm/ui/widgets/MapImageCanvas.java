//* Copyright (c) 2004 Chengdong Li : cdli@ccs.uky.edu
//* Modifications (c) 2009 Gregoire CASSOU ! delegreg@yahoo.com
package com.delegreg.rpgm.ui.widgets;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tracker;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.ui.widgets.ImageCanvas;
import com.delegreg.library.model.IMapAble;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.model.Location;
import com.delegreg.rpgm.ui.IImageKeys;
import com.delegreg.rpgm.ui.actions.EditItemAction;

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
public class MapImageCanvas extends ImageCanvas {

	private ArrayList<ImageHyperlink> locations;
	
	private IToolBarManager toolbar; 
	private IMenuManager menu;	
	private ScrolledForm form;
	private boolean moveLocations=false;
	private ToggleMoveAction actToggleMove;
	private MouseListener mListener=new MouseListener(){
		@Override
		public void mouseDoubleClick(MouseEvent e) {
			if (moveLocations){
				ImageHyperlink item=(ImageHyperlink) e.widget;
				int i=locations.indexOf(item);
				item.setBounds(new Rectangle(16*i,0,16,16));
				IMapAble souslieu = (IMapAble) item.getData();
				souslieu.setMapLocation(null);
				item.redraw();
			} 
		}

		@Override
		public void mouseDown(MouseEvent e) {
			if (moveLocations){
				ImageHyperlink item=(ImageHyperlink) e.widget;
				Tracker tracker = new Tracker(item.getParent(), SWT.NONE);
				Rectangle rect = item.getBounds();
				tracker.setRectangles(new Rectangle[] { rect });
				if (tracker.open()){
					item.setBounds(tracker.getRectangles()[0]);
					IMapAble souslieu = (IMapAble) item.getData();
					int realx=(int) ((item.getBounds().x-getOffsetX())/getScale());
					int realy=(int) ((item.getBounds().y-getOffsetY())/getScale());
					souslieu.setMapLocation(new Point(realx,realy));
					item.redraw();
				};				
			} 
		}

		@Override
		public void mouseUp(MouseEvent e) {}
	};

	private IHyperlinkListener hListener=new IHyperlinkListener() {
		public void linkActivated(HyperlinkEvent e) {
			EditItemAction action=new EditItemAction(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
			Location souslieu=(Location) e.widget.getData();
			if (souslieu.getDocRessource()!=null){
				action.setSelection(souslieu.getDocRessource());
				if (action.isEnabled()) {
					// Open the corresponding item
					action.run();
				} 
			}
		}
		public void linkEntered(HyperlinkEvent e) {
			hoverHyperLink=(ImageHyperlink) e.widget;
			
		}
		public void linkExited(HyperlinkEvent e) {
			hoverHyperLink=null;
		}
		
	};
	
	
	static private final Image locImg= AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.LOCATION).createImage();
	static private final Image hovLocImg= AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.HOVER_LOCATION).createImage();

	private ImageHyperlink hoverHyperLink;
	
	private PaintListener transparencyHandler=new PaintListener() {
		
		@Override
		public void paintControl(PaintEvent e) {
			ImageHyperlink me=(ImageHyperlink) e.widget;
			IMapAble souslieu = (IMapAble) me.getData();
			Rectangle rect;
			if (souslieu.getMapLocation()!=null){
				int realx=(int) (souslieu.getMapLocation().x*getScale())+(int) getOffsetX();
				int realy=(int) (souslieu.getMapLocation().y*getScale())+(int) getOffsetY();
				rect=new Rectangle(realx,realy,16,16);
			}else {
				rect=new Rectangle(0,0,16,16);
			}
			ImageData tid;
			if (hoverHyperLink==me){
				tid=me.getHoverImage().getImageData();
			} else {
				tid=me.getImage().getImageData();
			}
			Image paintimage=new Image(e.gc.getDevice(),tid);
			Image result = new Image(e.gc.getDevice(), 16, 16);
			GC gc = new GC(result);
			GC extractgc=new GC(getScreenImage());
			Image baseImage=new Image(e.gc.getDevice(), 16, 16);
			extractgc.copyArea(baseImage, rect.x, rect.y);
			extractgc.dispose();
			gc.drawImage(baseImage, 0, 0);
			gc.drawImage(paintimage, 0, 0);
			gc.dispose();
			e.gc.drawImage(result, 0, 0);
			baseImage.dispose();
			paintimage.dispose();
			result.dispose();
		}
	};
	
	private final class ToggleMoveAction extends Action {
		public ToggleMoveAction() {
			super(Messages.LocationEditorMapPage_PositionAction,Action.AS_CHECK_BOX);
			setToolTipText(Messages.LocationEditorMapPage_PositionActionTooltip);
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.LOCK));
			setChecked(true);
		}
		@Override
		public void run() {	
			moveLocations=!this.isChecked();
		}
	}
	
	/**
	 * Constructor for ScrollableCanvas.
	 * @param parent the parent of this control.
	 * @param style the style of this control.
	 */
	public MapImageCanvas(final Composite parent, int style) {
		super( parent, style);
		setAlpha(locImg);
		setAlpha(hovLocImg);
	}

	public MapImageCanvas(Composite body) {
		super(body);
	}

	public void contribute(IViewSite frm){
		IActionBars bars = frm.getActionBars();
		toolbar=bars.getToolBarManager();
		menu=bars.getMenuManager();
		contribute();
		super.contribute(frm);
	}
	public void contribute(ScrolledForm frm){
		form=frm;
		toolbar=frm.getToolBarManager();
		contribute();
		super.contribute(frm);
	}
	
	private void contribute(){
		makeActions();
		hookContextMenu();
		contributeToActionBars();
	}
	
	private void makeActions() {
		actToggleMove=new ToggleMoveAction();
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
		manager.add(actToggleMove);
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

	public void buildLocations(Object[] icons) {
		locations=new ArrayList<ImageHyperlink>();
		
		for (int i = 0; i < icons.length; i++) {
			IMapAble souslieu = (IMapAble) icons[i];
			ImageHyperlink item=new ImageHyperlink(this, SWT.NONE);
			item.setImage(locImg);
			item.setHoverImage(hovLocImg);
			item.addPaintListener(transparencyHandler);
			item.setToolTipText(souslieu.getName());
			item.setData(souslieu);
			item.setVisible(true);
			item.addMouseListener(mListener);
			item.addHyperlinkListener(hListener);
			locations.add(item);
		}
	}
	
	public void positionLocations(){
		if (locations==null){return;}
		Rectangle rect;
		//get canvas scrolloffset and zoomfactor
		//now create controls for each sublocation and check if we have a mapping
		for (int i = 0; i < locations.size(); i++) {
			ImageHyperlink item=locations.get(i);
			IMapAble souslieu = (IMapAble) item.getData();
			if (souslieu.getMapLocation()!=null){
				int realx=(int) (souslieu.getMapLocation().x*getScale())+(int) getOffsetX();
				int realy=(int) (souslieu.getMapLocation().y*getScale())+(int) getOffsetY();
				rect=new Rectangle(realx,realy,16,16);
			}else {
				rect=new Rectangle(16*i,0,16,16);
			}
			item.setBounds(rect);
			item.redraw();
		}
	}

	
	@Override
	public void syncScrollBars() {
		super.syncScrollBars();
		positionLocations();
	}

	@Override
	public Image loadImage(String filename) {
		Image result= super.loadImage(filename);
		positionLocations();
		return result;
	}

	/**
	 * Dispose the garbage here
	 */
	public void dispose() {
		super.dispose();
	}

	
	private void setAlpha(Image img){
		ImageData tid=img.getImageData();
		for (int j = 0; j < 16; j++) {
			for (int k = 0; k < 16; k++) {
				if (tid.getPixel(0, 0)==0){
					tid.setAlpha(0, 0, 255);
				}
			}
		}
	}

}
