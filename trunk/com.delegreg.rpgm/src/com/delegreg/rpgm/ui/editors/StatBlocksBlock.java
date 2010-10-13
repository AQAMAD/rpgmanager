/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.delegreg.rpgm.ui.editors;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.model.Actor;
//import com.delegreg.rpgm.model.DefaultStatBlock;
import com.delegreg.rpgm.model.IStatBlock;
import com.delegreg.rpgm.model.StatBlocks;
import com.delegreg.rpgm.ui.IImageKeys;
/**
 *
 */
public class StatBlocksBlock extends MasterDetailsBlock {
	private FormPage page;
	private TableViewer viewer;
	public StatBlocksBlock(FormPage page) {
		this.page = page;
	}
	/**
	 * @param id
	 * @param title
	 */
	class MasterContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof IEditorInput) {
				IEditorInput input = (IEditorInput) page
						.getEditor().getEditorInput();
				return ((Actor)input.getAdapter(Actor.class)).getStatBlocks().toArray();
			}
			return new Object[0];
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	class MasterLabelProvider extends LabelProvider
			implements
				ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			if (obj instanceof IStatBlock) {
				//return PlatformUI.getWorkbench().getSharedImages().getImage();
				return ((IStatBlock)obj).getName();
			}
			return obj.toString();
		}
		public Image getColumnImage(Object obj, int index) {
			if (obj instanceof IStatBlock) {
				return ((IStatBlock)obj).ClassIcon();
			}
			return null;
		}
	}
	protected void createMasterPart(final IManagedForm managedForm,
			Composite parent) {
		//read the active statblocks description
		HashMap<Class, Class> statBlocks=Application.getStatBlocks();
		//final ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.DESCRIPTION|Section.TITLE_BAR);
		section.setText("Statblocks for this actor"); //$NON-NLS-1$
		section.setDescription("Manage the statblocks for your actor, statblocks can be system specific depending on the RPG system plugins you have installed."); //$NON-NLS-1$
		section.marginWidth = 10;
		section.marginHeight = 5;
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		Table t = toolkit.createTable(client, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		gd.verticalSpan = statBlocks.size()+1;
		t.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		for (Iterator it = statBlocks.keySet().iterator(); it.hasNext();) {
			Class sb = (Class) it.next();
			IStatBlock isb;
			try {
				isb = (IStatBlock) sb.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}
			Button b = toolkit.createButton(client, "Add " + isb.ClassName(), SWT.PUSH); //$NON-NLS-1$
			b.setToolTipText("Create a new " + isb.ClassName());
			b.setImage(isb.ClassIcon());
			b.setData(sb);
			b.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Replace Auto-generated method stub with statblock type selection
					IEditorInput input = (IEditorInput) page.getEditor().getEditorInput();
					Class sb=(Class) e.widget.getData();
					StatBlocks blocks=((Actor)input.getAdapter(Actor.class)).getStatBlocks();
					IStatBlock isb;
					try {
						isb = (IStatBlock) sb.newInstance();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return;
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return;
					}
					isb.setName(isb.ClassName() + " n°" + (blocks.size()+1));
					blocks.add(isb);
					viewer.refresh();
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
			b.setLayoutData(gd);
		}
		
		Button b2 = toolkit.createButton(client, "Remove", SWT.PUSH); //$NON-NLS-1$
		b2.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.DELETEITEM).createImage());
		b2.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Replace Auto-generated method stub with statblock type selection
				IEditorInput input = (IEditorInput) page
				.getEditor().getEditorInput();
				StatBlocks blocks=((Actor)input.getAdapter(Actor.class)).getStatBlocks();
				StructuredSelection sel=(StructuredSelection) viewer.getSelection();
				for (Iterator iterator = sel.iterator(); iterator
						.hasNext();) {
					IStatBlock sb = (IStatBlock) iterator.next();
					if (blocks.contains(sb)){
						blocks.remove(sb);
					}
				}
				viewer.refresh();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		b2.setLayoutData(gd);

		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TableViewer(t);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
				viewer.refresh();
			}
		});
		viewer.setContentProvider(new MasterContentProvider());
		viewer.setLabelProvider(new MasterLabelProvider());
		viewer.setInput(page.getEditor().getEditorInput());
	}
	protected void createToolBarActions(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		Action haction = new Action("hor", Action.AS_RADIO_BUTTON) { //$NON-NLS-1$
			public void run() {
				sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		haction.setChecked(true);
		haction.setToolTipText("Horizontal presentation"); //$NON-NLS-1$
		haction.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				Application.PLUGIN_ID, IImageKeys.HORIZONTAL));
		Action vaction = new Action("ver", Action.AS_RADIO_BUTTON) { //$NON-NLS-1$
			public void run() {
				sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		vaction.setChecked(false);
		vaction.setToolTipText("Vertical presentation"); //$NON-NLS-1$
		vaction.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				Application.PLUGIN_ID, IImageKeys.VERTICAL));
		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);
	}
	protected void registerPages(DetailsPart detailsPart) {
		//read the active statblocks description
		HashMap<Class, Class> statBlocks=Application.getStatBlocks();
		for (Iterator it = statBlocks.keySet().iterator(); it.hasNext();) {
			Class sb = (Class) it.next();
			Class dp = statBlocks.get(sb);
			try {
				detailsPart.registerPage(sb, (IDetailsPage)dp.newInstance());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
		//detailsPart.registerPage(DefaultStatBlock.class, new DefaultStatBlockDetailsPage());
		//detailsPart.registerPage(TypeTwo.class, new TypeTwoDetailsPage());
	}
}