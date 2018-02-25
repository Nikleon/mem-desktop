package mem.gui.login;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.layout.Pane;

public class LoginEvent extends Event {
	private static final long serialVersionUID = 1L;

	public static final EventType<LoginEvent> TYPE_LOGIN_ATTEMPT = new EventType<>("LOGIN_ATTEMPT");
	public static final EventType<LoginEvent> TYPE_LOGOUT = new EventType<>("LOGOUT");

	private final Pane source;
	private final Credentials credentials;

	public LoginEvent(EventType<LoginEvent> type, Pane src, Credentials creds) {
		super(type);

		this.source = src;
		this.credentials = creds;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public Pane getSource() {
		return source;
	}

}
