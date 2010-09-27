package pinsgatherer.server;

import org.openqa.selenium.server.SeleniumServer;

public class ServerManager {
	
	private static SeleniumServer server;
	
	static {
		 try {
			server = new SeleniumServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void start() {
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void stop() {
		server.stop();
	}
}
