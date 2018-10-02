package it.cbmz.raspo.internal.util;

import java.io.IOException;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.Enumeration;

public class MacAddress {

    public static void main ( String args[] ) {
         macAddress();
    }

    public static String macAddress() {

        if(MAC_ADDRESS == null) {
			//InetAddress ip;

			try {
				//ip = InetAddress.getLocalHost();

				//NetworkInterface network = NetworkInterface.getByName(ip);
                NetworkInterface network = _getOnlineNetwork();
				byte[] mac = network.getHardwareAddress();

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					sb.append(
						String.format(
							"%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
				}

				MAC_ADDRESS = sb.toString();
				System.out.println(MAC_ADDRESS);
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}

		return MAC_ADDRESS;

	}

	private static NetworkInterface _getOnlineNetwork() {
        Enumeration<NetworkInterface> interfaces;
        try {
            // iterate over the network interfaces known to java
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException se) {
            se.printStackTrace();
            return null;
        }
        for (NetworkInterface interface_ : Collections.list(interfaces)) {
            // we shouldn't care about loopback addresses
            try {
                if (interface_.isLoopback())
                    continue;

                // if you don't expect the interface to be up you can skip this
                // though it would question the usability of the rest of the code
                if (!interface_.isUp())
                    continue;
            } catch (SocketException se){
                se.printStackTrace();
            }
            // iterate over the addresses associated with the interface
            Enumeration<InetAddress> addresses = interface_.getInetAddresses();
            for (InetAddress address : Collections.list(addresses)) {
                // look only for ipv4 addresses
                if (address instanceof Inet6Address)
                    continue;

                // use a timeout big enough for your needs
                try {
                    if (!address.isReachable(3000))
                        continue;
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
                // java 7's try-with-resources statement, so that
                // we close the socket immediately after use
                try (SocketChannel socket = SocketChannel.open()) {
                    // again, use a big enough timeout
                    socket.socket().setSoTimeout(3000);

                    // bind the socket to your local interface
                    socket.bind(new InetSocketAddress(address, 8080));

                    // try to connect to *somewhere*
                    socket.connect(new InetSocketAddress("google.com", 80));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    continue;
                }

                System.out.format("ni: %s, ia: %s\n", interface_, address);

                // stops at the first *working* solution
                return interface_;
            }
        }

        return null;
    }
	private static String MAC_ADDRESS;
}
