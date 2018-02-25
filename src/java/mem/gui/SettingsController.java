package mem.gui;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.validator.routines.UrlValidator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import mem.gui.login.Credentials;
import mem.utils.LinkUtils;

public class SettingsController {

	@FXML
	private BorderPane root;

	@FXML
	private ListView<Hyperlink> whitelist, blacklist;

	@FXML
	private TextField whitelistInput, blacklistInput;

	@FXML
	private void initialize() {
		linkListInput(whitelist, whitelistInput);
		linkListInput(blacklist, blacklistInput);
	}

	private void linkListInput(ListView<Hyperlink> list, TextField input) {
		input.setOnAction(evt -> {
			try {
				String enteredText = LinkUtils.ensureProtocol(input.getText());
				if (!UrlValidator.getInstance().isValid(enteredText))
					throw new MalformedURLException();

				list.getItems().add(new WebsiteEntry(list, enteredText));
				input.clear();

			} catch (MalformedURLException | URISyntaxException e) {
				// Flash text field red to indicate error
				input.setEffect(new ColorAdjust(0, .25, 0, 0));

				// Remove effect after a short delay
				KeyFrame task = new KeyFrame(Duration.millis(200), onFinishEvt -> input.setEffect(null));
				new Timeline(task).play();
			}
		});
	}

	public void initTabs(Credentials creds) {
		// TODO: disable tabs and fetch settings as appropriate
	}

	class WebsiteEntry extends Hyperlink {
		private URI uri;

		public WebsiteEntry(ListView<Hyperlink> parent, String link) throws URISyntaxException {
			super(link);

			this.uri = new URI(link);

			// Display simplified link
			setText(LinkUtils.getSimple(link));

			// Open link in default browser when clicked
			this.setOnAction(evt -> {
				try {
					if (SystemUtils.IS_OS_LINUX) // If running on Linux
						Runtime.getRuntime().exec("xdg-open " + uri);
					else if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE))
						Desktop.getDesktop().browse(uri);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			// Remove link from list when right-clicked
			this.setOnMouseClicked(evt -> {
				if (evt.getButton() == MouseButton.SECONDARY)
					parent.getItems().remove(this);
			});

			setFocusTraversable(false);
		}

		@Override
		public void fire() {
			super.fire();
			setVisited(false);
		}
	}

}
