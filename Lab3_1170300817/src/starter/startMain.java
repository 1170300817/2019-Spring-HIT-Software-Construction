package starter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import applications.AtomStructure.AtomGame;
import applications.SocialNetworkCircle.SocialNetworkGame;
import applications.TrackGame.TrackGame;

public class startMain {
	public static void GameMenu() {
		System.out.println("1.\tTrackGame");
		System.out.println("2.\tAtomStructure");
		System.out.println("3.\tSocialNetworkCircle");
		System.out.println("end.\t 结束");
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean exitflag = false;
		while (true) {
			GameMenu();
			String input = reader.readLine().trim();
			switch (input) {
			case "1":
				new TrackGame().gameMain();
				break;
			case "2":
				new AtomGame().gameMain();
				break;
			case "3":
				new SocialNetworkGame().gameMain();
				break;
			case "end":// 结束游戏
				exitflag = true;
				break;
			default:
				System.out.println("输入错误");
				break;
			}
			if (exitflag)
				break;
		}
	}

}
