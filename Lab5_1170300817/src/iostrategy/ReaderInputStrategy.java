package iostrategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReaderInputStrategy implements InputStrategy {

  private InputStreamReader inputStreamReader;

  public ReaderInputStrategy(String fileName) throws IOException {
    inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
  }

  @Override
  public String readLine() throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    char c;
    int i;
    while ((i = inputStreamReader.read()) != -1) {
      c = (char) i;
      if (c == '\n' || c == '\r') {
        break;
      }
      stringBuilder.append(c);
    }
    return i == -1 ? null : stringBuilder.toString();
  }

  @Override
  public void close() throws IOException {
    inputStreamReader.close();
  }
}
