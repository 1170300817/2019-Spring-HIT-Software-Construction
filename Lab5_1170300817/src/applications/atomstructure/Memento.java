package applications.atomstructure;

import track.Track;

public class Memento {

  private final Track fromTrack;
  private final Track toTrack;

  /**
   * 获得终止轨道.
   * 
   * @return
   */
  public Track getFromTrack() {
    return fromTrack;
  }

  /**
   * 获得起始轨道.
   * 
   * @return
   */
  public Track getToTrack() {
    return toTrack;
  }

  /**
   * 构造方法.
   * 
   * @param fromTrack 起始轨道
   * @param toTrack 目标轨道
   */
  public Memento(Track fromTrack, Track toTrack) {
    this.fromTrack = fromTrack;
    this.toTrack = toTrack;
  }

}
