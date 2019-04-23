package carrenting.server.logger;


import org.apache.log4j.Logger;

/**
 * The logger manager for the server, so that there's only one log in the server.
 * It can be retrieved to log messages.
 *
 */
public class ServerLogger {

	/**
	 * The logger to be used in the server.
	 */
	private static final Logger log;
	
	static {
		log = Logger.getLogger("ServerLog");
	}
	
	/**
	 * Retrieves the logger to be used in the server.
	 * @return a Logger object.
	 */
	public static Logger getLogger() {
//		log.info("Info example");
//		log.warn("warn example");
//		log.fatal("fatal example");
//		log.error("error example");
		return log;
	}
	
}