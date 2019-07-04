package track;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class trackTest {
	// Testing strategy:
	// 覆盖所有重要方法，尽可能覆盖所有分支
	@Test
	public void compareToTest() {
		Track t1 = new Track("a", 50);
		Track t2 = new Track("b", 150);
		Track t3 = new Track("c", 250);
		Track t4 = new Track("d", 350);
		List<Track> tracks = new ArrayList<>();
		tracks.add(t3);
		tracks.add(t4);
		tracks.add(t1);
		tracks.add(t2);
		assertEquals("c", tracks.get(0).getName());
		Collections.sort(tracks);
		assertEquals("a", tracks.get(0).getName());

	}
}
