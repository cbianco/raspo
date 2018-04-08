package it.cbmz.raspo.internal.pipeline;

import fr.bmartel.speedtest.SpeedTestSocket;
import it.cbmz.raspo.internal.kafka.message.Message;
import it.cbmz.raspo.internal.listener.CFSpeedListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import it.cbmz.raspo.internal.util.Constants.Handler;

import java.util.concurrent.CompletableFuture;

@Component(
	immediate = true,
	property = {
		EventConstants.EVENT_TOPIC + "=" + Handler.UPLOAD_HANDLER
	},
	service = EventHandler.class
)
public class UploadHandler extends BaseSpeedTestHandler {

	@Override
	public void handleEvent(Event event) {
		try {
			Message message = getMessage(event);

			SpeedTestSocket speedTestSocket = new SpeedTestSocket();

			CompletableFuture<String> stringCompletableFuture =
				new CompletableFuture<>();

			CFSpeedListener uploadSpeedListener =
				new CFSpeedListener(stringCompletableFuture);

			speedTestSocket.addSpeedTestListener(uploadSpeedListener);

			speedTestSocket.startUpload(
				"http://ipv4.ikoula.testdebit.info/",
				1000000);

			message.add("up_speed", stringCompletableFuture.get());

			sendMessage(Handler.SEND_HANDLER, message);

		}
		catch (Throwable e) {
			onError(e);
		}

	}

	@Override
	@Reference
	protected void setEventAdmin(EventAdmin eventAdmin) {
		super.setEventAdmin(eventAdmin);
	}


}
