package it.cbmz.raspo.internal.pipeline;

import it.cbmz.raspo.internal.kafka.message.Message;
import it.cbmz.raspo.internal.util.Constants;
import it.cbmz.raspo.internal.util.MacAddress;
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
public class StartPipelineHandler extends BasePipelineHandler {

	@Override
	void doHandleEvent(Event event) throws Exception {

		String command =(String)event.getProperty("command");

		String topic =(String)event.getProperty("topic");

		Message message = Message.of(command);

		sendMessage(topic, message);

	}

	@Override
	@Reference
	protected void setEventAdmin(EventAdmin eventAdmin) {
		super.setEventAdmin(eventAdmin);
	}

}
