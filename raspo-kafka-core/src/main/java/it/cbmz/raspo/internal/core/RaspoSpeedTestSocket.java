package it.cbmz.raspo.internal.core;

import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import org.osgi.service.component.annotations.*;

@Component(
	immediate = true
)
//@Designate(ocd = RaspoSpeedTestConfiguration.class)
public class RaspoSpeedTestSocket extends SpeedTestSocket {

	@interface Config {
		int interval() default 30000;
		String uriSpeedTest() default "http://ipv4.ikoula.testdebit.info/1M.iso";
	}

	@Activate
	public void activate(RaspoSpeedTestSocket.Config configuration) {

		startDownload(configuration.uriSpeedTest(), configuration.interval());
	}

	@Modified
	public void modified(RaspoSpeedTestSocket.Config configuration) {

		deactivate();

		activate(configuration);

	}

	@Deactivate
	public void deactivate() {
		shutdownAndWait();
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unsetISpeedTestListener"
	)
	protected void setISpeedTestListener(
		ISpeedTestListener iSpeedTestListener) {

		addSpeedTestListener(iSpeedTestListener);
	}

	protected void unsetISpeedTestListener(
		ISpeedTestListener iSpeedTestListener) {

		removeSpeedTestListener(iSpeedTestListener);
	}

}
