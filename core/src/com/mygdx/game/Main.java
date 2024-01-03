package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Fonts fonts;
	public Background background;
	
	public void create() {
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800f, 600f);
		
		fonts = new Fonts();
		
		background = new Background();
		
		this.setScreen(new MainMenuScreen(this));
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		fonts.dispose();
		background.dispose();
	}
}