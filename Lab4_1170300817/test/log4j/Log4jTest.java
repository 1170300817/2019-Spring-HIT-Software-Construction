package log4j;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.Instant;

import org.junit.Test;

import logRecord.logKeeper;
import logRecord.logRecord;

public class Log4jTest {

	logRecord log1 = new logRecord(
			"TYPE(DEBUG) TIME(2019-05-19 01:17:35) METHOD(:applications.AtomStructure.AtomGame.gameMain(AtomGame.java:82)) REASON(文件读取成功)\r\n");
	logRecord log2 = new logRecord(
			"TYPE(DEBUG) TIME(2019-05-19 01:17:38) METHOD(:applications.AtomStructure.AtomGame.gameMain(AtomGame.java:95)) REASON(跃迁成功:从track0到track2)\r\n");
	logRecord log3 = new logRecord(
			"TYPE(DEBUG) TIME(2019-05-19 02:05:14) METHOD(:applications.AtomStructure.AtomGame.gameMain(AtomGame.java:87)) REASON(文件读取成功)\r\n");

	@Test
	public void log4jTest1() {
		assertTrue(log3.toString().contains("类名:	applications.AtomStructure.AtomGame"));
		assertTrue(log3.toString().contains("时间:	2019-05-19T02:05:14Z"));
		assertTrue(log3.toString().contains("详细信息:	文件读取成功"));
	}
	@Test
	public void log4jTest2() {
		assertTrue(log3.getClassName().equals("applications.AtomStructure.AtomGame"));
		assertTrue(log3.getLogType().equals("DEBUG"));
		assertTrue(log3.getReason().equals("文件读取成功"));
		assertTrue(log3.getMethodName().equals("gameMain"));
	}
	@Test
	public void log4jFliterTest() throws IOException {
		 logKeeper LOGKEEPER=new logKeeper();
		String inputString1="2019-05-19 01:17:35";
		String inputString2="2019-05-19 02:05:14";
		final Instant time1 = Instant.parse(inputString1.replace(' ','T')+"Z");
		final Instant time2 = Instant.parse(inputString2.replace(' ','T')+"Z");
		String resultString=LOGKEEPER.getFilterLogs(p -> p.getTime().isAfter(time1)&&p.getTime().isBefore(time2));
		assertTrue(resultString.contains("详细信息:	跃迁成功:从track0到track2"));

		
	}

}
