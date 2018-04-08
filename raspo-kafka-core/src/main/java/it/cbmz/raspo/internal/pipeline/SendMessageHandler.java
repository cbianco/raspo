package it.cbmz.raspo.internal.pipeline;

import it.cbmz.raspo.internal.kafka.message.Message;
import it.cbmz.raspo.internal.util.Constants;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.concurrent.CompletableFuture;

@Component(
	immediate = true,
	property = {
		EventConstants.EVENT_TOPIC + "=" + Constants.Handler.SEND_HANDLER
	},
	service = EventHandler.class
)
public class SendMessageHandler extends BaseSpeedTestHandler {

	@Override
	public void handleEvent(Event event) {

		try {

			Message message = getMessage(event);

			_producer.flush();

			CompletableFuture.supplyAsync(() ->
				_producer.send(new ProducerRecord<>(
					"client", message.toJSON())
				)
			);

		}
		catch (Throwable e) {
			onError(e);
		}

	}

	@Reference
	private Producer<String, String> _producer;

	@Override
	@Reference
	protected void setEventAdmin(EventAdmin eventAdmin) {
		super.setEventAdmin(eventAdmin);
	}


}
