package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
	private Main game;
	private OrthographicCamera camera;
	private Player player;
	private boolean isTextureDown;
	private float animTimer;
	private BitmapFont titleText;
	private BitmapFont startText;

	public MainMenuScreen(Main game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		camera = game.camera;
		
		// passing null for the PipeManager parameter to avoid unnecessary instantiation
		player = new Player(null);
		
		isTextureDown = true;
		
		titleText = game.fonts.createFont(60, 3, Color.WHITE);
		
		startText = game.fonts.createFont(24, 0, Color.BLACK);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1f);
		
		animTimer += Gdx.graphics.getDeltaTime();
		
		if (animTimer >= 0.5f) {
			animTimer = 0;
			
			isTextureDown = !isTextureDown;
		}
		
		// the player texture is updated based on the isTextureDown flag to simulate wing flapping animation
		if (isTextureDown) {
			player.getPlayerSprite().setTexture(player.getPlayerTextureDown());
		} else {
			player.getPlayerSprite().setTexture(player.getPlayerTextureUp());
		}

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		game.background.draw(game.batch);
		
		player.draw(game.batch);
		
		titleText.draw(game.batch, "Flap-tastrophe", 100f, 550f);
		
		startText.draw(game.batch, "Click anywhere to start", 400f, 300f);
		
		game.batch.end();
		
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			// set the player texture to playerTextureDown before switching to the game screen for a smooth transition
			player.getPlayerSprite().setTexture(player.getPlayerTextureDown());
			
			game.setScreen(new GameScreen(game));
			
			dispose();
			
			return;
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		player.dispose();
		titleText.dispose();
		startText.dispose();
	}
}
