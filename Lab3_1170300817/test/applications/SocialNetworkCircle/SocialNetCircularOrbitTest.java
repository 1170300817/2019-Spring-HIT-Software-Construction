package applications.SocialNetworkCircle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class SocialNetCircularOrbitTest {
	// Testing strategy:
	// 补测一些子类方法
	SocialNetCircularOrbit orbit = new SocialNetCircularOrbit();
	Person center = Person.getInstance("xxx", 20, "M");
	Person p1 = Person.getInstance("aa", 20, "M");
	Person p2 = Person.getInstance("bb", 20, "M");
	Person p3 = Person.getInstance("cc", 20, "M");
	Person p4 = Person.getInstance("dd", 20, "M");
	Person p5 = Person.getInstance("ff", 20, "M");

	@Test
	public void getIntimacyTest() {
		orbit.setCentralObject(center);
		orbit.addcentralRelation(p1, 0.5);
		orbit.addtrackRelation(p1, p2, 0.5);
		orbit.reArrange();
		assertEquals(0.75, orbit.getIntimacy(p1));
	}
}
