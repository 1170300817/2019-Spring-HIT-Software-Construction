package applications.SocialNetworkCircle;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.junit.Test;

import exception.illegalParameterException;
import exception.illegalTextGrammarException;
import exception.sameLabelException;
import exception.unDefinedPersonException;

public class SocialNetworkTest {

	@Test
	public void SocialNetworkTestrelation() throws NumberFormatException, illegalTextGrammarException,
			sameLabelException, IOException, unDefinedPersonException, illegalParameterException {
//		List<Person> personList = new ArrayList<Person>();
		SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();
		socialCircularOrbitBuilder.createFromFile("src/txt/SocialNetworkCircle.txt");
		SocialNetCircularOrbit socialCircularOrbit = (SocialNetCircularOrbit) socialCircularOrbitBuilder
				.getConcreteCircularOrbit();
//		Person centralUser = socialCircularOrbit.getCentralObject();
		socialCircularOrbit.reArrange();
		assertTrue(socialCircularOrbit.toString().contains("TommyWong的关系网:"));
		System.out.println(socialCircularOrbit.toString());
		assertTrue(socialCircularOrbit.toString().contains("track0上有:"));
		assertTrue(socialCircularOrbit.toString().contains("LisaWong"));
		assertTrue(socialCircularOrbit.toString().contains("DavidChen"));
		assertTrue(socialCircularOrbit.toString().contains("track1上有:FrankLee"));

	}
}
