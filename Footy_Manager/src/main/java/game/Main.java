package game;

import game.Game;
import game.GameImpl;

import org.apache.log4j.BasicConfigurator;

public class Main {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		Game FootyManager= new GameImpl();
		FootyManager.start();
	}
}
