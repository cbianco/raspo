package it.cbmz.raspo.api;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class KarafRegistrator {

	private static final Set<Session> clientSessions =
		Collections.synchronizedSet(new HashSet<Session>());

	public static void registerConnection(Session session) {
		session.setIdleTimeout(-1);
		clientSessions.add(session);

	}

	public static void unregisterConnection(Session session) {
		clientSessions.remove(session);
	}

	public static void onMessage(String message, Session client) {
		System.out.println(
			String.format(
				"message: %s, Client: %s ", message, client.getLocalAddress()));
	}


	public static void publishToAll(String message) {
		StringBuffer buf = new StringBuffer(message);
		synchronized (clientSessions) {
			System.out.println(
				"send message '" + message + "' to " + clientSessions.size() + " clients");

			Iterator<Session> it = clientSessions.iterator();
			while (it.hasNext()) {
				Session session = it.next();
				try {
					session.getRemote().sendString(buf.toString());
				} catch (IOException exception) {
					clientSessions.remove(session);
					it = clientSessions.iterator();
					try {
						session.close();
					} catch (Exception e) {
					}
				}
			}
		}

	}

}
