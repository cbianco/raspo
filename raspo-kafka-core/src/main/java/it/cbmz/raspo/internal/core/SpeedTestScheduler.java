package it.cbmz.raspo.internal.core;


import it.cbmz.raspo.internal.util.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogService;

import java.util.Collections;

@Component(
	immediate = true,
	property = {
		"scheduler.period:Long=30",
		"scheduler.concurrent:Boolean=false",
		"scheduler.name=speedTest"
	}
)
public class SpeedTestScheduler implements Runnable {

	@Override
	public void run() {

		_eventAdmin.postEvent(
			new Event(
				Constants.Handler.START_SPEED_TEST_HANDLER,
				Collections.emptyMap())
		);

	}

	@Reference
	public void setLogService(LogService logService) {
		_log = logService;
	}

	@Reference
	private EventAdmin _eventAdmin;

	private LogService _log;
}