package it.cbmz.raspo.api;

import it.cbmz.raspo.example.api.EchoApi;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	property = {
		"osgi.command.function=sendMessage",
		"osgi.command.function=echo",
		"osgi.command.scope=ws"
	},
	service = Object.class,
	immediate = true
)
public class GogoMessage {

	public void sendMessage(String message) {

		it.cbmz.raspo.api.KarafRegistrator.publishToAll(message);
	}

	public void echo() {

		System.out.println(_echo.echo());
	}

	@Reference
	private EchoApi _echo;

}
