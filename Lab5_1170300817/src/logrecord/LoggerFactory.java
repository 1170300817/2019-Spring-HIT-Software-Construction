package logrecord;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerFactory {

  /**
   * Get own Log4j Logger.
   *
   * @param clazz the own class
   * @return Log4j.Logger instance
   */
  public static Logger createLogger(Class<?> clazz) {
    Logger logger = Logger.getLogger(clazz);
    PropertyConfigurator.configure("src/log4j.properties");

    return logger;
  }
}
