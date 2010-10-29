/*
 * 
 */
package com.delegreg.rpgm.ui;

import java.util.ArrayList;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.IContainer;
import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.HttpDocRessource;
import com.delegreg.library.model.HttpDocument;
import com.delegreg.library.model.IDocRessource;
import com.delegreg.library.model.IDocument;
import com.delegreg.library.model.Libraries;
import com.delegreg.library.model.Library;
import com.delegreg.library.model.PDFDocRessource;
import com.delegreg.library.model.PDFDocument;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
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

/**
 * A factory for creating RPGMAdapter objects.
 */
public class RPGMAdapterFactory implements IAdapterFactory {

	/**
	 * Instantiates a new rPGM adapter factory.
	 */
	public RPGMAdapterFactory() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	@Override
	public Object getAdapter(final Object adaptableObject, final Class adapterType) {
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Campaigns)
			return campaignsAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Campaign)
			return campaignAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Scenarios )
			return scenariosAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Scenario)
			return scenarioAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Locations)
			return locationsAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Location )
			return locationAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Groups)
			return groupsAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Group)
			return groupAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Actor)
			return actorAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Sequences)
			return sequencesAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Sequence)
			return sequenceAdapter;
		return null;
	}

	public String computeSizeLabel(IContainer<?> array){
		if (array.size()==0) {
			return "";//$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$
		} else {
			return " (" + array.size() + ")" ;  //$NON-NLS-1$//$NON-NLS-2$
		}
	};
	
	public String computeSizeLabel(ArrayList array){
		if (array.size()==0) {
			return "";//$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$
		} else {
			return " (" + array.size() + ")" ;  //$NON-NLS-1$//$NON-NLS-2$
		}
	};

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Class[] getAdapterList() {
		return new Class[] {IWorkbenchAdapter.class};
	}

	/** The campaigns adapter. */
	private IWorkbenchAdapter campaignsAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return null;
		}
		public String getLabel(Object o) {
			Campaigns entry = ((Campaigns)o);
			return Messages.RPGMAdapterFactory_CampaignsName + computeSizeLabel(entry);
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return null;
		}

		public Object[] getChildren(Object o) {
			Campaigns entry = ((Campaigns)o);
			Object[] tmp = new Object[entry.size()+1];
			System.arraycopy(entry.toArray(), 0, tmp, 0, entry.size());
			tmp[entry.size()]=entry.getReferences();
			return tmp;
		}
	};


	/** The campaign adapter. */
	private IWorkbenchAdapter campaignAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((Campaign)o).getContainer();
		}
		public String getLabel(final Object o) {
			Campaign entry = ((Campaign)o);
			if (!entry.getGameMaster().equals("")){ //$NON-NLS-1$
				return entry.getName() + ' ' + '(' + entry.getGameMaster() + ')';
			}else{
				return entry.getName();
			}				
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageKeys.CAMPAIGN);
		}

		public Object[] getChildren(Object o) {
			Campaign entry = ((Campaign)o);
			Object[] temp=new Object[4];
			temp[0]=entry.getScenarios();
			temp[1]=entry.getSideQuests();
			temp[2]=entry.getLocations();
			temp[3]=entry.getActors();
			return temp;
		}
	};

	/** The scenarios adapter. */
	private IWorkbenchAdapter scenariosAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((Scenarios)o).getContainer();
		}
		public String getLabel(Object o) {
			Scenarios entry = ((Scenarios)o);
			return entry.getName() + computeSizeLabel(entry); //$NON-NLS-1$ //$NON-NLS-2$
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageKeys.SCENARIOS);
		}

		public Object[] getChildren(Object o) {
			Scenarios entry = ((Scenarios)o);
			//to be filled soon too
			//add the scenarios objects
			return entry.toArray();
		}
	};

	/** The scenario adapter. */
	private IWorkbenchAdapter scenarioAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((Scenario)o).getContainer();
		}
		public String getLabel(Object o) {
			Scenario entry = ((Scenario)o);
			return entry.getName();
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageKeys.SCENARIO);
		}

		public Object[] getChildren(Object o) {
			Scenario entry=(Scenario)o;
			Object[] temp=new Object[2];
			temp[0]=entry.getSequences();
			temp[1]=entry.getActors();
			return temp;
		}
	};



	/** The locations adapter. */
	private IWorkbenchAdapter locationsAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((Locations)o).getContainer();
		}
		public String getLabel(Object o) {
			Locations entry = ((Locations)o);
			return Messages.RPGMAdapterFactory_LocationsName + computeSizeLabel(entry); //$NON-NLS-2$
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageKeys.LOCATIONS);
		}

		public Object[] getChildren(Object o) {
			Locations entry = ((Locations)o);
			return entry.toArray();
		}
	};

	/** The location adapter. */
	private IWorkbenchAdapter locationAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((Location)o).getContainer();
		}
		public String getLabel(Object o) {
			Location entry = ((Location)o);
			return entry.getName();
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageKeys.LOCATION);
		}

		public Object[] getChildren(Object o) {
			Location entry = ((Location)o);
			return entry.getSubLocations().toArray();					    
		}
	};




	/** The actor adapter. */
	private IWorkbenchAdapter actorAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((Actor)o).getContainer();
		}
		public String getLabel(Object o) {
			Actor entry = ((Actor)o);
			return entry.getName();
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageKeys.ACTOR);
		}

		public Object[] getChildren(Object o) {
			return new Object[0];
		}
	};



	/** The group adapter. */
	private IWorkbenchAdapter groupAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((Group)o).getContainer();
		}
		public String getLabel(Object o) {
			Group entry = ((Group)o);
			return entry.getName() + computeSizeLabel(entry);
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageKeys.GROUP);
		}

		public Object[] getChildren(Object o) {
			//to be filled soon too
			//add the subgroups and actors objects
			Group entry = ((Group)o);
			Object tmp[] = new Object[entry.size()+entry.getSubGroups().size()];
			System.arraycopy(entry.getSubGroups().toArray(), 0, tmp, 0, entry.getSubGroups().size());
			System.arraycopy(entry.toArray(), 0, tmp, entry.getSubGroups().size(), entry.size());
			return tmp;
		}
	};	  

	/** The groups adapter. */
	private IWorkbenchAdapter groupsAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((Groups)o).getContainer();
		}
		public String getLabel(Object o) {
			Groups entry = ((Groups)o);
			return entry.getName() + computeSizeLabel(entry);
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageKeys.GROUPS);
		}

		public Object[] getChildren(Object o) {
			Groups entry = ((Groups)o);
			return entry.toArray();					    
		}
	};	  

	/** The groups adapter. */
	private IWorkbenchAdapter sequencesAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((Sequences)o).getContainer();
		}
		public String getLabel(Object o) {
			Sequences entry = ((Sequences)o);
			return entry.getName() + computeSizeLabel(entry);
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageKeys.SEQUENCES);
		}

		public Object[] getChildren(Object o) {
			//to be filled soon too
			//add the subgroups and actors objects
			Sequences entry = ((Sequences)o);
			return entry.toArray();					    
		}
	};	  

	/** The sequence adapter. */
	private IWorkbenchAdapter sequenceAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((Sequence)o).getContainer();
		}
		public String getLabel(Object o) {
			Sequence entry = ((Sequence)o);
			return entry.getName();
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageKeys.SEQUENCE);
		}

		public Object[] getChildren(Object o) {
			return new Object[0];
		}
	};



}
