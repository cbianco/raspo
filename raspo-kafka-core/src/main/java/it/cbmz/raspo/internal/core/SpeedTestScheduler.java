package it.cbmz.raspo.internal.core;


import it.cbmz.raspo.internal.util.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component(
	immediate = true/**,
	property = {
		"scheduler.period:Long=600",
		"scheduler.concurrent:Boolean=false",
		"scheduler.name=speedTest"
	}**/
)
public class SpeedTestScheduler implements Runnable {

	@Override
	public void run() {

		Map<String, Object> map = new HashMap<>(2, 2);

		map.put("command", Constants.Command.SPEED_TEST);

		map.put("topic", Constants.Handler.DOWNLOAD_HANDLER);

		_eventAdmin.postEvent(
			new Event(Constants.Handler.START_SPEED_TEST_HANDLER, map));

	}

	@Reference
	public void setLogService(LogService logService) {
		_log = logService;
	}

	@Reference
	private EventAdmin _eventAdmin;

	private LogService _log;
}