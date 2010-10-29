package com.delegreg.rpgm.ui.views;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IContaineable;
import com.delegreg.core.IContentListener;
import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.IDocRessource;
import com.delegreg.library.model.IDocument;
import com.delegreg.library.model.ImageDocument;
import com.delegreg.library.model.Libraries;
import com.delegreg.library.model.Library;
import com.delegreg.library.model.PDFDocument;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.library.ui.LibraryAdapterFactory;
import com.delegreg.library.util.DocumentIndexer;
import com.delegreg.rpgm.core.RpgmRelationalAdapter;
import com.delegreg.rpgm.core.Serializer;
import com.delegreg.rpgm.model.Actor;
import com.delegreg.rpgm.model.Campaign;
import com.delegreg.rpgm.model.Campaigns;
import com.delegreg.rpgm.model.Group;
import com.delegreg.rpgm.model.Groups;
import com.delegreg.rpgm.model.Location;
import com.delegreg.rpgm.model.Locations;
import com.delegreg.rpgm.model.Scenario;
import com.delegreg.rpgm.model.Scenarios;
import com.delegreg.rpgm.model.Sequence;
import com.delegreg.rpgm.model.Sequences;
import com.delegreg.rpgm.ui.RPGMAdapterFactory;
import com.delegreg.rpgm.ui.actions.DeleteItemAction;
import com.delegreg.rpgm.ui.actions.EditItemAction;
import com.delegreg.rpgm.ui.actions.ExportItemAction;
import com.delegreg.rpgm.ui.actions.ImportItemAction;
import com.delegreg.rpgm.ui.actions.NewItemAction;
import com.delegreg.rpgm.ui.actions.RenameItemAction;


public class CampaignsView extends ViewPart {
	public static final String ID = "com.delegreg.rpgm.views.campaigns"; //$NON-NLS-1$
	private TreeViewer treeViewer;
	private Campaigns campaigns;
	private EditItemAction editAction;
	private RenameItemAction renameAction;
	private NewItemAction newAction;
	private DeleteItemAction deleteAction;
	private ImportItemAction importAction;
	private ExportItemAction exportAction;
	/**
	 * @return the campaigns
	 */
	public Campaigns getCampaigns() {
		return campaigns;
	}


	private IAdapterFactory libAdapterFactory = new LibraryAdapterFactory();
	private IAdapterFactory adapterFactory = new RPGMAdapterFactory();
	private IContentListener cListener;

	public CampaignsView() {
	}

	
	private void makeActions() {
		  editAction = new EditItemAction(getSite().getWorkbenchWindow());
		  newAction = new NewItemAction(getSite().getWorkbenchWindow());
		  renameAction = new RenameItemAction(getSite().getWorkbenchWindow());
		  deleteAction = new DeleteItemAction(getSite().getWorkbenchWindow());
		  importAction = new ImportItemAction(getSite().getWorkbenchWindow());
		  exportAction = new ExportItemAction(getSite().getWorkbenchWindow());

		  // edit an item on double click.
		  treeViewer.addDoubleClickListener(new IDoubleClickListener() {
		    public void doubleClick(DoubleClickEvent event) {
		      editAction.run();
		    }
		  });

		    
		  // Create the context menu and register it with the Workbench.
		  MenuManager menuMgr = new MenuManager("campaignsPopup"); //$NON-NLS-1$
		  menuMgr.add(newAction);
		  menuMgr.add(editAction);
		  menuMgr.add(renameAction);
		  menuMgr.add(deleteAction);
		  menuMgr.add(new Separator());
		  menuMgr.add(importAction);
		  menuMgr.add(exportAction);
		  menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		  Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
		  treeViewer.getControl().setMenu(menu);
		  getSite().registerContextMenu(menuMgr, treeViewer);
		}	
	
