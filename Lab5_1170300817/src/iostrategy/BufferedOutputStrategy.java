package iostrategy;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BufferedOutputStrategy implements OutputStrategy {
  private BufferedWriter bufferedWriter;
  private OutputStreamWriter outputStreamWriter;


  public BufferedOutputStrategy(String filePath) throws IOException {
    outputStreamWriter = new OutputStreamWriter(new FileOutputStream(filePath));
    bufferedWriter = new BufferedWriter(outputStreamWriter);
  }

  @Override
  public void write(String string) throws IOException {
    bufferedWriter.write(string);
  }

  @Override
  public void close() throws IOException {
    bufferedWriter.flush();
    bufferedWriter.close();
  }
}
