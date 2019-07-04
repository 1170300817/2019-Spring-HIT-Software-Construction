package iostrategy;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class StreamInputStrategy implements InputStrategy {
  private DataInputStream dataInputStream;

  public StreamInputStrategy(String fileName) throws IOException {
    dataInputStream = new DataInputStream(new FileInputStream(fileName));
  }

  @Override
  @SuppressWarnings("deprecation")
  public String readLine() throws IOException {
    return dataInputStream.readLine();
  }

  @Override
  public void close() throws IOException {
    dataInputStream.close();
  }
}
