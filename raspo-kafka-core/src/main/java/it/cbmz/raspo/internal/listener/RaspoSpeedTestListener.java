package it.cbmz.raspo.internal.listener;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import it.cbmz.raspo.internal.kafka.message.Message;
import it.cbmz.raspo.internal.util.Constants;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class RaspoSpeedTestListener implements ISpeedTestListener {

	@interface Config {
		String topic() default "test";
	}

	@Activate
	@Modified
	public void modified(RaspoSpeedTestListener.Config configuration) {
		_raspoProducerConfiguration = configuration;
	}

	@Override
	public void onCompletion(SpeedTestReport report) {

		Message speedTest =
			Message.of(Constants.SPEED_TEST,
				report.getTransferRateOctet().toString());

		_producer.send(new ProducerRecord<>(
			_raspoProducerConfiguration.topic(), speedTest.toJSON())
		);
	}

	@Override
	public void onProgress(float percent, SpeedTestReport report) {
	}

	@Override
	public void onError(SpeedTestError speedTestError, String errorMessage) {
		if(speedTestError != null) {
			switch (speedTestError) {
				case SOCKET_ERROR:
				case MALFORMED_URI:
				case SOCKET_TIMEOUT:
				case CONNECTION_ERROR:
				case UNSUPPORTED_PROTOCOL:
				case INVALID_HTTP_RESPONSE:
					_producer.send(new ProducerRecord<>(
						_raspoProducerConfiguration.topic(),
						"error: " + errorMessage)
					);
					break;
			}
		}
	}

	private Config _raspoProducerConfiguration;

	@Reference
	private Producer<String, String> _producer;
}