	@Override
	public void createPartControl(Composite parent) {
		//initializeCampaigns(); // temporary tweak to build a fake model
		//'récupérer la campagne via le preferencestore'
		campaigns=Application.getCurrentCampaigns();
		
		
		//MultipleText mt=new MultipleText(parent,SWT.BORDER);
		
		treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Campaigns.class);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Campaign.class);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Scenarios.class);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Scenario.class);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Locations.class);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Location.class);
		Platform.getAdapterManager().registerAdapters(libAdapterFactory, Libraries.class);
		Platform.getAdapterManager().registerAdapters(libAdapterFactory, Library.class);
		Platform.getAdapterManager().registerAdapters(libAdapterFactory, IDocument.class);
		Platform.getAdapterManager().registerAdapters(libAdapterFactory, IDocRessource.class);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Group.class);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Groups.class);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Actor.class);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Sequences.class);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Sequence.class);
		getSite().setSelectionProvider(treeViewer);
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setContentProvider(new BaseWorkbenchContentProvider());

		// glisser/deposer		
		Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
		int operations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		
		
	   // Create the drag source on the tree
	    DragSource ds = new DragSource(treeViewer.getTree(), operations);
	    
	    ds.setTransfer(types);
		final ArrayList<IContaineable> dragList=new ArrayList<IContaineable>();

	    ds.addDragListener(new DragSourceAdapter() {
	    	
	      /* (non-Javadoc)
			 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragFinished(org.eclipse.swt.dnd.DragSourceEvent)
			 */
			@Override
			public void dragFinished(DragSourceEvent event) {
				// TODO perform "delete from move" type operations here
				super.dragFinished(event);
			}

			/* (non-Javadoc)
			 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragStart(org.eclipse.swt.dnd.DragSourceEvent)
			 */
			@Override
			public void dragStart(DragSourceEvent event) {
				// TODO check here if drag may start
				TreeItem[] selection = treeViewer.getTree().getSelection();
				dragList.clear();
				if (selection.length > 0) {
					event.doit = true;
					for (int i = 0; i < selection.length; i++) {
						dragList.add((IContaineable) selection[i].getData());
					}
				} else {
					event.doit = false;
				}
			}

			/* (non-Javadoc)
			 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
			 */
			@Override
			public void dragSetData(DragSourceEvent event) {
				//deux cas, un objet simple ou multiple
				event.data = Serializer.getInstance().toXML(dragList);
				super.dragSetData(event);
			}

	    });		  

	    // Create the drop target on the button
	    DropTarget dt = new DropTarget(treeViewer.getTree(), operations);
		types = new Transfer[] {TextTransfer.getInstance(),FileTransfer.getInstance()};
	    dt.setTransfer(types);
	    dt.addDropListener(new DropTargetAdapter() {
	     
			/* (non-Javadoc)
			 * @see org.eclipse.swt.dnd.DropTargetAdapter#dragOver(org.eclipse.swt.dnd.DropTargetEvent)
			 */
			@Override
			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
				//deux cas, dans le même parent ou pas
				if (event.item != null) {
					TreeItem item = (TreeItem)event.item;
					//test for transfer type
					if (FileTransfer.getInstance().isSupportedType(event.currentDataType)){
						//file object transfer
						if (item.getData() instanceof Libraries){
							// can drop only folder, test filetypes
							event.detail = DND.DROP_LINK;
						}else if (item.getData() instanceof Library){
							// can drop files, test filetypes
							event.detail = DND.DROP_LINK;
						}else{
							event.detail = DND.DROP_NONE;
						}
							
					} else {
						//internal object transfer
						RpgmRelationalAdapter adapter=new RpgmRelationalAdapter((BaseContaineableContainer)item.getData());
						//item is the current item
						//check out the item type before accepting a move operation 
						if (item.getData()==dragList.get(0).getContainer() || ((IContaineable)item.getData()).getContainer()==dragList.get(0).getContainer()){
							//System.out.println("inparent");
							Point pt = treeViewer.getTree().getDisplay().map(null, treeViewer.getTree(), event.x, event.y);
							Rectangle bounds = item.getBounds();
							if (pt.y < bounds.y + bounds.height/3) {
								//System.out.println("lt " + pt.y + " " + bounds.y + " " + (pt.y-bounds.y));
								event.feedback |= DND.FEEDBACK_INSERT_BEFORE;
								event.detail = DND.DROP_MOVE;
							} else if (pt.y > bounds.y + 2*bounds.height/3) {
								//System.out.println("gt " + pt.y + " " + bounds.y + " " + (pt.y-bounds.y));
								event.feedback |= DND.FEEDBACK_INSERT_AFTER;
								event.detail = DND.DROP_MOVE;
							} else {
								//System.out.println("bt " + pt.y + " " + bounds.y + " " + (pt.y-bounds.y) );
								event.feedback |= DND.FEEDBACK_SELECT;
								if (adapter.doesAcceptSubItem((IContaineable) dragList.get(0))){
									//System.out.println("inparentdoesacceptcopy");
									event.detail = DND.DROP_COPY;
								}else if (adapter.doesImportSubItem((IContaineable) dragList.get(0))){
									//System.out.println("inparentdoesacceptlink");
									event.detail = DND.DROP_LINK;
								}else{
									event.detail = DND.DROP_NONE;
								}						}
						} else {
							//depending on the item type, allow the copy/import
							//System.out.println("notinparent");
							event.feedback |= DND.FEEDBACK_SELECT;
							if (adapter.doesAcceptSubItem((IContaineable) dragList.get(0))){
								//System.out.println("doesacceptcopy");
								event.detail = DND.DROP_COPY;
							}else if (adapter.doesImportSubItem((IContaineable) dragList.get(0))){
								//System.out.println("doesacceptlink");
								event.detail = DND.DROP_LINK;
							}else{
								event.detail = DND.DROP_NONE;
							}
								
						}
					}
				}
			}

		public void drop(DropTargetEvent event) {
			if (event.data == null) {
				event.detail = DND.DROP_NONE;
				return;
			}
			if (FileTransfer.getInstance().isSupportedType(event.currentDataType)){
				System.out.println("dropfile"); //$NON-NLS-1$
				if (event.detail==DND.DROP_LINK){
					//implement the import action
					final TreeItem item = (TreeItem)event.item;
					final String[] files = (String[]) event.data;
					ProgressMonitorDialog pd = new ProgressMonitorDialog(getSite().getShell());
					try {
						pd.run(false /*fork*/, true /*cancelable*/, new IRunnableWithProgress() {
						    public void run(IProgressMonitor monitor) {
						    	monitor.beginTask(Messages.CampaignsView_ProgressImporting, countDocument(files));
								if (item.getData() instanceof Library){
									Library lib=(Library) item.getData(); 
									importFiles(lib, files,monitor);					
								}
								if (item.getData() instanceof Libraries){
									Libraries lib=(Libraries) item.getData(); 
									importFiles(lib, files,monitor);					
								}
						      monitor.done();
						    }


						  });
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}else {
				System.out.println("dropobj"); //$NON-NLS-1$
				if (event.detail==DND.DROP_MOVE){
					//implement the move action
					//get the parent, the new location and the source
					TreeItem dropitem = (TreeItem)event.item;
					BaseContaineableContainer location=(BaseContaineableContainer) dropitem.getData();
					//on a l'emplacement du drop
					for (Iterator<IContaineable> iterator = dragList.iterator(); iterator.hasNext();) {
						IContaineable item = (IContaineable) iterator.next();
						BaseContaineableContainer parent=(BaseContaineableContainer) item.getContainer();
						RpgmRelationalAdapter adapter=new RpgmRelationalAdapter(parent);
						if (location.getContainer()==parent){
							//insert after location
							adapter.moveSubItemAfter(item,location);
						} else if(location==parent){
							//move to top
							adapter.moveSubItemToTop(item);
						} else {
							//move somewhere else
							
						}
					}
						
					}
				if (event.detail==DND.DROP_LINK){
					//implement the import action
					//get the parent, the new location and the source
					TreeItem dropitem = (TreeItem)event.item;
					BaseContaineableContainer parent=(BaseContaineableContainer) dropitem.getData();
					RpgmRelationalAdapter adapter=new RpgmRelationalAdapter(parent);
					ArrayList<IContaineable> items = (ArrayList<IContaineable>) Serializer.getInstance().fromXML((String) event.data);
					for (Iterator<IContaineable> iterator = items.iterator(); iterator.hasNext();) {
						IContaineable item = (IContaineable) iterator.next();
						if (adapter.doesImportSubItem(item)){
							adapter.importSubObject(item);
						}else{
							System.out.println("can't adapt"); //$NON-NLS-1$
						}
					}
				}
				if (event.detail==DND.DROP_COPY){
					//implement the copy action
					//get the parent and the source
					TreeItem dropitem = (TreeItem)event.item;
					BaseContaineableContainer parent=(BaseContaineableContainer) dropitem.getData();
					RpgmRelationalAdapter adapter=new RpgmRelationalAdapter(parent);
					ArrayList<IContaineable> items = (ArrayList<IContaineable>) Serializer.getInstance().fromXML((String) event.data);
					for (Iterator<IContaineable> iterator = items.iterator(); iterator.hasNext();) {
						IContaineable item = (IContaineable) iterator.next();
						if (adapter.doesAcceptSubItem(item)){
							adapter.addSubObject(item);
						}else{
							System.out.println("can't adapt"); //$NON-NLS-1$
						}
					}
				}
			}

		
			
		}
	      
	    });

		
		treeViewer.setInput(campaigns);

		cListener = new IContentListener() {
			public void contentChanged() {
				treeViewer.refresh();
			}
		};
		campaigns.addContentListener(cListener);

		
		
		makeActions();
	}

	public void refresh() {
		treeViewer.refresh();
	}

	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}



	public void dispose() {
		campaigns.removeContentListener(cListener);
		Platform.getAdapterManager().unregisterAdapters(adapterFactory);
		super.dispose();
	}

	public void setCampaigns(Campaigns newRoot) {
		try {
			if (campaigns!=null){
					campaigns.removeContentListener(cListener);
			}
			campaigns=newRoot;
			campaigns.addContentListener(cListener);
			treeViewer.setInput(campaigns);
			treeViewer.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int countDocument(String[] files) {
		if (files==null){return 0;}
		int count=files.length;
		for (int i = 0; i < files.length; i++) {
			File tfile=new File(files[i]);
			count= count + countDocument(tfile.list());
		}
		return count;
	}	

	private int countDocument(File[] files) {
		if (files==null){return 0;}
		int count=files.length;
		for (int i = 0; i < files.length; i++) {
			count= count + countDocument(files[i].list());
		}
		return count;
	}	
	
	private void importFiles(final Library lib, final String[] files, IProgressMonitor monitor) {
		//System.out.println("importFiles"); 
		if (files != null && files.length > 0) {
			// Convert the given monitor into a progress instance 
			SubMonitor progress = SubMonitor.convert(monitor, countDocument(files));
			// Advance the monitor by another 30%
			progress.beginTask(Messages.CampaignsView_ProgressImporting, countDocument(files));
			for (int i = 0; i < files.length; i++) {
		        if (progress.isCanceled()) {return;};
		        progress.subTask(Messages.CampaignsView_Importing + files[i]);
				File newFile=new File(files[i]);
				importFile(lib, newFile,progress);
				progress.worked(1);
		        getSite().getShell().getDisplay().readAndDispatch();
			}
			progress.done();
		} 
	}

	private void importFiles(final Libraries lib, final String[] files, IProgressMonitor monitor) {
		if (files != null && files.length > 0) {
			// Convert the given monitor into a progress instance 
			SubMonitor progress = SubMonitor.convert(monitor, countDocument(files));
			// Advance the monitor by another 30%
			progress.beginTask(Messages.CampaignsView_ProgressImporting, countDocument(files));
			for (int i = 0; i < files.length; i++) {
		        if (progress.isCanceled()) {return;};
		        progress.subTask(Messages.CampaignsView_Importing + files[i]);
				File newFile=new File(files[i]);
				if (newFile.isDirectory()){
					Library newlib=new Library(newFile.getName());
					lib.add(newlib);
					//insert code here to process contents
					importFiles(newlib, newFile.listFiles(),progress);
				}
		        progress.worked(1);
		        getSite().getShell().getDisplay().readAndDispatch();
			}
	      progress.done();
		} 

	}

	private void importFiles(Library lib, File[] files,IProgressMonitor monitor) {
		if (files != null && files.length > 0) {
			// Convert the given monitor into a progress instance 
			SubMonitor progress = SubMonitor.convert(monitor, countDocument(files));
			// Advance the monitor by another 30%
			progress.beginTask(Messages.CampaignsView_ProgressImporting, countDocument(files));
			for (int i = 0; i < files.length; i++) {
		        if (progress.isCanceled()) {return;};
		        progress.subTask(Messages.CampaignsView_Importing + files[i]);
				importFile(lib, files[i],progress);
		        progress.worked(1);
		        getSite().getShell().getDisplay().readAndDispatch();
			}
            progress.done();
		} 
	}


	private void importFile(Library lib, File newFile,IProgressMonitor monitor) {
		System.out.println("process : " + newFile.getPath());  //$NON-NLS-1$
		if (newFile.exists() && newFile.isFile()){
			String fileName=newFile.getName().toLowerCase();
			if (fileName.endsWith(".pdf")){ //$NON-NLS-1$
				//import pdf
				PDFDocument pdf=new PDFDocument();
				pdf.setName(newFile.getName().substring(0, newFile.getName().length()-4));
				pdf.setFileName(newFile.getPath());
				DocumentIndexer indexer=new DocumentIndexer();
				indexer.index(pdf);
				lib.add(pdf);
			}
			if (fileName.endsWith(".bmp") || fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".gif")){ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				//import pdf
				ImageDocument img=new ImageDocument(fileName.substring(0, fileName.length()-4));
				img.setFileName(newFile.getPath());
				lib.add(img);
			}
		}else if (newFile.exists() && newFile.isDirectory()){
			//we only have first level directories here, so do the following test : 
			// if directory contains mostly mp3 files, import as audio ressource
			// else import as library and recurse on contained files
			FileFilter fileFilter = new FileFilter() {
			    public boolean accept(File file) {
			    return file.isFile();
			}};
			FileFilter mp3fileFilter = new FileFilter() {
			    public boolean accept(File file) {
			    if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")){ //$NON-NLS-1$
			   	 return true;
			    }
			    return false;
			}};
			int numFiles=newFile.listFiles(fileFilter).length;
			int nummp3Files=newFile.listFiles(mp3fileFilter).length;
			if (nummp3Files>(numFiles-nummp3Files)){
				//mp3folder
				//import as audio ressource
				AudioDocument audiof=new AudioDocument();
				audiof.setName(newFile.getName());
				audiof.setFolder(newFile.getPath());
				DocumentIndexer indexer=new DocumentIndexer();
				indexer.index(audiof);
				lib.add(audiof);
			}else {
			    //import as library and process contents
				Library newlib=new Library(newFile.getName());
				lib.getSubLibraries().add(newlib);
				//insert code here to process contents
				System.out.println("importsublib"); //$NON-NLS-1$
				importFiles(newlib, newFile.listFiles(),monitor);
			} 
		}
	}


}
