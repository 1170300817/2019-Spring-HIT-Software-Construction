package applications.AtomStructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import circularOrbit.CircularOrbitBuilder;
import exception.illegalTextGrammarException;
import exception.logicalErrorException;
import track.Track;

public class AtomCircularOrbitBuilder extends CircularOrbitBuilder<Particle, Particle> {

	public void createFromFile(String fileString)
			throws illegalTextGrammarException, NumberFormatException, IOException, logicalErrorException {
		Particle Nucleus = null;
		int[] DefaultRadius = new int[10];
		for (int i = 0; i < 10; i++) {
			DefaultRadius[i] = 50 + 100 * i;
		}
		Integer trackNum = null;
		createCircularOrbit();
		BufferedReader in = new BufferedReader(new FileReader(new File(fileString)));

		String fileline;
		String elementpattern = "ElementName\\s*::=\\s*([A-Z]{1}[a-z]{0,1})$";
		String trackpattern = "NumberOfTracks\\s*::=\\s*(\\d+)";
		String electronpattern = "NumberOfElectron\\s*::=\\s*((?:(?:\\d+\\/\\d+)|;)+)";
		while ((fileline = in.readLine()) != null) {
			Matcher elementMatcher = Pattern.compile(elementpattern).matcher(fileline);
			Matcher trackMatcher = Pattern.compile(trackpattern).matcher(fileline);
			Matcher electronMatcher = Pattern.compile(electronpattern).matcher(fileline);
			if (elementMatcher.find()) {
				Nucleus = Particle.getNucleus(elementMatcher.group(1));
			} else if (trackMatcher.find()) {
				trackNum = Integer.valueOf(trackMatcher.group(1));
			} else if (electronMatcher.find()) {
				List<Track> trackList = new ArrayList<Track>();
				Map<Track, List<Particle>> elementMap = new HashMap<Track, List<Particle>>();
				String[] NUm = electronMatcher.group(1).split(";");
				if (NUm.length != trackNum) {
					in.close();
					throw new logicalErrorException(":轨道数前后不一致错误");
				}
				for (int i = 0; i < NUm.length; i++) {
					Track track = new Track("track" + i, DefaultRadius[i]);
					trackList.add(track);
					String[] value = NUm[i].split("/");
					int objNum = Integer.valueOf(value[1]);
					List<Particle> currentList = new LinkedList<Particle>();
					for (int j = 0; j < objNum; j++) {
						Particle p = Particle.getElectron();
						currentList.add(p);
					}
					elementMap.put(track, currentList);
				}
				this.createCircularOrbit();
				this.bulidTracks(trackList);
				this.bulidPhysicalObjects(Nucleus, elementMap);
			} else {
				analyzeInput(fileline);
			}
		}
		in.close();

	}

	public void analyzeInput(String readLine) throws illegalTextGrammarException {
		String[] arguments = readLine.trim().split("\\s");
		if (arguments[0].equals("ElementName")) {
			if (arguments.length != 3) {
				throw new illegalTextGrammarException(readLine + ":元素名字参数缺失");
			}
			if (!Pattern.matches("\\s*([A-Z] {1}[a-z] {0,1})", arguments[2])) {
				throw new illegalTextGrammarException(readLine + ":元素名字错误");
			}
		} else if (arguments[0].equals("NumberOfTracks")) {
			if (arguments.length != 3) {
				throw new illegalTextGrammarException(readLine + ":轨道数参数缺失");
			}
			if (!Pattern.matches("\\s*(\\d+)", arguments[2])) {
				throw new illegalTextGrammarException(readLine + ":轨道数错误");
			}
		} else if (arguments[0].equals("NumberOfElectron")) {
			if (!Pattern.matches("\\s*((?:(?:\\d+\\/\\d+)|;)+)", arguments[2])) {
				throw new illegalTextGrammarException(readLine + ":轨道电子参数错误");
			}
		}
	}

	@Override
	public void createCircularOrbit() {
		concreteCircularOrbit = new AtomCircularOrbit();
	}

}
