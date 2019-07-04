package log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerFactory {
  /**
   * 工厂方法，获得Logger
   * 
   * @param clazz 类
   * @return Logger
   */
  public static Logger createLogger(Class<?> clazz) {
    Logger logger = Logger.getLogger(clazz);
    PropertyConfigurator.configure("src/log4j.properties");

    return logger;
  }
}
