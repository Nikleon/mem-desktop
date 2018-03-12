package mem.gui.login;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import mem.gui.GuiEvent;

public class CalendarController {
	
	@FXML
	private AnchorPane root;
	
	@FXML
	private TextArea numInput;
	
	@FXML
	private Button syncButton;
	
	@FXML
	private Button backButton;
	
	@FXML
	private ScrollPane taskPane_to;
	
	@FXML
	private Button filterButton;
	
	@FXML
	private ScrollPane taskPane_from;
	
	@FXML
	private void initialize() {
		
	}
	
	public void initCalendar() {
		backButton.setOnAction(evt -> {
			root.fireEvent(new GuiEvent(GuiEvent.TYPE_CLOSE_CALENDAR, root));
		});
	}
	
}
