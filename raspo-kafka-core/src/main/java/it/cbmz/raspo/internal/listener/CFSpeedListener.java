package it.cbmz.raspo.internal.listener;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

import java.util.concurrent.CompletableFuture;

public class CFSpeedListener implements ISpeedTestListener {

	private final CompletableFuture<String> _completableFuture;

	public CFSpeedListener(CompletableFuture<String> future) {
		_completableFuture = future;
	}

	@Override
	public void onCompletion(SpeedTestReport report) {
		_completableFuture.complete(
			report.getTransferRateOctet().toString());
	}

	@Override
	public void onProgress(float percent, SpeedTestReport report) {
		//NU SERVE
	}

	@Override
	public void onError(SpeedTestError speedTestError, String errorMessage) {
		//TODO IMPLEMENTARE GESTIONE ERRORI
	}

	/*@Override
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
	}*/
}
