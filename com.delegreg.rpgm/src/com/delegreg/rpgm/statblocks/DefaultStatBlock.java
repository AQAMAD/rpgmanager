package com.delegreg.rpgm.statblocks;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.BaseContaineableNameable;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.model.IStatBlock;
import com.delegreg.rpgm.ui.IImageKeys;

public class DefaultStatBlock extends BaseContaineableNameable implements IStatBlock {

	private int age;
	private float height;
	private float weight;
	private String behavior; 
	private String description;
	private String background;
	static Image icon=AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.STATBLOCK).createImage();;
	
	static String STABLOCK_NLSKEY="DefaultStatBlock"; //$NON-NLS-1$

	public DefaultStatBlock(String name) {
		this();
		this.setName(name);
	}

	public DefaultStatBlock() {
		super();
	}

	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getBehavior() {
		return behavior;
	}
	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}

	@Override
	public Image ClassIcon() {
		return icon;
	}

	@Override
	public String ClassName() {
		return Messages.getByName(STABLOCK_NLSKEY);
	}
	
			
	
}
