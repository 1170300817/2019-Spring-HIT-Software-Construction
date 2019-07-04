package logrecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LogKeeper {

  private List<LogRecord> records = new ArrayList<>();

  /**
   * 构造方法.
   * 
   * @throws IOException 读取日志出现文件错误
   */
  public LogKeeper() throws IOException {
    BufferedReader in = new BufferedReader(new FileReader(new File("out/debug.log")));
    String fileline;
    while ((fileline = in.readLine()) != null) {
      LogRecord record = new LogRecord(fileline.trim());
      records.add(record);

    }
    in = new BufferedReader(new FileReader(new File("out/error.log")));
    while ((fileline = in.readLine()) != null) {
      LogRecord record = new LogRecord(fileline.trim());
      records.add(record);

    }
    records.sort((x, y) -> {
      return x.getTime().compareTo(y.getTime());
    });
  }

  /**
   * 筛选日志条目的函数.
   * 
   * @param predicate 筛选条件
   * @return
   */
  public String getFilterLogs(Predicate<LogRecord> predicate) {
    List<LogRecord> logs = records.stream().filter(predicate).collect(Collectors.toList());
    StringBuilder sb = new StringBuilder();
    for (LogRecord logRecord : logs) {
      sb.append(logRecord.toString());
    }
    return sb.toString();
  }

  public static void main(String[] args) throws IOException {
    new LogKeeper();

  }

}
