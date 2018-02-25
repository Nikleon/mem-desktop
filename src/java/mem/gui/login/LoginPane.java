package mem.gui.login;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Duration;

public class LoginPane extends GridPane {

	private static final Font TITLE_FONT = Font.font("Agency FB", 28.0);

	private static final String ERROR_MESSAGE = "Invalid login. Try again.";
	private static final Font ERROR_FONT = Font.font("Calibri", FontPosture.ITALIC, 14.0);

	private Label title;
	private Label errorNotif;
	private TextField usernameInput, passwordInput;

	public LoginPane() {
		super();
		this.setVgap(10.0);
		this.setHgap(10.0);
		this.setPadding(new Insets(20.0, 20.0, 30.0, 20.0));

		title = new Label("Account Login");
		title.setFont(TITLE_FONT);

		errorNotif = new Label(ERROR_MESSAGE);
		errorNotif.setFont(ERROR_FONT);
		errorNotif.setTextFill(Color.TRANSPARENT);

		VBox centeredText = new VBox(title, errorNotif);
		centeredText.setAlignment(Pos.TOP_CENTER);
		centeredText.setSpacing(5.0);
		this.add(centeredText, 0, 0);
		LoginPane.setColumnSpan(centeredText, 2);

		this.add(new Label("Username:"), 0, 1);

		usernameInput = new TextField();
		this.add(usernameInput, 1, 1);

		this.add(new Label("Password:"), 0, 2);

		passwordInput = new PasswordField();
		this.add(passwordInput, 1, 2);

		// Switch focus to password field on username-field return
		usernameInput.setOnAction(evt -> {
			passwordInput.requestFocus();
		});

		// Fire LOGIN_ATTEMPT event on password-field return
		passwordInput.setOnAction(evt -> {
			String user = usernameInput.getText();
			String pass = passwordInput.getText();
			this.fireEvent(new LoginEvent(LoginEvent.TYPE_LOGIN_ATTEMPT, this, new Credentials(user, pass)));
		});
	}

	public void displayErrorMessage() {
		if (errorNotif.getTextFill() == Color.RED)
			return;
		errorNotif.setTextFill(Color.RED);

		// Hide message after delay
		KeyFrame task = new KeyFrame(Duration.millis(1000), evt -> errorNotif.setTextFill(Color.TRANSPARENT));
		new Timeline(task).play();
	}

}
