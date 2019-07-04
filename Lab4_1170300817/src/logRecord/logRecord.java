package logRecord;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class logRecord {
    String logType; //log类型
    String reason;
    String className; //类名
    String methodName; //方法名
    Instant time; //时间

    public logRecord(String line) {
        String regex = "TYPE\\((.*)\\) TIME\\((.*)\\) METHOD\\(:(.*)\\.(.*)\\(.*\\)\\) REASON\\((.*)\\)";
        Matcher matcher = Pattern.compile(regex).matcher(line);
        if(matcher.find()) {
            logType = matcher.group(1);
            className = matcher.group(3);
            methodName = matcher.group(4);
            reason = matcher.group(5);
            String timeStr = matcher.group(2);
            time = Instant.parse(timeStr.replace(' ','T')+"Z");
        }
    }

    @Override
    public String toString() {
        return String.format("错误类型:\t%s%n类名:\t%s%n方法名:\t%s%n时间:\t%s%n详细信息:\t%s%n%n",
                logType,className,methodName,time,reason);
    }

    public String getLogType() {
        return logType;
    }

    public String getReason() {
        return reason;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Instant getTime() {
        return time;
    }
}