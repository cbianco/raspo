package it.cbmz.raspo.internal.core;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;

@Component(
	immediate = true,
	property = {
		"scheduler.period:Long=30",
		"scheduler.concurrent:Boolean=false",
		"scheduler.name=ping"
	}
)
public class PingScheduler implements Runnable {

	@Override
	public void run() {

		Future<RecordMetadata> send = _producer.send(new ProducerRecord<>(
			"test", "ping", "lallalero"));

		try {
			send.get();
		}
		catch (Exception e) {
			_log.log(LogService.LOG_INFO, e.getMessage(), e);
		}

		_log.log(LogService.LOG_INFO, "inviato");

	}

	@Reference
	public void setProducer(Producer<String, String> producer) {
		_producer = producer;
	}

	@Reference
	public void setLogService(LogService logService) {
		_log = logService;
	}

	private Producer<String, String> _producer;
	private LogService _log;
}
