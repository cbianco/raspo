package it.cbmz.raspo.internal.pipeline;

import fr.bmartel.speedtest.SpeedTestSocket;
import it.cbmz.raspo.internal.util.Constants;
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
		EventConstants.EVENT_TOPIC + "=" + Constants.Handler.DOWNLOAD_HANDLER
	},
	service = EventHandler.class
)
public class DownloadHandler extends BaseSpeedTestHandler {

	@interface Config{
		String url() default "http://ipv4.ikoula.testdebit.info/1M.iso";
	}

	@Activate
	@Modified
	protected void update(DownloadHandler.Config config){
		_url = config.url();
	}

	@Override
	Type addSpeedTestUrl(SpeedTestSocket speedTestSocket) {

		speedTestSocket.startDownload(_url);

		return Type.DOWNLOAD;
	}


	@Override
	@Reference
	protected void setEventAdmin(EventAdmin eventAdmin) {
		super.setEventAdmin(eventAdmin);
	}

	private String _url;

}
