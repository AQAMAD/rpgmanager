package com.delegreg.library.ui.views;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;

import com.delegreg.core.ui.widgets.ImageCanvas;
import com.delegreg.library.Activator;
import com.delegreg.library.Messages;
import com.delegreg.library.model.IDocOrRessource;
import com.delegreg.library.model.PDFDocRessource;
import com.delegreg.library.model.PDFDocument;
import com.delegreg.library.ui.IImageKeys;
import com.delegreg.library.util.LibraryPreferenceStore;
import com.delegreg.library.util.PdfDecoderFactory;


public class PDFDocView extends ViewPart {

	private final class FirstPageAction extends Action {
		public FirstPageAction() {
			setText("First page");
			setToolTipText("Go to the first page in the file");
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.FIRST));
		}
		@Override
		public void run() {	
			firstPage();
		}
	}
	private final class LastPageAction extends Action {
		public LastPageAction() {
			setText("Last page");
			setToolTipText("Go to the last page of the file");
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.LAST));
		}
		@Override
		public void run() {	
			lastPage();
		}
	}
	private final class PreviousPageAction extends Action {
		public PreviousPageAction() {
			setText("Previous page");
			setToolTipText("Go to the previous page");
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.PREVIOUS));
		}
		@Override
		public void run() {	
			movePage(-1);
		}
	}
	private final class NextPageAction extends Action {
		public NextPageAction() {
			setText("Next page");
			setToolTipText("Go to the next page");
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.NEXT));
		}
		@Override
		public void run() {	
			movePage(1);
		}
	}
	private final class FForwardAction extends Action {
		public FForwardAction() {
			setText("Forward 10 pages");
			setToolTipText("Skip the next 10 pages");
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.FFORWARD));
		}
		@Override
		public void run() {	
			movePage(10);
		}
	}
	private final class FBackAction extends Action {
		public FBackAction() {
			setText("Backward 10 pages");
			setToolTipText("Go backward 10 pages");
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.FBACKWARD));
		}
		@Override
		public void run() {	
			movePage(-10);
		}
	}

	private final class IndexAction extends Action {
		public IndexAction() {
			setText("Add an index to this PDF page");
			setToolTipText("Add index to this page");
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.INDEX));
		}
		@Override
		public void run() {	
			InputDialog id=new InputDialog(getSite().getShell(),"New PDF index" , "Type the name of the new index", "", //$NON-NLS-3$
					new IInputValidator(){
				@Override
				public String isValid(String newText) {
					if (newText==null){
						return "Invalid name for the index";
					}else{
						return null;
					}
				}

			});
			if (id.open()==InputDialog.OK){
				PDFDocRessource dr=new PDFDocRessource(id.getValue());
				dr.setPageNumber(currentPage);
				if (doc instanceof PDFDocument){
					((PDFDocument)doc).add(dr);
					((PDFDocument)doc).fireChanged();
				}else if (doc instanceof PDFDocRessource){
					((PDFDocRessource)doc).add(dr);
					((PDFDocRessource)doc).fireChanged();
				}
			}
		}
	}


	private final class SaveImageAction extends Action {
		public SaveImageAction() {
			setText("Save page as image");
			setToolTipText("Save the page as an image file");
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.IMAGE));
		}
		@Override
		public void run() {	
			FileDialog dialog=new FileDialog(  getViewSite().getShell(),SWT.SAVE);
			dialog.setFilterExtensions(new String[]{"*.png"}); //$NON-NLS-1$
			dialog.setFilterNames(new String[]{"Image Files"}); //$NON-NLS-1$
			String fileName=dialog.open();
			SavePageToImage(fileName,currentPage);
		}
	}	

	private final class SaveAllAsImageAction extends Action {
		public SaveAllAsImageAction() {
			setText("Save pages as image folder");
			setToolTipText("Save all pages in the current document as image files in a folder.");
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.IMAGES));
		}
		@Override
		public void run() {	
			DirectoryDialog dialog=new DirectoryDialog(  getViewSite().getShell(),SWT.SAVE);
			String folderName=dialog.open();
			SaveAllPagesToFolder(folderName);
		}
	}	

	/**flag to stop multiple access*/
	boolean isDecoding=false;	
	public static final String ID = "com.delegreg.library.views.pdf"; //$NON-NLS-1$

	/**actual JPedal library*/
	private PdfDecoder decodePDF=new PdfDecoder();

	/** Current page number (first page is 1) */
	private int currentPage = 1;

	private boolean mustLoad=false;
	
	private String fileName = ""; //$NON-NLS-1$
	private IDocOrRessource doc ; //$NON-NLS-1$
	private FormToolkit toolkit;
	private ScrolledForm form;
	private IAction actStart;
	private IAction actFBack;
	private IAction actBack;
	private IAction actForward;
	private IAction actFForward;
	private IAction actEnd;
	private IAction actIndex;
	private IAction actSaveAsImage;
	private IAction actSaveAllAsImages;
	private ImageCanvas canvas; 	
	private Combo pageCombo;
	private ControlContribution pageComboContainer;
	private float resolution=1f;

	/**
	 * @wbp.parser.constructor
	 */
	public PDFDocView() {
		super();
		PreferenceStore ps=LibraryPreferenceStore.getInstance();
		try {
			ps.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public PDFDocView(String fileName) {
		this();
		this.openPDF(fileName);
	}

	private void SavePageToImage(String fileName,int page){
		BufferedImage img;
		try {
			img = decodePDF.getPageAsImage(page);
			File nw=new File(fileName);
			javax.imageio.ImageIO.write(img, "png", nw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void SaveAllPagesToFolder(String folderName){
		for (int i = 1; i <= decodePDF.getPageCount(); i++) {
			String fileName=folderName + File.separatorChar + "Pg" + i + ".png";
			SavePageToImage(fileName, i);
		}
	}
	
	@Override
	public final void createPartControl(final Composite parent) {
		try{

			toolkit = new FormToolkit(parent.getDisplay()); 
			form = toolkit.createScrolledForm(parent); 
			toolkit.decorateFormHeading(form.getForm());

			form.getBody().setLayout(new GridLayout(2,false)); 

			// Create the canvas for drawing  
			canvas = new ImageCanvas( form.getBody());  
			canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

			makeActions();
			hookContextMenu();
			contributeToActionBars();

			canvas.contribute(getViewSite());

			if (mustLoad){
				String s=fileName;
				fileName="";
				openPDF(s);
				gotoPage(currentPage);
				mustLoad=false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}catch(Error e){
			e.printStackTrace();
		}

	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}



	private void fillLocalPullDown(IContributionManager manager) {
		fillMenu( manager,true);
	}

	private void fillContextMenu(IMenuManager manager) {
		fillMenu(manager,true);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		fillMenu(manager,false);
	}



	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(canvas);
		canvas.setMenu(menu);
		//getSite().registerContextMenu(menuMgr, );
	}

	private void makeActions() {
		actFBack = new FBackAction();			
		actFForward = new FForwardAction();			
		actEnd = new LastPageAction();			
		actStart = new FirstPageAction();			
		actForward = new NextPageAction();			
		actBack = new PreviousPageAction();			
		pageComboContainer=new ControlContribution("Custom") { //$NON-NLS-1$
			protected Control createControl(Composite parent) {
				pageCombo = new Combo(parent, SWT.NONE);
				pageCombo.add("999"); //$NON-NLS-1$
				pageCombo.addSelectionListener(new SelectionListener(){
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						//test the page being entered
						int page=((Combo)e.widget).getSelectionIndex() + 1 ;
						if (page==0){
							//effectively called when enter is pressed
							//calculate page from text entered
							page=Integer.parseInt( ((Combo)e.widget).getText() );
							if (!(page==0 || page >decodePDF.getPageCount())){
								if (page!=currentPage){
									currentPage=page;
									decodePage();
									return;
								}
							}
							System.out.println("something was typed that is not a page"); //$NON-NLS-1$
						}
					}
					@Override
					public void widgetSelected(SelectionEvent e) {
						int page=((Combo)e.widget).getSelectionIndex() + 1 ;
						if (page!=currentPage){
							currentPage=page;
							decodePage();
						}
					}					
				}
				);
				pageCombo.pack(true);
				return pageCombo;
			}
		};
		actIndex=new IndexAction();
		actSaveAsImage=new SaveImageAction();
		actSaveAllAsImages=new SaveAllAsImageAction();
	}

	/*
	 * fill any object with action buttons
	 */
	private void fillMenu(IContributionManager manager,boolean noCombos) {

		manager.add(actIndex);
		manager.add(actSaveAsImage);
		manager.add(actSaveAllAsImages);
		manager.add(new Separator());
		manager.add(actStart);
		manager.add(actFBack);
		manager.add(actBack);
		if (!noCombos){
			manager.add(pageComboContainer);			
		}
		manager.add(actForward);
		manager.add(actFForward);
		manager.add(actEnd);
		manager.update(true);

	}	

	@Override
	public final void setFocus() {
		canvas.setFocus();
	}

	public final void dispose() {
		toolkit.dispose(); 
		super.dispose();
	}

	public final void openPDF(final PDFDocument doc) {
		this.doc=doc;
		openPDF(doc.getFileName());
	}

	public final void openPDF(final PDFDocRessource doc) {
		this.doc=doc;
		openPDF(doc.getPdfDocument());
	}

	public final void openPDF(final String fileName) {

		if (fileName.equals(this.fileName)){
			return;
		}

		String shortFileName;

		this.fileName=fileName;

		boolean isEnabled=true;

		File checkFile;
		try {
			checkFile=new File(fileName);
			shortFileName=checkFile.getName();
		} catch (Exception e) {
			checkFile=new File(""); //$NON-NLS-1$
			shortFileName=""; //$NON-NLS-1$
		}

		if(!checkFile.exists()){
			System.out.println("file missing : " + checkFile.getName()); //$NON-NLS-1$
			isEnabled=false;
			return;
		} else {
			isEnabled=true;
			setPartName("PDF Viewer" + " : " + shortFileName);
		}

		try {
			decodePDF=PdfDecoderFactory.getInstance(fileName);
		} catch (PdfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//pdf file was opened
		//reset page combo
		pageCombo.removeAll();
		for (int i = 1; i <= decodePDF.getPageCount(); i++) {
			pageCombo.add(Integer.toString(i));
		}
		pageCombo.select(0);
		pageCombo.pack(true);

		boolean fileCanBeOpened= false;
		if (decodePDF.isEncrypted() && !decodePDF.isFileViewable()) {

			InputDialog input = new InputDialog(getSite().getShell(),"Password needed","Please enter the password for this file",
					"", //$NON-NLS-1$
					null); 
			input.open();

			String password = input.getValue();

			/** try and reopen with new password */
			if (password != null) {
				try {
					decodePDF.setEncryptionPassword(password);
				} catch (PdfException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (decodePDF.isFileViewable()) {
					fileCanBeOpened = true;
				} else {
					fileCanBeOpened = false;
				}
			}

			if(!fileCanBeOpened) {
				MessageDialog.openInformation(getSite().getShell(),"Password needed","Invalid password");
			}

		} else {
			fileCanBeOpened=true;
		} 

		if(fileCanBeOpened){

			decodePage();

		}

		/**
		 * add count and then buttons
		 */
		/**
		 * activate SWT buttons
		 **/
		actStart.setEnabled(isEnabled);
		actFBack.setEnabled(isEnabled);
		actBack.setEnabled(isEnabled);
		actForward.setEnabled(isEnabled);
		actFForward.setEnabled(isEnabled);
		actEnd.setEnabled(isEnabled);
	}


	public final void changePage(final int pageChange) {
		int newPage=currentPage+pageChange;
		/**check page in range*/
		if((pageChange==-1 && currentPage>1) || (pageChange==1 && currentPage < decodePDF.getPageCount()-1)||
				(pageChange==-10 && currentPage>10) || (pageChange==10 && currentPage < decodePDF.getPageCount()-10)){
			gotoPage(newPage);
		}
	}	

	public final void gotoPage(final int newPage) {
		if (currentPage==newPage){return;}
		/**check page in range*/
		if((newPage<=decodePDF.getPageCount()-1)&&(newPage>=1)){
			currentPage=newPage;
			try {
				decodePage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	

	/**
	 * goto first page
	 */
	private void firstPage() {
		gotoPage(1);
	}

	/**
	 * goto last page
	 */
	public final void lastPage() {
		gotoPage(decodePDF.getPageCount());
	}

	/**
	 * goto another page
	 */
	private void movePage(final int change) {
		int newPage = currentPage + change;

		if (change < 0) {
			if (newPage > 0) {
				gotoPage(newPage);
			}
		} else if (newPage <= decodePDF.getPageCount()) {
			gotoPage(newPage);
		}
	}


	private void repaintPDF() {
		canvas.redraw();
		canvas.update();
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		// TODO Auto-generated method stub
		if (!(memento==null)){
			if (!(memento.getString(site.getSecondaryId())==null)){
				fileName=memento.getString(site.getSecondaryId());
				currentPage=memento.getInteger(site.getSecondaryId()+".page");
				mustLoad=true;
			} 
		}
		super.init(site, memento);
	}

	@Override
	public void saveState(IMemento memento) {
		// TODO Auto-generated method stub
		memento.putString(getViewSite().getSecondaryId(),fileName);
		memento.putInteger(getViewSite().getSecondaryId()+".page", currentPage);
		super.saveState(memento);
	}

	/**
	 * decode page and display
	 **/
	private void decodePage() {


		if (!isDecoding) {
			isDecoding = true;



			Runnable pageDecoder = new Runnable() {
				public void run() {
					try {

						PreferenceStore ps=LibraryPreferenceStore.getInstance();
						resolution=ps.getInt(LibraryPreferenceStore.HIRES_PDF)/100;
						float scale=((float) canvas.getScale());

						//decodePDF.useHiResScreenDisplay(hiRes);
						decodePDF.setPageParameters(resolution, currentPage);

						boolean retry=false;
						try {
							canvas.loadImage(decodePDF.getPageAsImage(currentPage));
						} catch (Error e) {
							// TODO Auto-generated catch block
							System.gc();
							e.printStackTrace();
							retry=true;
						}
						if (retry){
							try {
								canvas.loadImage(decodePDF.getPageAsImage(currentPage));
							} catch (Error e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						//setScale(scaling);
						canvas.scale(scale);

						pageCombo.select(currentPage-1);

						repaintPDF();

						isDecoding = false;
					} catch (Exception e) {
						isDecoding = false;
						e.printStackTrace();
					}
				}
			};

			getSite().getShell().getDisplay().asyncExec(pageDecoder);
		}

	}

}
