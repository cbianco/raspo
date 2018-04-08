package it.cbmz.raspo.internal.pipeline;

import it.cbmz.raspo.internal.kafka.message.Message;
import it.cbmz.raspo.internal.util.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

@Component(
	immediate = true,
	property = {
		EventConstants.EVENT_TOPIC + "=" + Constants.Handler.START_SPEED_TEST_HANDLER
	},
	service = EventHandler.class
)
public class StartSpeedTestHandler extends BaseSpeedTestHandler {

	@Override
	public void handleEvent(Event event) {

		Message message =
			Message.of(Constants.Command.SPEED_TEST);

		_eventAdmin.sendEvent(
			new Event(
				Constants.Handler.DOWNLOAD_HANDLER,
				singletonMap(Constants.Handler.EventKey.MESSAGE_KEY, message)
			)
		);
	}

	@Override
	@Reference
	protected void setEventAdmin(EventAdmin eventAdmin) {
		super.setEventAdmin(eventAdmin);
	}

}
