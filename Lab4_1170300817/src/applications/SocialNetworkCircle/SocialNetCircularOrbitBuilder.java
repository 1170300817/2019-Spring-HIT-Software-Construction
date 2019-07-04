package applications.SocialNetworkCircle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import circularOrbit.CircularOrbitBuilder;
import exception.illegalParameterException;
import exception.illegalTextGrammarException;
import exception.sameLabelException;
import exception.unDefinedPersonException;
import track.Track;

public class SocialNetCircularOrbitBuilder extends CircularOrbitBuilder<Person, Person> {
	public List<Person> personList = new ArrayList<Person>();

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
	 * @throws unDefinedPersonException
	 */
	public void bulidRelations(Person centralObj, List<Person> personList, List<relationKeeper> keeperList)
			throws unDefinedPersonException {

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
			if (p1 == null) {
				throw new unDefinedPersonException("关系中出现未定义的人:" + keeper.getFromString());
			}
			if (p2 == null) {
				throw new unDefinedPersonException("关系中出现未定义的人:" + keeper.getToString());
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

	public List<Person> createFromFile(String fileString) throws illegalTextGrammarException, sameLabelException,
			NumberFormatException, IOException, unDefinedPersonException, illegalParameterException {
		Person centralUser = null;
		List<Person> personList = new ArrayList<Person>();
		List<relationKeeper> keeperList = new ArrayList<relationKeeper>();
		BufferedReader in = new BufferedReader(new FileReader(new File(fileString)));
		String fileline;
		String centralUserPatternString = "CentralUser\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>";
		String friendPatternString = "Friend\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>";
		String socialTiePatternString = "SocialTie\\s*::=\\s*<([A-Za-z0-9]+),\\s*([A-Za-z0-9]+),\\s*(0(?:\\.\\d{1,3})?|1(?:\\.0{1,3})?)>";

		while ((fileline = in.readLine()) != null) {
			Matcher centralUserMatcher = Pattern.compile(centralUserPatternString).matcher(fileline);
			Matcher friendMatcher = Pattern.compile(friendPatternString).matcher(fileline);
			Matcher socialTieMatcher = Pattern.compile(socialTiePatternString).matcher(fileline);
			if (centralUserMatcher.find()) {
				String nameString = centralUserMatcher.group(1);
				int age = Integer.valueOf(centralUserMatcher.group(2));
				String gender = centralUserMatcher.group(3);
				centralUser = Person.getInstance(nameString, age, gender);
			} else if (friendMatcher.find()) {
				String nameString = friendMatcher.group(1);
				int age = Integer.valueOf(friendMatcher.group(2));
				String gender = friendMatcher.group(3);
				Person person = Person.getInstance(nameString, age, gender);
				personList.add(person);
				this.createCircularOrbit();
			} else if (socialTieMatcher.find()) {
				Double weight = Double.valueOf(socialTieMatcher.group(3));
				relationKeeper currKeeper = new relationKeeper(socialTieMatcher.group(1), socialTieMatcher.group(2),
						weight);
				keeperList.add(currKeeper);
			} else {
				analyzeInput(fileline);
			}
		}
		in.close();
		this.bulidRelations(centralUser, personList, keeperList);
		return personList;

	}

	public void analyzeInput(String readLine) throws illegalTextGrammarException {
		String[] arguments = readLine.trim().split("=");
		if (Pattern.matches("(CentralUser\\s*::\\s*)", arguments[0])
				|| Pattern.matches("(Friend\\s*::\\s*)", arguments[0])) {
			String[] parameter = arguments[1].split(",");
			if (parameter.length != 3) {
				throw new illegalTextGrammarException(readLine + ":人物参数缺失");
			}
			if (!Pattern.matches("(\\s*<[A-Za-z0-9]+)", parameter[0])) {
				throw new illegalTextGrammarException(readLine + ":人物名字错误");
			}
			if (!Pattern.matches("(\\d+)", parameter[1])) {
				throw new illegalTextGrammarException(readLine + ":人物年龄错误");
			}
			if (!Pattern.matches("([MF]>)", parameter[2])) {
				throw new illegalTextGrammarException(readLine + ":人物性别错误");
			}
		} else if (Pattern.matches("(SocialTie\\s*::\\s*)", arguments[0])) {
			String[] parameter = arguments[1].split(",");
			if (parameter.length != 3) {
				throw new illegalTextGrammarException(readLine + ":社交关系参数缺失");
			}
			if (!Pattern.matches("(\\s*<[A-Za-z0-9]+)", parameter[0])
					|| !Pattern.matches("(\\s*[A-Za-z0-9]+)", parameter[1])) {
				throw new illegalTextGrammarException(readLine + ":社交关系名字错误");
			}
			if (!Pattern.matches("(0(?:\\.\\d{1,3})?|1(?:\\.0{1,3})?>)", parameter[0])) {
				throw new illegalTextGrammarException(readLine + ":社交关系亲密度错误");
			}
		}

	}
}
