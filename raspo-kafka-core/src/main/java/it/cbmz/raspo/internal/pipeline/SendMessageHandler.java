package it.cbmz.raspo.internal.pipeline;

import it.cbmz.raspo.internal.kafka.message.Message;
import it.cbmz.raspo.internal.util.Constants;
import it.cbmz.raspo.internal.util.MacAddress;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
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
public class SendMessageHandler extends BasePipelineHandler {

	@interface Config {
		String topic() default "client";
	}

	@Override
	void doHandleEvent(Event event) throws Exception {

		Message message = getMessage(event);

		message.add("mac", MacAddress.macAddress());

		_producer.flush();

		CompletableFuture.supplyAsync(() ->
			_producer.send(new ProducerRecord<>(
				_topic, message.toJSON())
			)
		);

	}

	@Activate
	@Modified
	protected void update(SendMessageHandler.Config config){
		_topic = config.topic();
	}


	@Override
	@Reference
	protected void setEventAdmin(EventAdmin eventAdmin) {
		super.setEventAdmin(eventAdmin);
	}

	@Reference
	private Producer<String, String> _producer;

	private String _topic;

}
