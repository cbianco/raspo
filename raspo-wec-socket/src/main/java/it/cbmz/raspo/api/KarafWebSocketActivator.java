package it.cbmz.raspo.api;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class KarafWebSocketActivator implements BundleActivator {


	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("start bundle");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("stop bundle");
	}
}
