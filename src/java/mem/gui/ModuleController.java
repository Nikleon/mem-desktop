package mem.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import mem.gui.login.Credentials;
import mem.gui.login.LoginEvent;

public class ModuleController {

	private static final String NAME_FORMAT = "USER: %s";

	@FXML
	private VBox parent;

	@FXML
	private ToggleButton chromeToggle;
	@FXML
	private ToggleButton desktopToggle;
	@FXML
	private ToggleButton calendarToggle;

	@FXML
	private Label name;
	@FXML
	private Hyperlink logoutLink;

	@FXML
	private void initialize() {
		// Give logout button functionality
		logoutLink.setOnAction(evt -> {
			parent.fireEvent(new LoginEvent(LoginEvent.TYPE_LOGOUT, parent, null));
		});
	}

	public void initModules(Credentials creds) {
		name.setText(String.format(NAME_FORMAT, creds.username()));
	}

}
