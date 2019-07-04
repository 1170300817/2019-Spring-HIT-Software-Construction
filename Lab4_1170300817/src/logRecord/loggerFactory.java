package logRecord;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class loggerFactory {

  /**
   * Get own Log4j Logger.
   *
   * @param clazz the own class
   * @return Log4j.Logger instance
   */
  public static Logger createLogger(Class clazz) {
	  Logger logger = Logger.getLogger(clazz);
    PropertyConfigurator.configure("src/log4j.properties");
    
    return logger;
  }
}
