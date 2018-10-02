package it.cbmz.raspo.internal.pipeline;

import it.cbmz.raspo.internal.kafka.message.Message;
import it.cbmz.raspo.internal.pipeline.excpetion.PipelineException;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;
import it.cbmz.raspo.internal.util.Constants.Handler.EventKey;
import it.cbmz.raspo.internal.util.Constants.Handler;
import it.cbmz.raspo.internal.util.LogUtil;

import java.util.Collections;
import java.util.Map;

public abstract class BasePipelineHandler implements EventHandler {

	@Override
	public final void handleEvent(Event event) {
		try {
			doHandleEvent(event);
		}
		catch(Exception pe) {
			onError(pe);
		}

	}

	abstract void doHandleEvent(Event event) throws Exception;

	Message getMessage(Event event) throws PipelineException {

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

	void sendMessage(String topic, Message message) {
		_eventAdmin.sendEvent(
			new Event(
				topic, singletonMap(EventKey.MESSAGE_KEY, message.toJSON())));
		LogUtil.info("Message event sent:\n ["+message.toJSON()+"]");
		
	}

	final void onError(Throwable e) {

		_eventAdmin.sendEvent(new Event(
			Handler.ERROR_HANDLER,
			singletonMap(EventKey.EXCEPTION_KEY, e))
		);

	}

	final <K> Map<K, Object> singletonMap(K key, Object obj) {
		return Collections.singletonMap(key, obj);
	}

	protected void setEventAdmin(EventAdmin eventAdmin){
		_eventAdmin = eventAdmin;
	}

	EventAdmin _eventAdmin;

}
