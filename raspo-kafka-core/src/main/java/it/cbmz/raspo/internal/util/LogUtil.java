package it.cbmz.raspo.internal.util;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

public class LogUtil {

	public static void info(String message) {
		getService().log(LogService.LOG_INFO, message);
	}

	public static void error(String message) {
		getService().log(LogService.LOG_ERROR, message);
	}

	public static void warn(String message) {
		getService().log(LogService.LOG_WARNING, message);
	}

	public static void info(String message, Throwable e) {
		getService().log(LogService.LOG_INFO, message, e);
	}

	public static void warn(String message, Throwable e) {
		getService().log(LogService.LOG_WARNING, message, e);
	}

	public static void error(String message, Throwable e) {
		getService().log(LogService.LOG_ERROR, message, e);
	}

	private static LogService getService() {
		return _serviceTracker.getService();
	}

	private static  ServiceTracker<LogService, LogService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(LogService.class);

		ServiceTracker<LogService, LogService> serviceTracker =
			new ServiceTracker<>(bundle.getBundleContext(),
				LogService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
