package iostrategy;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferedInputStrategy implements InputStrategy {

  private BufferedReader bufferedReader;
  private InputStreamReader inputStreamReader;

  public BufferedInputStrategy(String fileName) throws IOException {
    inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
    bufferedReader = new BufferedReader(inputStreamReader);
  }

  @Override
  public String readLine() throws IOException {
    return bufferedReader.readLine();
  }

  @Override
  public void close() throws IOException {
    bufferedReader.close();
    inputStreamReader.close();
  }
}
