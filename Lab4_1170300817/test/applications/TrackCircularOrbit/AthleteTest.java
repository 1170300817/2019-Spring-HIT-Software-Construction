package applications.TrackCircularOrbit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import applications.TrackGame.Athlete;
import exception.illegalParameterException;
import exception.sameLabelException;

public class AthleteTest {

	@Test
	public void athleteEX1Test() {
		try {
			Athlete.getInstance(",.a", 16, "AAM", 12, 9.65);
		} catch (illegalParameterException | sameLabelException e) {
			assertTrue(e.getMessage().contains("运动员名字错误"));
		}
	}

	@Test
	public void athleteEX2Test() {
		try {
			Athlete.getInstance("asd", 16, "AM", 12, 9.65);
		} catch (illegalParameterException | sameLabelException e) {
			assertTrue(e.getMessage().contains("运动员国籍错误"));
		}
	}

	@Test 
	public void athleteEX3Test() {
		try {
			Athlete.getInstance("asf", 16, "AAM", 12, 9.065);
		} catch (illegalParameterException | sameLabelException e) {
			assertTrue(e.getMessage().contains("运动员最好成绩错误"));
		}
	}

	@Test
	public void compareTest() throws sameLabelException, illegalParameterException {

		Athlete athlete1=Athlete.getInstance("ccc", 16, "AAM", 12, 9.65);
		Athlete athlete2=Athlete.getInstance("eee", 16, "AAM", 12, 9.69);
		assertTrue(athlete1.compareTo(athlete2)==-1);
		assertTrue(athlete2.compareTo(athlete1)==1);
		assertTrue(athlete2.compareTo(athlete2)==0);

	}
}
