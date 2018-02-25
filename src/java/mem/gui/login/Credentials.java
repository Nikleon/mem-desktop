package mem.gui.login;

public class Credentials {

	private final String username;
	private final String password;

	public Credentials(String user, String pass) {
		this.username = user;
		this.password = pass;
	}

	public String username() {
		return username;
	}

	public String password() {
		return password;
	}

}
