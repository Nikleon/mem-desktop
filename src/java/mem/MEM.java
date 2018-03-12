package mem;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mem.gui.CalendarController;
import mem.gui.GuiEvent;
import mem.gui.ModuleController;
import mem.gui.SettingsController;
import mem.gui.login.AccountManager;
import mem.gui.login.Credentials;
import mem.gui.login.LoginEvent;
import mem.gui.login.LoginPane;

public class MEM extends Application {

	public static enum MODULES {
		CHROME_EXTENSION, DESKTOP_APP_MONITOR;
	}

	private static final String LOGIN_TITLE = "MEM Login";
	private static final String TITLE = "Multi-tasking Enhancement Modules";
	private static final String CALENDAR_TITLE = "Calendar Import";

	private static final String FXML_SETTINGS_TABS = "settings_tabpane.fxml";
	private static final String FXML_MODULE_LIST = "module_vbox.fxml";
	private static final String FXML_CALENDAR_PANE = "calendar_anchorpane.fxml";

	private ClassLoader classLoader;

	private GridPane loginPane;
	private Credentials credentials;
	
	private TabPane settingsPane;
	private VBox modulePane;
	private AnchorPane calendarPane;

	@Override
	public void init() throws Exception {
		classLoader = this.getClass().getClassLoader();
		loginPane = new LoginPane();
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		// Set layout to contain: {loginPane}
		Scene scene = new Scene(loginPane);
		primaryStage.setScene(scene);

		primaryStage.setTitle(LOGIN_TITLE);
		primaryStage.setResizable(false);
		primaryStage.show();

		// If there is a login attempt, try to authenticate
		scene.addEventHandler(LoginEvent.TYPE_LOGIN_ATTEMPT, evt -> {
			credentials = evt.getCredentials();
			boolean valid = AccountManager.authenticate(credentials);

			if (valid) {
				openMainView(scene, primaryStage, credentials);
			} else {
				// Show 'invalid creds' message
				((LoginPane) evt.getSource()).displayErrorMessage();

				// TODO: Shake window with Timeline/KeyFrames
			}
		});

		// If there is a logout attempt, reset to login screen
		scene.addEventHandler(LoginEvent.TYPE_LOGOUT, evt -> {
			credentials = null;
			loginPane = new LoginPane();

			scene.setRoot(loginPane);
			primaryStage.setTitle(LOGIN_TITLE);
			primaryStage.sizeToScene();
			primaryStage.centerOnScreen();

			settingsPane = null;
			modulePane = null;
		});

		scene.addEventHandler(GuiEvent.TYPE_OPEN_CALENDAR, evt -> {
			try {
				initCalendarPane();
			} catch (IOException e) {
				e.printStackTrace();
			}

			scene.setRoot(calendarPane);
			primaryStage.setTitle(CALENDAR_TITLE);
			primaryStage.sizeToScene();
			primaryStage.centerOnScreen();

			settingsPane = null;
			modulePane = null;
		});

		scene.addEventHandler(GuiEvent.TYPE_CLOSE_CALENDAR, evt -> {
			openMainView(scene, primaryStage, credentials);

			calendarPane = null;
		});

	}

	private void initSettingsPane(Credentials creds) throws IOException {
		// Load settings pane from FXML file
		FXMLLoader settingsLoader = new FXMLLoader(classLoader.getResource(FXML_SETTINGS_TABS));
		settingsPane = settingsLoader.<TabPane>load();

		// Disable tabs and fetch settings as appropriate
		SettingsController settingsController = settingsLoader.<SettingsController>getController();
		settingsController.initTabs(creds);
	}

	private void initModulePane(Credentials creds) throws IOException {
		// Load module pane from FXML file
		FXMLLoader moduleLoader = new FXMLLoader(classLoader.getResource(FXML_MODULE_LIST));
		modulePane = moduleLoader.<VBox>load();

		// Toggle modules as appropriate
		ModuleController moduleController = moduleLoader.<ModuleController>getController();
		moduleController.initModules(creds);
	}

	private void initCalendarPane() throws IOException {
		FXMLLoader calendarLoader = new FXMLLoader(classLoader.getResource(FXML_CALENDAR_PANE));
		calendarPane = calendarLoader.<AnchorPane>load();
		CalendarController calendarController = calendarLoader.<CalendarController>getController();
		calendarController.initCalendar();
	}

	private void openMainView(Scene scene, Stage primaryStage, Credentials creds) {
		// Set up new layout elements: HBox{settingsTabs, userPane}
		try {
			initSettingsPane(creds);
			initModulePane(creds);
		} catch (IOException e) {
			e.printStackTrace();
		}


		HBox root = new HBox(settingsPane, new Separator(Orientation.VERTICAL), modulePane);
		root.setPadding(new Insets(10.0, 20.0, 10.0, 20.0));
		root.setSpacing(20.0);
		root.setPrefSize(loginPane.getWidth(), loginPane.getHeight());

		scene.setRoot(root);
		primaryStage.setTitle(TITLE);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();

		// Animate transition
		// TODO: Transition
	}

	public static void main(String[] args) {
		Application.launch();
	}

}
