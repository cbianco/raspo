package it.cbmz.raspo.internal.pipeline;

import fr.bmartel.speedtest.SpeedTestSocket;
import it.cbmz.raspo.internal.kafka.message.Message;
import it.cbmz.raspo.internal.listener.CFSpeedListener;
import it.cbmz.raspo.internal.util.Constants;
import org.osgi.service.event.Event;

import java.util.concurrent.CompletableFuture;

public abstract class BaseSpeedTestHandler extends BasePipelineHandler {

	enum Type {

		DOWNLOAD("dw_speed"), UPLOAD("up_speed");

		private String _type;

		Type(String type) { _type = type; }
	}

	@Override
	final void doHandleEvent(Event event) throws Exception {

		Message message = getMessage(event);

		SpeedTestSocket speedTestSocket = new SpeedTestSocket();

		CompletableFuture<String> cf = new CompletableFuture<>();

		CFSpeedListener speedListener = new CFSpeedListener(cf);

		speedTestSocket.addSpeedTestListener(speedListener);

		Type type = addSpeedTestUrl(speedTestSocket);

		message.add(type._type, cf.get());

		speedTestSocket.closeSocket();

		sendMessage(Constants.Handler.UPLOAD_HANDLER, message);

	}

	abstract Type addSpeedTestUrl(SpeedTestSocket speedTestSocket);


}
