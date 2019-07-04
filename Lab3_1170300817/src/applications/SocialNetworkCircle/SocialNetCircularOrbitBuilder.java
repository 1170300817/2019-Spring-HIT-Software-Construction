package applications.SocialNetworkCircle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import circularOrbit.CircularOrbitBuilder;
import track.Track;

public class SocialNetCircularOrbitBuilder extends CircularOrbitBuilder<Person, Person> {

	public SocialNetCircularOrbitBuilder() {
		concreteCircularOrbit = new SocialNetCircularOrbit();
	}

	/**
	 * 创建具体类型的子类
	 */
	@Override
	public void createCircularOrbit() {
		concreteCircularOrbit = new SocialNetCircularOrbit();
	}

	/**
	 * 从关系网络生成轨道结构
	 * 
	 * @param centralObj
	 * @param personList
	 * @param keeperList
	 */
	public void bulidRelations(Person centralObj, List<Person> personList, List<relationKeeper> keeperList) {

		Map<Track, List<Person>> OrbitMap = new HashMap<>();
		List<Track> trackList = new ArrayList<>();
		concreteCircularOrbit.setCentralObject(centralObj);

		for (relationKeeper keeper : keeperList) {
			Iterator<Person> iterator = personList.iterator();
			Person p1 = null;
			Person p2 = null;
			while (iterator.hasNext()) {
				Person person = iterator.next();
				if (person.getName().equals(keeper.getFromString())) {
					p1 = person;
				}
				if (centralObj.getName().equals(keeper.getFromString())) {
					p1 = centralObj;
				}
				if (person.getName().equals(keeper.getToString())) {
					p2 = person;
				}
			}
			if (p1.getName().equals(centralObj.getName())) {
				concreteCircularOrbit.addcentralRelation(p2, keeper.getWeight());
			} else {
				concreteCircularOrbit.addtrackRelation(p1, p2, keeper.getWeight());
				concreteCircularOrbit.addtrackRelation(p2, p1, keeper.getWeight());
			}
		}

		Set<Person> finishedPerson = new HashSet<>();
		Track track1 = new Track("track0", 50);
		OrbitMap.put(track1, new ArrayList<Person>());
		trackList.add(track1);
		for (Person p : concreteCircularOrbit.getCentralConnectedObject()) {
			OrbitMap.get(track1).add(p);
			finishedPerson.add(p);
		}
		int i = 0;
		boolean flag = true;
		while (flag) {
			flag = false;
			i++;
			Track t = new Track("track" + i, 50 + 100 * i);
			Set<Person> temSet = new HashSet<>();
			for (Person p : finishedPerson) {
				if (concreteCircularOrbit.getTrackConnectedObject(p).size() > 0) {
					for (Person peo : concreteCircularOrbit.getTrackConnectedObject(p)) {
						if (!finishedPerson.contains(peo)) {
							temSet.add(peo);
							flag = true;
						}
					}
				}
			}
			if (flag) {
				trackList.add(t);
				OrbitMap.put(t, new ArrayList<Person>());
				OrbitMap.get(t).addAll(temSet);
				finishedPerson.addAll(temSet);
			}
		}
		this.bulidTracks(trackList);
		this.bulidPhysicalObjects(centralObj, OrbitMap);

	}

}
