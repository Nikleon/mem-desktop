package mem.gui;

import java.util.HashMap;

public class ModuleSettings extends HashMap<String, Integer> {
	private static final long serialVersionUID = 1L;

	public static final int SETTING_OFF = 0;
	public static final int SETTING_ON = 1;

	public ModuleSettings() {
		super();
	}

	public void parse(String jsonSettings) {
		// TODO: parse with GSON and put into map
	}

}
