package it.cbmz.raspo.internal.core;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.osgi.framework.*;
import org.osgi.framework.wiring.BundleWiring;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {

		Properties props = new Properties();

		Bundle bundle = context.getBundle();

		URL resource = bundle.getResource("raspo.properties");

		try(InputStream inputStream = resource.openStream()) {
			props.load(inputStream);
		}

		Thread thread = Thread.currentThread();

		ClassLoader currentClassLoader = thread.getContextClassLoader();

		try {
			BundleWiring adapt = bundle.adapt(BundleWiring.class);

			ClassLoader bundleClassLoader = adapt.getClassLoader();

			thread.setContextClassLoader(bundleClassLoader);

			Producer<String, String> producer = new KafkaProducer<>(props);

			producerServiceRegistration =
				context.registerService(Producer.class, producer, null);
		}
		finally {
			thread.setContextClassLoader(currentClassLoader);
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {

		ServiceReference<Producer> reference =
			producerServiceRegistration.getReference();

		Producer service = context.getService(reference);

		if(service != null) {
			service.close();
		}

		try {
			producerServiceRegistration.unregister();
		}
		catch (Exception e) {
			// servizio già disattivato
		}
		producerServiceRegistration = null;
	}

	private ServiceRegistration<Producer> producerServiceRegistration;
}
