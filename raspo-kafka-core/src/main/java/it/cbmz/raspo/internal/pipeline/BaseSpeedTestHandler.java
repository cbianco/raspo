package it.cbmz.raspo.internal.pipeline;

import it.cbmz.raspo.internal.kafka.message.Message;
import it.cbmz.raspo.internal.pipeline.excpetion.PipelineException;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;
import it.cbmz.raspo.internal.util.Constants.Handler.EventKey;
import it.cbmz.raspo.internal.util.Constants.Handler;

import java.util.Collections;
import java.util.Map;

public abstract class BaseSpeedTestHandler implements EventHandler {

	protected Message getMessage(Event event) throws PipelineException {

		Object message = event.getProperty(EventKey.MESSAGE_KEY);

		if(message == null) {
			throw new PipelineException("message non può essere null");
		}

		if((message instanceof String)) {
			return Message.toMessage((String)message);
		}

		if(!(message instanceof Message)) {
			throw new PipelineException(
				"deve essere un'instanza di " + Message.class.getName());
		}

		return (Message)message;
	}

	protected void sendMessage(String topic, Message message) {
		_eventAdmin.sendEvent(new Event(topic, singletonMap(EventKey.MESSAGE_KEY, message.toJSON())));
	}

	protected final void onError(Throwable e) {

		_eventAdmin.sendEvent(new Event(
			Handler.ERROR_HANDLER,
			singletonMap(EventKey.EXCEPTION_KEY, e))
		);

	}

	protected final <K> Map<K, Object> singletonMap(K key, Object obj) {
		return Collections.singletonMap(key, obj);
	}

	protected void setEventAdmin(EventAdmin eventAdmin){
		_eventAdmin = eventAdmin;
	}

	protected EventAdmin _eventAdmin;

}
