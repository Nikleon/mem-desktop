package mem.gui.login;

import mem.MEM.MODULES;
import mem.gui.ModuleSettings;

public class AccountManager {

	public static boolean authenticate(Credentials creds) {
		return true; // TODO: Actually try to log in to something
	}

	public ModuleSettings getModuleSettings(MODULES module, Credentials creds) {
		ModuleSettings settings = null;

		switch (module) {
		case CHROME_EXTENSION:
			// TODO
			break;
		case DESKTOP_APP_MONITOR:
			// TODO
			break;
		}

		return settings;
	}

}
