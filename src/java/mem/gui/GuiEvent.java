package mem.gui;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;

public class GuiEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	public static final EventType<GuiEvent> TYPE_OPEN_CALENDAR = new EventType<>(
			"OPEN_CALENDAR");
	public static final EventType<GuiEvent> TYPE_CLOSE_CALENDAR = new EventType<>(
			"CLOSE_CALENDAR");
	
	private final Node source;
	
	public GuiEvent(EventType<GuiEvent> type, Node src) {
		super(type);
		
		this.source = src;
	}
	
	public Node getSource() {
		return source;
	}
	
}
