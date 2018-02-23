package mem.utils;

public class LinkUtils {

	public static final Protocol DEFAULT_PROTOCOL = Protocol.HTTP;

	public enum Protocol {
		HTTP("http"), HTTPS("https"), FTP("ftp");

		private final String value;

		private Protocol(String str) {
			this.value = str;
		}

		public String getString() {
			return value;
		}
	}

	public static boolean isValid(String link) {
		if (link == null)
			return false;

		String protocolMarker = "://";
		if (link.contains(protocolMarker))
			link = link.substring(link.indexOf(protocolMarker) + protocolMarker.length());

		String[] components = link.split("/")[0].split(".");
		if (components.length < 2)
			return false;

		return true;
	}

	public static String ensureProtocol(String link) {
		if (link == null)
			throw new IllegalArgumentException();

		String check = link.split("://")[0];

		for (Protocol p : Protocol.values())
			if (check.equals(p.getString()))
				return link;

		link = DEFAULT_PROTOCOL.getString() + "://" + link;
		return link;
	}

	public static String getSimple(String link) {
		if (link == null)
			throw new IllegalArgumentException();

		for (Protocol p : Protocol.values())
			link = link.replace(p.getString() + "://", "");

		if (link.indexOf("www.") == 0)
			link = link.substring("www.".length());

		return link;
	}

}
