package com.delegreg.library.ui.views;


import java.io.File;
import java.util.Map;
import java.util.Random;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.AudioRessource;
import com.delegreg.library.Activator;
import com.delegreg.library.Messages;
import com.delegreg.library.ui.IImageKeys;


public class AudioPlayer extends ViewPart implements BasicPlayerListener {
	public static final String ID = "com.delegreg.library.views.audio"; //$NON-NLS-1$
	
	private TableViewer viewer;

	//some actions in menu
	private Action actionPrevious;
	private Action actionPlay;
	private Action actionPauseResume;
	private Action actionStop;
	private Action actionNext;
	private Action actionClear;
	private Action actionDoubleClick;
	private Action actionRandom;
	private Action actionLoopSingle;
	private Action actionLoopAll;
	/*
	 * indicate if player is playing, do not change !!!
	 */
	private boolean playing=false;
	/*
	 * music player object
	 */
	private BasicPlayer player;
	/*
	 * controler for player, have functions like play, stop... (but use functions in this class)
	 *
	 */
	private BasicController control;    
	
	
	private long currentPosition;
	private long currentSize;

	private Label progressReport;
	private String currentFile;
	
	private boolean loopSingle=false;

	private boolean loopAll=false;
	
	private boolean random=false;

	private AudioDocument document;

	
	
