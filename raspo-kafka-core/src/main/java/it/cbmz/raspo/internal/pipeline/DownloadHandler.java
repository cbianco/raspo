package it.cbmz.raspo.internal.pipeline;

import fr.bmartel.speedtest.SpeedTestSocket;
import it.cbmz.raspo.internal.kafka.message.Message;
import it.cbmz.raspo.internal.listener.CFSpeedListener;
import it.cbmz.raspo.internal.util.Constants;
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
		EventConstants.EVENT_TOPIC + "=" + Constants.Handler.DOWNLOAD_HANDLER
	},
	service = EventHandler.class
)
public class DownloadHandler extends BaseSpeedTestHandler {

	@Override
	public void handleEvent(Event event) {

		try {
			Message message = getMessage(event);

			SpeedTestSocket speedTestSocket = new SpeedTestSocket();

			CompletableFuture<String> stringCompletableFuture =
				new CompletableFuture<>();

			CFSpeedListener downloadSpeedListener =
				new CFSpeedListener(stringCompletableFuture);

			speedTestSocket.addSpeedTestListener(downloadSpeedListener);

			speedTestSocket.startDownload(
				"http://ipv4.ikoula.testdebit.info/1M.iso");

			message.add("dw_speed", stringCompletableFuture.get());

			speedTestSocket.closeSocket();

			sendMessage(Constants.Handler.UPLOAD_HANDLER, message);

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
