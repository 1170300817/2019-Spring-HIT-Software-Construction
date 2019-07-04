package applications.SocialNetworkCircle;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import exception.illegalParameterException;
import exception.illegalTextGrammarException;
import exception.sameLabelException;
import exception.unDefinedPersonException;

public class SocialNetCircularOrbitBuilderTest {

	@Test
	public void sicialException1Test() {
		SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();
		try {
			socialCircularOrbitBuilder.createFromFile("src/txt/exception/Social1.txt");
		} catch (NumberFormatException | illegalTextGrammarException | sameLabelException | IOException
				| unDefinedPersonException | illegalParameterException e) {
			assertTrue(e.getMessage().contains(":人物参数缺失"));
		}
	}

	@Test
	public void sicialException2Test() {
		SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();

		try {
			socialCircularOrbitBuilder.createFromFile("src/txt/exception/Social2.txt");
		} catch (NumberFormatException | illegalTextGrammarException | sameLabelException | IOException
				| unDefinedPersonException | illegalParameterException e) {
			assertTrue(e.getMessage().contains(":人物名字错误"));
		}
	}

	@Test
	public void sicialException3Test() {
		SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();

		try {
			socialCircularOrbitBuilder.createFromFile("src/txt/exception/Social3.txt");
		} catch (NumberFormatException | illegalTextGrammarException | sameLabelException | IOException
				| unDefinedPersonException | illegalParameterException e) {
			assertTrue(e.getMessage().contains(":人物年龄错误"));
		}
	}

	@Test
	public void sicialException4Test() {
		SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();

		try {
			socialCircularOrbitBuilder.createFromFile("src/txt/exception/Social4.txt");
		} catch (NumberFormatException | illegalTextGrammarException | sameLabelException | IOException
				| unDefinedPersonException | illegalParameterException e) {
			assertTrue(e.getMessage().contains(":人物性别错误"));
		}
	}

	@Test
	public void sicialException5Test() {
		SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();

		try {
			socialCircularOrbitBuilder.createFromFile("src/txt/exception/Social5.txt");
		} catch (NumberFormatException | illegalTextGrammarException | sameLabelException | IOException
				| unDefinedPersonException | illegalParameterException e) {
			assertTrue(e.getMessage().contains(":社交关系参数缺失"));
		}
	}

	@Test
	public void sicialException6Test() {
		SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();

		try {
			socialCircularOrbitBuilder.createFromFile("src/txt/exception/Social6.txt");
		} catch (NumberFormatException | illegalTextGrammarException | sameLabelException | IOException
				| unDefinedPersonException | illegalParameterException e) {
			assertTrue(e.getMessage().contains(":社交关系名字错误"));
		}
	}

	@Test
	public void sicialException7Test() {
		SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();

		try {
			socialCircularOrbitBuilder.createFromFile("src/txt/exception/Social7.txt");
		} catch (NumberFormatException | illegalTextGrammarException | sameLabelException | IOException
				| unDefinedPersonException | illegalParameterException e) {
			assertTrue(e.getMessage().contains(":社交关系亲密度错误"));
		}
	}

	

	@Test
	public void sicialSameLabel1ExceptionTest() {
		SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();

		try {
			socialCircularOrbitBuilder.createFromFile("src/txt/exception/SocialSameLabel.txt");
		} catch (NumberFormatException | illegalTextGrammarException | sameLabelException | IOException
				| unDefinedPersonException | illegalParameterException e) {
			assertTrue(e.getMessage().contains("为名的对象已经存在"));
		}
	}

	@Test
	public void sicialSameLabel2ExceptionTest() {
		SocialNetCircularOrbitBuilder socialCircularOrbitBuilder1 = new SocialNetCircularOrbitBuilder();
		try {
			socialCircularOrbitBuilder1.createFromFile("src/txt/exception/SocialSameLabel2.txt");
		} catch (NumberFormatException | illegalTextGrammarException | sameLabelException | IOException
				| unDefinedPersonException | illegalParameterException e) {
			assertTrue(e.getMessage().contains("的边已经存在"));
		}
	}
	@Test
	public void sicialLogicalExceptionTest() {
		SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();

		try {
			socialCircularOrbitBuilder.createFromFile("src/txt/exception/SocialLocialErr.txt");
		} catch (NumberFormatException | illegalTextGrammarException | sameLabelException | IOException
				| unDefinedPersonException | illegalParameterException e) {
			assertTrue(e.getMessage().contains("出现未定义的人"));
		}
	}
}