	/**
	 * The constructor.
	 * @throws BasicPlayerException 
	 */
	public AudioPlayer() throws BasicPlayerException {
		//initialize player
		player = new BasicPlayer();
		control = (BasicController) player;
		player.addBasicPlayerListener(AudioPlayer.this);
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2,false));
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new BaseWorkbenchContentProvider());
		viewer.setLabelProvider(new WorkbenchLabelProvider());
		//viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		viewer.getTable().setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,1));
		Label title=new Label(parent,SWT.NULL);
		title.setText("Currently playing : ");
		title.setLayoutData(new GridData(SWT.LEFT,SWT.FILL,false,false));
		progressReport=new Label(parent,SWT.NULL);
		progressReport.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
		parent.pack();
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				AudioPlayer.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	/*
	 * fill any object with action buttons
	 */
	private void fillMenu(IContributionManager manager) {
		manager.add(actionPrevious);
		manager.add(actionPlay);
		manager.add(actionPauseResume);
		manager.add(actionStop);
		manager.add(actionNext);
		manager.add(new Separator());
		manager.add(actionRandom);
		manager.add(actionLoopSingle);
		manager.add(actionLoopAll);
		manager.add(new Separator());
		manager.add(actionClear);
		
		
	}
	private void fillLocalPullDown(IContributionManager manager) {
		fillMenu( manager);
	}

	private void fillContextMenu(IMenuManager manager) {
		fillMenu(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		fillMenu(manager);
	}

	/*
	 * action action buttons
	 */
	private void makeActions() {
		//previous
		actionPrevious = new Action() {
			public void run() {
				AudioPlayer.this.previous();
			}
		};
		actionPrevious.setText("Previous");
		actionPrevious.setToolTipText("Previous song");
		actionPrevious.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.PREVSONG));
		
		//play
		actionPlay = new Action() {
			public void run() {
				AudioPlayer.this.play();
			}
		};
		actionPlay.setText("Play");
		actionPlay.setToolTipText("Start playing selected song");
		actionPlay.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.PLAYSONG));
		
		//pause/resume
		actionPauseResume = new Action() {
			public void run() {
				AudioPlayer.this.pauseResume();					
			}
		};
		actionPauseResume.setText("Pause");
		actionPauseResume.setToolTipText("Pause the current song");
		actionPauseResume.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.PAUSERESUME));
		
		//stop
		actionStop = new Action() {
			public void run() {
				AudioPlayer.this.stop();					
			}
		};
		actionStop.setText("Stop");
		actionStop.setToolTipText("Stop playback");
		actionStop.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.STOPSONG));

		//next
		actionNext = new Action() {
			public void run() {
				AudioPlayer.this.next();					
			}
		};
		actionNext.setText("Next");
		actionNext.setToolTipText("Play next song");
		actionNext.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.NEXTSONG));

		//clear
		actionClear = new Action(){
			public void run(){
				AudioPlayer.this.clear();
			}
		};
		
		actionClear.setText("Clear");
		actionClear.setToolTipText("Clear the playlist");
		actionClear.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.DELETEITEM));

		//random
		actionRandom = new Action("Random",Action.AS_CHECK_BOX){
			public void run(){
				AudioPlayer.this.random=this.isChecked();
			}
		};
		
		actionRandom.setChecked(random);
		actionRandom.setToolTipText("Play songs in random order");
		actionRandom.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.RANDOM));

		//loop single
		actionLoopSingle = new Action("Loop",Action.AS_CHECK_BOX){
			public void run(){
				AudioPlayer.this.loopSingle=this.isChecked();
			}
		};
		
		actionLoopSingle.setChecked(loopSingle);
		actionLoopSingle.setToolTipText("Play this song over and over");
		actionLoopSingle.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.LOOP));

		//loop list
		actionLoopAll = new Action("Loop list",Action.AS_CHECK_BOX){
			public void run(){
				AudioPlayer.this.loopAll=this.isChecked();
			}
		};
		
		actionLoopAll.setChecked(loopAll);
		actionLoopAll.setToolTipText("Play this list over and over");
		actionLoopAll.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, IImageKeys.LOOPALL));

		//doubleclick
		actionDoubleClick = new Action() {
			public void run() {
				AudioPlayer.this.play();
			}			
		};
	}

	/*
	 * play previous song
	 */
	protected void previous(){
		if (random){
			Random r=new Random();
			int pos = r.nextInt(viewer.getTable().getItemCount())+1;
			if(pos<viewer.getTable().getItemCount()){
				viewer.getTable().setSelection(pos);
				play();		
			}
		}else{
			int pos = viewer.getTable().getSelectionIndex();
			if(pos>0){
				viewer.getTable().setSelection(pos-1);
				play();
			}
		}
	}
	
	/*
	 * start playing
	 */
	protected void play() {
		if (viewer.getTable().getSelectionIndex()==-1 && viewer.getTable().getItemCount()>0){
			viewer.getTable().setSelection(0);
		}
		try {
			currentFile=((AudioRessource)viewer.getElementAt(viewer.getTable().getSelectionIndex())).getFileName();
			control.open(new File((currentFile)));
			currentFile=new File((currentFile)).getName();
			control.play();
		} catch (BasicPlayerException e) {
			throw new  RuntimeException(e);
		}		
	}	
	
	/*
	 * pause/resume playing current song
	 */
	protected void pauseResume() {
		try {
			if(playing){
				control.pause();
			}else{
				control.resume();
			}
				
		} catch (BasicPlayerException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/*
	 * stop playing
	 */
	protected void stop(){
		try {
			control.stop();
		} catch (BasicPlayerException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * play next song
	 */
	protected void next(){
		if (loopSingle){
			play();		
		}else if (random){
			Random r=new Random();
			int pos = r.nextInt(viewer.getTable().getItemCount())+1;
			if(pos<viewer.getTable().getItemCount()){
				viewer.getTable().setSelection(pos);
				play();		
			}
		}else{
			int pos = viewer.getTable().getSelectionIndex();
			if(pos<viewer.getTable().getItemCount()-1){
				viewer.getTable().setSelection(pos+1);
				play();		
			} else if (loopAll) {
				//end of list, start over
				viewer.getTable().setSelection(0);
				play();		
			}
		}
		
	}


	/*
	 * clear playlist
	 */
	protected void clear() {
			Object o=viewer.getElementAt(0);
			while(o!=null){
				viewer.remove(o);
				o=viewer.getElementAt(0);
			}
			stop();
	}

	/*
	 * add dir to playlist
	 */
	protected void addDir() {
		//TODO show ressource explore on audio ressources only
	}

	/*
	 * add file/files to playlist
	 */
	protected void addFiles() {
		//TODO show ressource explore on audio ressources only
	}


	/*
	 * add file/files to playlist
	 */
	public void setDocument(AudioDocument doc) {
        if (doc != null){
        	document=doc;
        	setPartName("Audio player" + " : " + doc.getName());        	
        	for(int i=0;i<doc.size();i++){
        		AudioRessource ar =(AudioRessource) doc.get(i);
        		viewer.add(ar);
        	}
        }        
	}
	
	/*
	 * add file/files to playlist
	 */
	public void addRessource(AudioRessource doc) {
        if (doc != null){
    		viewer.add(doc);
        }        
	}
	
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent eve0nt) {
				actionDoubleClick.run();
			}
		});
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void opened(Object arg0, Map arg1) {
		if (arg1.containsKey("duration")) {currentSize = Long.parseLong(arg1.get("duration").toString());} //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void progress(int arg0, long arg1, byte[] arg2, Map arg3) {
		//set boolean playing, needed for pause/resume
		playing=true;		
		if (arg3.containsKey("mp3.position.microseconds")) {currentPosition = Long.parseLong(arg3.get("mp3.position.microseconds").toString());} //$NON-NLS-1$ //$NON-NLS-2$
		//TODO report progress on progress bar
		getSite().getShell().getDisplay().asyncExec(new Runnable(){
			@Override
			public void run() {
				//calcul du % d'avancement
				long step=currentSize/100;
				int progress=(int)(currentPosition/step);
				String text=currentFile + " (" + formatDuration(currentPosition) + "/" + formatDuration(currentSize) + "/" + progress + "%)"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				if (!progressReport.isDisposed()){
					progressReport.setText(text);
				}
			}});
	}

	private String formatDuration(Long microseconds){
		Long milliseconds=microseconds/1000;
		Long seconds=milliseconds/1000;
		milliseconds=milliseconds-(seconds*1000);
		Long minutes=seconds/60;
		seconds=seconds-(minutes*60);
		return minutes + ":" + seconds ;//+ ":" + milliseconds; //$NON-NLS-1$
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stop();
		player.removeBasicPlayerListener(AudioPlayer.this);		
		super.dispose();
	}

	public void setController(BasicController arg0) {
		
	}

	public void stateUpdated(BasicPlayerEvent arg0) {
		//set boolean playing, needed for pause/resume
		if(arg0.getCode() == BasicPlayerEvent.OPENED){
			playing=true;
		}else if(arg0.getCode() == BasicPlayerEvent.RESUMED){
			playing=true;
		}else if(arg0.getCode() == BasicPlayerEvent.PAUSED){
			playing=false;
		}else if(arg0.getCode() == BasicPlayerEvent.STOPPED){
			playing=false;
		}else if(arg0.getCode() == BasicPlayerEvent.EOM){
			//TODO : next file, loop or random
			//standard mode : playnext
			getSite().getShell().getDisplay().asyncExec(new Runnable(){
				@Override
				public void run() {
					next();
				}
			});
			System.out.println("eom"); //$NON-NLS-1$
		}else if(arg0.getCode() == BasicPlayerEvent.GAIN){
			System.out.println("gain"); //$NON-NLS-1$
		}else if(arg0.getCode() == BasicPlayerEvent.PAN){
			System.out.println("pan"); //$NON-NLS-1$
		}else if(arg0.getCode() == BasicPlayerEvent.SEEKED){
			System.out.println("seeked"); //$NON-NLS-1$
		}else if(arg0.getCode() == BasicPlayerEvent.SEEKING){
			System.out.println("seeking"); //$NON-NLS-1$
		}else if(arg0.getCode() == BasicPlayerEvent.OPENING){
			System.out.println("opening"); //$NON-NLS-1$
		}else {
			//System.out.println(arg0.getDescription().toString());
		}
	}
	
}


