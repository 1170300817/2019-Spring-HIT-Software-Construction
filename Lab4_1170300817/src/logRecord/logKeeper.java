package logRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class logKeeper {

	private List<logRecord> records = new ArrayList<>();

	public logKeeper() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(new File("out/debug.log")));
		String fileline;
		while ((fileline = in.readLine()) != null) {
			logRecord record = new logRecord(fileline.trim());
			records.add(record);

		}
		in = new BufferedReader(new FileReader(new File("out/error.log")));
		while ((fileline = in.readLine()) != null) {
			logRecord record = new logRecord(fileline.trim());
			records.add(record);

		}
		records.sort((x, y) -> {
			return x.getTime().compareTo(y.getTime());
		});
	}

	public String getFilterLogs(Predicate<logRecord> predicate) {
		List<logRecord> logs = records.stream().filter(predicate).collect(Collectors.toList());
		StringBuilder sb = new StringBuilder();
		for (logRecord logRecord : logs) {
			sb.append(logRecord.toString());
		}
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		new logKeeper();

	}

}
