package it.cbmz.raspo.internal.util;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class MacAddress {

	public static String macAddress() {

		if(MAC_ADDRESS == null) {
			InetAddress ip;

			try {
				ip = InetAddress.getLocalHost();

				NetworkInterface network = NetworkInterface.getByInetAddress(ip);

				byte[] mac = network.getHardwareAddress();

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					sb.append(
						String.format(
							"%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
				}

				MAC_ADDRESS = sb.toString();

			}
			catch (Exception e){
				e.printStackTrace();
			}
		}

		return MAC_ADDRESS;

	}

	private static String MAC_ADDRESS;
}
