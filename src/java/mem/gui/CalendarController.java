package mem.gui;

import java.io.IOException;
import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

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
	private Button filterButton;

	@FXML
	private ListView<HBox> taskView_to;

	@FXML
	private ListView<HBox> taskView_from;

	@FXML
	private void initialize() {

	}

	public void initCalendar() {

		numInput.setText("10");

		backButton.setOnAction(evt -> {
			root.fireEvent(new GuiEvent(GuiEvent.TYPE_CLOSE_CALENDAR, root));
		});

		syncButton.setOnAction(evt -> {
			int userNum = Integer.parseInt(numInput.getText().split("\n")[0]);
			if (userNum <= 0 || userNum > 1000) {
				userNum = 10;
			}
			List<Event> items = null;
			try {
				items = Quickstart.quickstart(userNum);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (items.size() == 0) {
				System.out.println("No upcoming events found.");
			} else {
				System.out.println("Upcoming events");
				for (Event event : items) {
					taskView_from.getItems().add(new TaskEntry(event.getSummary(), event.getStart().getDate()));
				}
			}
		});

		filterButton.setOnAction(evt -> {
			int selectedId = taskView_from.getSelectionModel().getSelectedIndex();
			if (selectedId != -1) {
				HBox itemToRemove = taskView_from.getSelectionModel().getSelectedItem();
				taskView_to.getItems().add(itemToRemove);

				int SelectedId_new = (selectedId == taskView_from.getItems().size() - 1) ? selectedId - 1 : selectedId;

				taskView_from.getItems().remove(selectedId);
				taskView_from.getSelectionModel().select(SelectedId_new);
			}
		});
	}

	class TaskEntry extends HBox {
		private Label title;
		private Label time;

		public TaskEntry(String title, DateTime startTime) {
			this.title = new Label(title);
			this.getChildren().add(this.title);

			if (startTime != null) {
				this.time = new Label(startTime.toString());
				this.getChildren().add(this.time);
			}

			this.title.setPrefWidth(400);
			this.setSpacing(60);
		}
	}
}
