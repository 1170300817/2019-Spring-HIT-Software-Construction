package iostrategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class WriterOutputStrategy implements OutputStrategy {

  private OutputStreamWriter outputStreamWriter;

  public WriterOutputStrategy(String filePath) throws IOException {
    outputStreamWriter = new OutputStreamWriter(new FileOutputStream(filePath));
  }

  @Override
  public void write(String string) throws IOException {
    outputStreamWriter.write(string);
  }

  @Override
  public void close() throws IOException {
    outputStreamWriter.flush();
    outputStreamWriter.close();
  }
}
