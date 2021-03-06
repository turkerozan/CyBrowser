package edu.ucsf.rbvi.cyBrowser.internal.tasks;

import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import edu.ucsf.rbvi.cyBrowser.internal.model.CyBrowserManager;

public class StartBrowserTaskFactory extends AbstractTaskFactory {

	CyServiceRegistrar registrar;
	final CyBrowserManager manager;
	boolean dialog;

	public StartBrowserTaskFactory(CyServiceRegistrar registrar, CyBrowserManager manager, boolean dialog) {
		this.registrar = registrar;
		this.dialog = dialog;
		this.manager = manager;
	}

	public boolean isReady() {
		return true;
	}

	public TaskIterator createTaskIterator() {
		return new TaskIterator(new StartBrowserTask(registrar, manager, dialog));
	}
}

