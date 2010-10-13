package com.delegreg.rpgm.ui.editors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.delegreg.rpgm.model.Actor;
import com.delegreg.rpgm.model.Campaign;
import com.delegreg.rpgm.model.Location;
import com.delegreg.rpgm.model.Scenario;
import com.delegreg.rpgm.model.Sequence;

public class EditorInputFactory {

	public IEditorInput getEditorInput(Object adaptable) {
		if(adaptable instanceof Campaign)
			return new CampaignEditorInput((Campaign)adaptable);
		if(adaptable instanceof Scenario)
			return new ScenarioEditorInput((Scenario)adaptable);
		if(adaptable instanceof Location)
			return new LocationEditorInput((Location)adaptable);
		if(adaptable instanceof Actor)
			return new ActorEditorInput((Actor)adaptable);
		if(adaptable instanceof Sequence)
			return new SequenceEditorInput((Sequence)adaptable);
		return null;
		
	}

	private class CampaignEditorInput implements IEditorInput {
		private Campaign campagne;
		public CampaignEditorInput(Campaign campagne) {
			super();
			Assert.isNotNull(campagne);
			this.campagne = campagne;
		}

		@Override
		public boolean exists() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return campagne.getName();
		}

		@Override
		public IPersistableElement getPersistable() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getToolTipText() {
			// TODO Auto-generated method stub
			return campagne.getName();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object getAdapter(Class adapter) {
			// TODO Auto-generated method stub
			if(adapter == Campaign.class)
				return campagne;
			return null;
		}

		public boolean equals(Object obj) {
			if (super.equals(obj))
				return true;
			if (!(obj instanceof CampaignEditorInput))
				return false;
			CampaignEditorInput other = (CampaignEditorInput) obj;
			return campagne.equals(other.campagne);
		}

		public int hashCode() {
			return campagne.hashCode();
		}


	}	
	
	private class ScenarioEditorInput implements IEditorInput {
		private Scenario scenar;
		public ScenarioEditorInput(Scenario scenar) {
			super();
			Assert.isNotNull(scenar);
			this.scenar = scenar;
		}

		@Override
		public boolean exists() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return scenar.getName();
		}

		@Override
		public IPersistableElement getPersistable() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getToolTipText() {
			// TODO Auto-generated method stub
			return scenar.getName();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object getAdapter(Class adapter) {
			// TODO Auto-generated method stub
			if(adapter == Scenario.class)
				return scenar;
			return null;
		}

		public boolean equals(Object obj) {
			if (super.equals(obj))
				return true;
			if (!(obj instanceof ScenarioEditorInput))
				return false;
			ScenarioEditorInput other = (ScenarioEditorInput) obj;
			return scenar.equals(other.scenar);
		}

		public int hashCode() {
			return scenar.hashCode();
		}


	}	
	
	private class LocationEditorInput implements IEditorInput {
		private Location lieu;
		public LocationEditorInput(Location lieu) {
			super();
			Assert.isNotNull(lieu);
			this.lieu = lieu;
		}

		@Override
		public boolean exists() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return lieu.getName();
		}

		@Override
		public IPersistableElement getPersistable() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getToolTipText() {
			// TODO Auto-generated method stub
			return lieu.getName();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object getAdapter(Class adapter) {
			// TODO Auto-generated method stub
			if(adapter == Location.class)
				return lieu;
			return null;
		}

		public boolean equals(Object obj) {
			if (super.equals(obj))
				return true;
			if (!(obj instanceof LocationEditorInput))
				return false;
			LocationEditorInput other = (LocationEditorInput) obj;
			return lieu.equals(other.lieu);
		}

		public int hashCode() {
			return lieu.hashCode();
		}


	}	
	

	private class ActorEditorInput implements IEditorInput {
		private Actor personne;
		public ActorEditorInput(Actor personne) {
			super();
			Assert.isNotNull(personne);
			this.personne = personne;
		}

		@Override
		public boolean exists() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return personne.getName();
		}

		@Override
		public IPersistableElement getPersistable() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getToolTipText() {
			// TODO Auto-generated method stub
			return personne.getName();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object getAdapter(Class adapter) {
			// TODO Auto-generated method stub
			if(adapter == Actor.class)
				return personne;
			return null;
		}

		public boolean equals(Object obj) {
			if (super.equals(obj))
				return true;
			if (!(obj instanceof ActorEditorInput))
				return false;
			ActorEditorInput other = (ActorEditorInput) obj;
			return personne.equals(other.personne);
		}

		public int hashCode() {
			return personne.hashCode();
		}


	}	
	
	private class SequenceEditorInput implements IEditorInput {
		private Sequence sequ;
		public SequenceEditorInput(Sequence sequ) {
			super();
			Assert.isNotNull(sequ);
			this.sequ = sequ;
		}

		@Override
		public boolean exists() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return sequ.getName();
		}

		@Override
		public IPersistableElement getPersistable() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getToolTipText() {
			// TODO Auto-generated method stub
			return sequ.getName();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object getAdapter(Class adapter) {
			// TODO Auto-generated method stub
			if(adapter == Sequence.class)
				return sequ;
			return null;
		}

		public boolean equals(Object obj) {
			if (super.equals(obj))
				return true;
			if (!(obj instanceof SequenceEditorInput))
				return false;
			SequenceEditorInput other = (SequenceEditorInput) obj;
			return sequ.equals(other.sequ);
		}

		public int hashCode() {
			return sequ.hashCode();
		}

	}	
	
	
	
}
