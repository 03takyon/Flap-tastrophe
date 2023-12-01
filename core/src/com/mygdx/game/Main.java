package com.mygdx.game;

import com.badlogic.gdx.Game;

public class Main extends Game {
	public void create() {
		this.setScreen(new MainMenuScreen(this));
	}
}