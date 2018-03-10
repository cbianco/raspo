package it.cbmz.raspo.example.impl;

import it.cbmz.raspo.example.api.EchoApi;
import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	service = EchoApi.class
)
public class EchoImpl implements it.cbmz.raspo.example.api.EchoApi {

	public String echo() {
		return "echo";
	}
}
