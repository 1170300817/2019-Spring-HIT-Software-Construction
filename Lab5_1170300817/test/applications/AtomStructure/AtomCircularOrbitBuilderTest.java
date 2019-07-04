package applications.AtomStructure;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;
import applications.atomstructure.AtomCircularOrbitBuilder;
import exception.illegalTextGrammarException;
import exception.logicalErrorException;

public class AtomCircularOrbitBuilderTest {
  AtomCircularOrbitBuilder builder = new AtomCircularOrbitBuilder();

  @Test
  public void creatTest() throws NumberFormatException, illegalTextGrammarException, IOException,
      logicalErrorException {
    builder.createCircularOrbit();
    builder.createFromFile("src/txt/AtomicStructure.txt");
  }

  @Test
  public void exception1Test() {
    try {
      builder.createFromFile("src/txt/exception/Atom1.txt");
    } catch (NumberFormatException | illegalTextGrammarException | IOException
        | logicalErrorException e) {
      assertTrue(e.getMessage().contains(":元素名字参数缺失"));
    }
  }

  @Test
  public void exception2Test() throws NumberFormatException, illegalTextGrammarException,
      IOException, logicalErrorException {
    try {
      builder.createFromFile("src/txt/exception/Atom2.txt");
    } catch (NumberFormatException | illegalTextGrammarException | IOException
        | logicalErrorException e) {
      assertTrue(e.getMessage().contains(":元素名字错误"));
    }
  }

  @Test
  public void exception3Test() throws NumberFormatException, illegalTextGrammarException,
      IOException, logicalErrorException {
    try {
      builder.createFromFile("src/txt/exception/Atom3.txt");
    } catch (NumberFormatException | illegalTextGrammarException | IOException
        | logicalErrorException e) {
      assertTrue(e.getMessage().contains(":轨道数参数缺失"));
    }
  }

  @Test
  public void exception4Test() throws NumberFormatException, illegalTextGrammarException,
      IOException, logicalErrorException {
    try {
      builder.createFromFile("src/txt/exception/Atom4.txt");
    } catch (NumberFormatException | illegalTextGrammarException | IOException
        | logicalErrorException e) {
      assertTrue(e.getMessage().contains(":轨道数错误"));
    }
  }

  @Test
  public void exception5Test() throws NumberFormatException, illegalTextGrammarException,
      IOException, logicalErrorException {
    try {
      builder.createFromFile("src/txt/exception/Atom5.txt");
    } catch (NumberFormatException | illegalTextGrammarException | IOException
        | logicalErrorException e) {
      assertTrue(e.getMessage().contains(":轨道电子参数错误"));
    }
  }

  @Test
  public void exception6Test() throws NumberFormatException, illegalTextGrammarException,
      IOException, logicalErrorException {
    try {
      builder.createFromFile("src/txt/exception/AtomLogicalErr.txt");
    } catch (NumberFormatException | illegalTextGrammarException | IOException
        | logicalErrorException e) {
      assertTrue(e.getMessage().contains("轨道数前后不一致错误"));
    }
  }

}
