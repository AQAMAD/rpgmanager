package com.delegreg.rpgm.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.delegreg.core.BaseContaineable;
import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.BaseContainer;
import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;
import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.AudioRessource;
import com.delegreg.library.model.HttpDocRessource;
import com.delegreg.library.model.HttpDocument;
import com.delegreg.library.model.IDocRessource;
import com.delegreg.library.model.IDocument;
import com.delegreg.library.model.ImageDocument;
import com.delegreg.library.model.Libraries;
import com.delegreg.library.model.Library;
import com.delegreg.library.model.PDFDocRessource;
import com.delegreg.library.model.PDFDocument;
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
import com.delegreg.rpgm.model.SideQuest;

public class RpgmRelationalAdapter {
	
	IContainer adapted;

	private static HashMap<Class,ArrayList<Class>> allowedChildren;
	private static HashMap<Class,ArrayList<Class>> importableChildren;

	private ArrayList<Class> allowedSubItems;
	private ArrayList<Class> importableSubItems;
	
	static {
		ArrayList<Class> tempClasses=new ArrayList<Class>();
		allowedChildren=new HashMap<Class,ArrayList<Class>>();
		allowedChildren.put(Actor.class, tempClasses);
		allowedChildren.put(Sequence.class, tempClasses);
		allowedChildren.put(ImageDocument.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(AudioRessource.class);
		allowedChildren.put(AudioDocument.class,tempClasses );
		allowedChildren.put(AudioRessource.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(PDFDocRessource.class);
		allowedChildren.put(PDFDocument.class,tempClasses );
		allowedChildren.put(PDFDocRessource.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(HttpDocRessource.class);
		allowedChildren.put(HttpDocument.class,tempClasses );
		allowedChildren.put(HttpDocRessource.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(Group.class);
		tempClasses.add(Actor.class);
		allowedChildren.put(Group.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(Group.class);
		allowedChildren.put(Groups.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(Location.class);
		allowedChildren.put(Location.class,tempClasses );
		allowedChildren.put(Locations.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(Sequence.class);
		allowedChildren.put(Sequences.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(Scenario.class);
		allowedChildren.put(Scenarios.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(Sequence.class);
		tempClasses.add(Actor.class);
		tempClasses.add(Group.class);
		allowedChildren.put(Scenario.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(PDFDocument.class);
		tempClasses.add(AudioDocument.class);
		tempClasses.add(HttpDocument.class);
		tempClasses.add(ImageDocument.class);
		tempClasses.add(Library.class);
		allowedChildren.put(Library.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(Library.class);
		tempClasses.add(Campaign.class);
		allowedChildren.put(Libraries.class,tempClasses );
		allowedChildren.put(Campaigns.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(Scenario.class);
		tempClasses.add(SideQuest.class);
		tempClasses.add(Group.class);
		tempClasses.add(Location.class);
		tempClasses.add(Campaign.class);
		allowedChildren.put(Campaign.class,tempClasses );		

	
		importableChildren=new HashMap<Class,ArrayList<Class>>();
		tempClasses=new ArrayList<Class>();
		importableChildren.put(Actor.class, tempClasses);
		importableChildren.put(Sequence.class, tempClasses);
		importableChildren.put(AudioDocument.class,tempClasses );
		importableChildren.put(AudioRessource.class,tempClasses );
		importableChildren.put(PDFDocument.class,tempClasses );
		importableChildren.put(PDFDocRessource.class,tempClasses );
		importableChildren.put(HttpDocument.class,tempClasses );
		importableChildren.put(HttpDocRessource.class,tempClasses );
		importableChildren.put(Location.class,tempClasses );
		importableChildren.put(Sequences.class,tempClasses );
		importableChildren.put(Scenario.class,tempClasses );
		importableChildren.put(Library.class,tempClasses );
		importableChildren.put(Libraries.class,tempClasses );
		importableChildren.put(Campaigns.class,tempClasses );
		importableChildren.put(Campaign.class,tempClasses );		
		importableChildren.put(ImageDocument.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(PDFDocument.class);
		tempClasses.add(HttpDocument.class);
		importableChildren.put(Scenarios.class,tempClasses );
		importableChildren.put(Groups.class,tempClasses );
		tempClasses=new ArrayList<Class>();
		tempClasses.add(PDFDocument.class);
		tempClasses.add(PDFDocRessource.class);
		tempClasses.add(HttpDocument.class);
		tempClasses.add(HttpDocRessource.class);
		importableChildren.put(Locations.class,tempClasses );
		importableChildren.put(Location.class,tempClasses );
		importableChildren.put(Group.class,tempClasses );	
	}
	
	public boolean addSubObject(IContaineable newSubObject){
		boolean result=addRpgm(newSubObject);
		if (result){
			adapted.fireContentChanged();
			return true;
		}
		System.out.println("Impossible to add " + newSubObject.getClass().getSimpleName() + " to " + this.getClass().getSimpleName()); //$NON-NLS-1$ //$NON-NLS-2$
		return false;
	}
	
	public boolean importSubObject(IContaineable newSubObject){
		boolean result=importRpgm(newSubObject);
		if (result){
			adapted.fireContentChanged();
			return true;
		}
		System.out.println("Impossible to import " + newSubObject.getClass().getSimpleName() + " to " + this.getClass().getSimpleName()); //$NON-NLS-1$ //$NON-NLS-2$
		return false;
	}

	private boolean addRpgm(IContaineable newSubObject){
		if (adapted instanceof Libraries){
			Libraries adaptedItem=(Libraries)adapted;
			if (newSubObject instanceof Campaign){
				Campaigns root=(Campaigns) adaptedItem.getRoot();
				root.add((Campaign) newSubObject);
				return true; 
			} 
			if (newSubObject instanceof Library){
				adaptedItem.add((Library) newSubObject);
				return true;
			}
			return false;
		}
		if (adapted instanceof Library){
			Library adaptedItem=(Library)adapted;
			if (newSubObject instanceof IDocument){
				adaptedItem.add((IDocument) newSubObject);
				return true;
			}
			if (newSubObject instanceof Library){
				adaptedItem.getSubLibraries().add((Library) newSubObject);
				return true;
			}
			return false;
		}
		if (adapted instanceof IDocument){
			IDocument adaptedItem=(IDocument)adapted;
			if (newSubObject instanceof IDocRessource){
				adaptedItem.add((IDocRessource) newSubObject);
				return true;
			}
			return false;
		}
		if (adapted instanceof IDocRessource){
			IDocRessource adaptedItem=(IDocRessource)adapted;
			if (newSubObject instanceof IDocRessource){
				adaptedItem.add((IDocRessource) newSubObject);
				return true;
			}
			return false;
		}
		if (adapted instanceof Campaigns){
			Campaigns adaptedItem=(Campaigns)adapted;
			if (newSubObject instanceof Campaign){
				adaptedItem.add((Campaign) newSubObject);
				return true;
			}
			if (newSubObject instanceof Library){
				adaptedItem.getReferences().add((Library) newSubObject);
				return true;
			}
			return false;
		}
		if (adapted instanceof Campaign){
			Campaign adaptedItem=(Campaign)adapted;
			if (newSubObject instanceof Campaign){
				Campaigns root=(Campaigns) adaptedItem.getRoot();
				root.add((Campaign) newSubObject);
				return true;
			}
			if (newSubObject instanceof Scenario){
				adaptedItem.getScenarios().add((Scenario) newSubObject);
				return true;
			}
			if (newSubObject instanceof SideQuest){
				adaptedItem.getSideQuests().add((SideQuest) newSubObject);
				return true;
			}
			if (newSubObject instanceof Group){
				adaptedItem.getActors().add((Group) newSubObject);
				return true;
			}
			if (newSubObject instanceof Location){
				adaptedItem.getLocations().add((Location) newSubObject);
				return true;
			}
			return false;
		}

		if (adapted instanceof Scenarios){
			Scenarios adaptedItem=(Scenarios)adapted;
			if (newSubObject instanceof Scenario){
				adaptedItem.add((Scenario) newSubObject);
				return true;
			}
			return false;
		}

		if (adapted instanceof Scenario){
			Scenario adaptedItem=(Scenario)adapted;
			if (newSubObject instanceof Sequence){
				adaptedItem.getSequences().add((Sequence) newSubObject);
				return true;
			}
			if (newSubObject instanceof Group){
				adaptedItem.getActors().getSubGroups().add((Group) newSubObject);
				return true;
			}
			if (newSubObject instanceof Actor){
				adaptedItem.getActors().add((Actor) newSubObject);
				return true;
			}
			return false;
		}
		
		if (adapted instanceof Sequences){
			Sequences adaptedItem=(Sequences)adapted;
			if (newSubObject instanceof Sequence){
				adaptedItem.add((Sequence) newSubObject);
				return true;
			}
			return false;
		}
		
		if (adapted instanceof Locations){
			Locations adaptedItem=(Locations)adapted;
			if (newSubObject instanceof Location){
				adaptedItem.add((Location) newSubObject);
				return true;
			}
			return false;
		}

		if (adapted instanceof Location){
			Location adaptedItem=(Location)adapted;
			if (newSubObject instanceof Location){
				adaptedItem.getSubLocations().add((Location) newSubObject);
				return true;
			}
			return false;
		}

		if (adapted instanceof Groups){
			Groups adaptedItem=(Groups)adapted;
			if (newSubObject instanceof Group){
				adaptedItem.add((Group) newSubObject);
				return true;
			}
			return false;
		}

		if (adapted instanceof Group){
			Group adaptedItem=(Group)adapted;
			if (newSubObject instanceof Actor){
				adaptedItem.add((Actor) newSubObject);
				return true;
			}
			if (newSubObject instanceof Group){
				adaptedItem.getSubGroups().add((Group) newSubObject);
				return true;
			}
			return false;
		}

		return false;
	}
	
	
	public RpgmRelationalAdapter(IContainer  adapted) {
		super();
		this.adapted = adapted;
		allowedSubItems = allowedChildren.get(adapted.getClass());
		importableSubItems = importableChildren.get(adapted.getClass());
	}

	public ArrayList<Class> getContentTypes() {
		// TODO Auto-generated method stub
		return allowedSubItems;
	}
	
	public ArrayList<Class> getImportTypes() {
		// TODO Auto-generated method stub
		return importableSubItems;
	}
	
	public boolean doesAcceptSubItem(IContaineable item) {
		// TODO Auto-generated method stub
		//System.out.println("indoesaccept");
		for (Iterator iterator = allowedSubItems.iterator(); iterator.hasNext();) {
			Class class1 = (Class) iterator.next();
			//System.out.println("test" + class1.getName() + "accepts" + obj.getClass().getName());
			if (class1.getName().equals(item.getClass().getName())){
				//System.out.println(class1.getSimpleName() + "accepts" + obj.getClass().getSimpleName());
				return true;
			}
		}
		return false;
	}
	
	
	public boolean doesImportSubItem(IContaineable item) {
		// TODO Auto-generated method stub
		//System.out.println("indoesaccept");
		for (Iterator iterator = importableSubItems.iterator(); iterator.hasNext();) {
			Class class1 = (Class) iterator.next();
			//System.out.println("test" + class1.getName() + "accepts" + obj.getClass().getName());
			if (class1.getName().equals(item.getClass().getName())){
				//System.out.println(class1.getSimpleName() + "accepts" + obj.getClass().getSimpleName());
				return true;
			}
		}
		return false;
	}

	public void moveSubItemToTop(IContaineable item) {
		if (adapted instanceof IContainer){
			//use the arraylist methods to reorder
			IContainer container=(IContainer) adapted;
			//use reorderring transfer to temporary ArrayList
			ArrayList newItems=new ArrayList();
			newItems.add(item);
			container.remove(item);
			for (Iterator iterator = container.iterator(); iterator.hasNext();) {
				IContaineable containeable = (IContaineable) iterator.next();
				newItems.add(containeable);
			}
			container.removeAll(newItems);
			container.addAll(newItems);
			adapted.fireContentChanged();
		}
	}

	public void moveSubItemAfter(IContaineable item,
			IContainer<IContaineable> location) {
		if (adapted instanceof IContainer){
			//use the arraylist methods to reorder
			if (location==item){
				return;
			}
			IContainer container=(IContainer) adapted;
			//use reorderring transfer to temporary ArrayList
			ArrayList newItems=new ArrayList();
			for (Iterator iterator = container.iterator(); iterator.hasNext();) {
				IContaineable containeable = (IContaineable) iterator.next();
				if (containeable!=item && containeable!=location){
					newItems.add(containeable);
				}
				if (containeable==location) {
					newItems.add(containeable);
					newItems.add(item);
				}
			}
			container.removeAll(newItems);
			container.addAll(newItems);
			adapted.fireContentChanged();
		}	}

	private boolean importRpgm(IContaineable item) {
		if (adapted instanceof Scenarios){
			Scenarios adaptedItem=(Scenarios)adapted;
			if (item instanceof IDocument){
				IDocument newItem=(IDocument) item;
				//convert and import
				Scenario scenar=new Scenario(newItem.getName());
				scenar.setDocRessource(newItem);
				for (Iterator iterator = newItem.iterator(); iterator.hasNext();) {
					IDocRessource docRessource = (IDocRessource) iterator
							.next();
					Sequence newSeq=new Sequence(docRessource.getName());
					newSeq.setDocRessource(docRessource);
					scenar.getSequences().add(newSeq);
				}
				adaptedItem.add(scenar);
				return true;
			}
			return false;
		}
		if (adapted instanceof Locations){
			Locations adaptedItem=(Locations)adapted;
			if (item instanceof IDocument){
				IDocument newItem=(IDocument) item;
				//convert and import
				Location lieu=new Location(newItem.getName());
				lieu.setDocRessource(newItem);
				for (Iterator iterator = newItem.iterator(); iterator.hasNext();) {
					IDocRessource docRessource = (IDocRessource) iterator
							.next();
					Location newLoc=new Location(docRessource.getName());
					newLoc.setDocRessource(docRessource);
					lieu.getSubLocations().add(newLoc);
				}
				adaptedItem.add(lieu);
				return true;
			}
			if (item instanceof IDocRessource){
				IDocRessource newItem=(IDocRessource) item;
				//convert and import
				Location lieu=new Location(newItem.getName());
				lieu.setDocRessource(newItem);
				adaptedItem.add(lieu);
				return true;
			}
			return false;
		}
		if (adapted instanceof Location){
			Location adaptedItem=(Location)adapted;
			if (item instanceof IDocument){
				IDocument newItem=(IDocument) item;
				//convert and import
				Location lieu=new Location(newItem.getName());
				lieu.setDocRessource(newItem);
				for (Iterator iterator = newItem.iterator(); iterator.hasNext();) {
					IDocRessource docRessource = (IDocRessource) iterator
							.next();
					Location newLoc=new Location(docRessource.getName());
					newLoc.setDocRessource(docRessource);
					lieu.getSubLocations().add(newLoc);
				}
				adaptedItem.getSubLocations().add(lieu);
				return true;
			}
			if (item instanceof IDocRessource){
				IDocRessource newItem=(IDocRessource) item;
				//convert and import
				Location lieu=new Location(newItem.getName());
				lieu.setDocRessource(newItem);
				adaptedItem.getSubLocations().add(lieu);
				return true;
			}
			return false;
		}
		if (adapted instanceof Groups){
			Groups adaptedItem=(Groups)adapted;
			if (item instanceof IDocument){
				IDocument newItem=(IDocument) item;
				//convert and import
				Group groupe=new Group(newItem.getName());
				//groupe.setDocRessource(newItem);
				for (Iterator iterator = newItem.iterator(); iterator.hasNext();) {
					IDocRessource docRessource = (IDocRessource) iterator
							.next();
					Actor newActor=new Actor(docRessource.getName());
					newActor.setDocRessource(docRessource);
					groupe.add(newActor);
				}
				adaptedItem.add(groupe);
				return true;
			}
			return false;
		}
		if (adapted instanceof Group){
			Group adaptedItem=(Group)adapted;
			if (item instanceof IDocument){
				IDocument newItem=(IDocument) item;
				//convert and import
				Group groupe=new Group(newItem.getName());
				//groupe.setDocRessource(newItem);
				for (Iterator iterator = newItem.iterator(); iterator.hasNext();) {
					IDocRessource docRessource = (IDocRessource) iterator
							.next();
					Actor newActor=new Actor(docRessource.getName());
					newActor.setDocRessource(docRessource);
					groupe.add(newActor);
				}
				adaptedItem.getSubGroups().add(groupe);
				return true;
			}
			if (item instanceof IDocRessource){
				IDocRessource newItem=(IDocRessource) item;
				//convert and import
				Actor acteur=new Actor(newItem.getName());
				acteur.setDocRessource(newItem);
				adaptedItem.add(acteur);
				return true;
			}
			return false;
		}
		return false;
		
	}	
	
}
