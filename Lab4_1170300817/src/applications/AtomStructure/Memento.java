package applications.AtomStructure;

import track.Track;

/**
 * @author Administrator 备忘录类
 */
public class Memento {
	private final Track fromTrack;
	private final Track toTrack;

	public Track getFromTrack() {
		return fromTrack;
	}

	public Track getToTrack() {
		return toTrack;
	}

	/**
	 * 构造方法
	 * 
	 * @param fromTrack 起始轨道
	 * @param toTrack   目标轨道
	 */
	public Memento(Track fromTrack, Track toTrack) {
		this.fromTrack = fromTrack;
		this.toTrack = toTrack;
	}

}
