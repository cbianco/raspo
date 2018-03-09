package it.cbmz.raspo.api;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class KarafWebSocket {

	@OnWebSocketConnect
	public void onOpen(Session session) {
		KarafRegistrator.registerConnection(session);
	}

	@OnWebSocketClose
	public void onClose(Session session, int statusCode, String reason) {
		KarafRegistrator.unregisterConnection(session);
	}

	@OnWebSocketMessage
	public void onText(Session session, String message) {
		KarafRegistrator.onMessage(message, session);
	}
}