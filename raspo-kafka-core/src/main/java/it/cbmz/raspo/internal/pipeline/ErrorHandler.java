package it.cbmz.raspo.internal.pipeline;


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
		EventConstants.EVENT_TOPIC + "=" + Constants.Handler.ERROR_HANDLER
	},
	service = EventHandler.class
)
public class ErrorHandler implements EventHandler {

	@Override
	public void handleEvent(Event event) {

		Object property =
			event.getProperty(Constants.Handler.EventKey.EXCEPTION_KEY);

		if(property instanceof Exception) {
			((Exception)property).printStackTrace();
		}
	}

	@Reference
	private EventAdmin _eventAdmin;

}
