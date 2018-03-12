package mem.gui;

import java.io.IOException;
import java.util.List;

import com.google.api.services.calendar.model.Event;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

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
	private ListView<String> taskView_to;
	
	@FXML
	private ListView<String> taskView_from;
	
//	@FXML
//    private TableView<List<eventObject>> tableView_to;
//
//    @FXML
//    private TableView<List<eventObject>> tableView_from;
//    
//    @FXML
//    private TableColumn<eventObject, Date> timeView_from;
//
//    @FXML
//    private TableColumn<eventObject, String> taskView_from;
//    
//
//    @FXML
//    private TableColumn<eventObject, Date> timeView_to;
//    
//    @FXML
//    private TableColumn<eventObject, String> eventView_to;

	@FXML
	private void initialize() {

	}
	
//	public static class eventObject_from{
//	    private final String event;
//	    private final Date eventDate;
//	    
//	    private eventObject_from(String event, Date eventDate) {
//	        this.event = event;
//	        this.eventDate = eventDate;
//	    }
//	    
//	    public String getEvent() {
//	    	return taskView_from.get();
//	    }
//	    
//	    public Date getEventDate() {
//	    	return timeView_from.get();
//	    }
//	    
//	    public Date setEvent(String event) {
//	    	return taskView_from.set(event);
//	    }
//	    
//	    public Date setEventDate(Date eventDate) {
//	    	return taskView_from.set(eventDate);
//	    }
//	}
	
	public void initCalendar() {
		
		numInput.setText("10");
		
		backButton.setOnAction(evt -> {
			root.fireEvent(new GuiEvent(GuiEvent.TYPE_CLOSE_CALENDAR, root));
		});

		syncButton.setOnAction(evt -> {
			int userNum = Integer.parseInt(numInput.getText());
			if(userNum <= 0 || userNum > 1000) {
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
					taskView_from.getItems().add(event.getSummary());
				}
			}
		});
		
		filterButton.setOnAction(evt -> {
            int selectedId = taskView_from.getSelectionModel().getSelectedIndex();
            if (selectedId != -1) {
            	String itemToRemove = taskView_from.getSelectionModel().getSelectedItem();
                taskView_to.getItems().add(itemToRemove);
                
                int SelectedId_new =
                        (selectedId == taskView_from.getItems().size() - 1)
                                ? selectedId - 1
                                : selectedId;

                taskView_from.getItems().remove(selectedId);
                taskView_from.getSelectionModel().select(SelectedId_new);
            }
		});
	}
}
