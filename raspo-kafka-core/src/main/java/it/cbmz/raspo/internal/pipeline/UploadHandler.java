package it.cbmz.raspo.internal.pipeline;

import fr.bmartel.speedtest.SpeedTestSocket;
import it.cbmz.raspo.internal.util.Constants;
import it.cbmz.raspo.internal.util.Constants.Handler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

@Component(
	immediate = true,
	property = {
		EventConstants.EVENT_TOPIC + "=" + Handler.UPLOAD_HANDLER
	},
	service = EventHandler.class
)
public class UploadHandler extends BaseSpeedTestHandler {

	@interface Config{
		String url() default "http://ipv4.ikoula.testdebit.info/";
		int fileSize() default 1000000;
	}

	@Activate
	@Modified
	protected void update(UploadHandler.Config config){
		_url = config.url();
		_fileSize = config.fileSize();
	}

	@Override
	String topicToSend() {
		return Handler.SEND_HANDLER;
	}

	@Override
	Type addSpeedTestUrl(SpeedTestSocket speedTestSocket) {

		speedTestSocket.startUpload(_url, _fileSize);

		return Type.UPLOAD;
	}

	@Override
	@Reference
	protected void setEventAdmin(EventAdmin eventAdmin) {
		super.setEventAdmin(eventAdmin);
	}

	private String _url;

	private int _fileSize;

}
