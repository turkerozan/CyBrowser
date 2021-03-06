package edu.ucsf.rbvi.cyBrowser.internal;

import java.util.Properties;

import static org.cytoscape.work.ServiceProperties.COMMAND;
import static org.cytoscape.work.ServiceProperties.COMMAND_DESCRIPTION;
import static org.cytoscape.work.ServiceProperties.COMMAND_NAMESPACE;
import static org.cytoscape.work.ServiceProperties.ENABLE_FOR;
import static org.cytoscape.work.ServiceProperties.INSERT_SEPARATOR_BEFORE;
import static org.cytoscape.work.ServiceProperties.IN_MENU_BAR;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;

import org.cytoscape.application.swing.CySwingApplication;

import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.TaskFactory;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import edu.ucsf.rbvi.cyBrowser.internal.tasks.HideBrowserTaskFactory;
import edu.ucsf.rbvi.cyBrowser.internal.tasks.StartBrowserTaskFactory;

import edu.ucsf.rbvi.cyBrowser.internal.model.CyBrowserManager;

public class CyActivator extends AbstractCyActivator {
	public CyActivator() {
		super();
	}

	public void start(BundleContext bc) {

		// See if we have a graphics console or not
		boolean haveGUI = true;
		ServiceReference ref = bc.getServiceReference(CySwingApplication.class.getName());

		if (ref == null) {
			haveGUI = false;
			// Issue error and return
		}

		// Get a handle on the CyServiceRegistrar
		CyServiceRegistrar registrar = getService(bc, CyServiceRegistrar.class);

		CyBrowserManager manager = new CyBrowserManager(registrar);

		{
			StartBrowserTaskFactory startBrowser = new StartBrowserTaskFactory(registrar, manager, true);
			Properties props = new Properties();
			props.setProperty(PREFERRED_MENU, "Apps.CyBrowser");
			props.setProperty(TITLE, "Launch Cytoscape web browser");
			props.setProperty(MENU_GRAVITY, "1.0");
			props.setProperty(IN_MENU_BAR, "true");
			props.setProperty(COMMAND_NAMESPACE, "cybrowser");
			props.setProperty(COMMAND, "dialog");
			registerService(bc, startBrowser, TaskFactory.class, props);
		}

		{
			StartBrowserTaskFactory startBrowser = new StartBrowserTaskFactory(registrar, manager, false);
			Properties props = new Properties();
			props.setProperty(COMMAND_NAMESPACE, "cybrowser");
			props.setProperty(COMMAND, "show");
			registerService(bc, startBrowser, TaskFactory.class, props);
		}
		
		{
			HideBrowserTaskFactory hideBrowser = new HideBrowserTaskFactory(registrar, manager);
			Properties props = new Properties();
			props.setProperty(COMMAND_NAMESPACE, "cybrowser");
			props.setProperty(COMMAND, "hide");
			registerService(bc, hideBrowser, TaskFactory.class, props);
		}
		
		/*
		{
			// Register our "Add Nodes" factory
			StartBrowserTaskFactory startBrowser = new StartBrowserTaskFactory(registrar, false);
			Properties props = new Properties();
			props.setProperty(PREFERRED_MENU, "Apps.CyBrowserResultsPanel");
			props.setProperty(TITLE, "Add CyBrowser to the results panel");
			props.setProperty(MENU_GRAVITY, "2.0");
			props.setProperty(IN_MENU_BAR, "true");
			registerService(bc, startBrowser, TaskFactory.class, props);
		}
		*/

		// manager.info("WebResultPanel initialized");
	}

}
