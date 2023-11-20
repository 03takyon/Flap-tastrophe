package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class MainMenuScreen implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Background background;
	private Game game;
	private Player player;
	private boolean isTextureDown;
	private float timer;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter titleParameter;
	private BitmapFont titleText;
	private BitmapFont startText;
	private FreeTypeFontParameter startParameter;

	public MainMenuScreen(Game game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800f, 600f);
		
		batch = new SpriteBatch();
		
		background = new Background();
		
		// passing null for the PipeManager parameter to avoid unnecessary instantiation
		player = new Player(null);
		
		isTextureDown = true;
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMario256.ttf"));
		
		titleParameter = new FreeTypeFontParameter();
		titleParameter.size = 60;
		titleParameter.borderColor = Color.BLACK;
		titleParameter.borderWidth = 3f;
		
		titleText = generator.generateFont(titleParameter);
		
		startParameter = new FreeTypeFontParameter();
		startParameter.size = 24;
		
		startText = generator.generateFont(startParameter);
		startText.setColor(0, 0, 0, 1f);
		
		generator.dispose();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1f);
		
		// timer increments based on the time since the last frame
		timer += Gdx.graphics.getDeltaTime();
		
		// switch isTextureDown to its opposite value every half a second
		if (timer >= 0.5f) {
			timer = 0;
			
			isTextureDown = !isTextureDown;
		}
		
		// the player texture is updated based on the isTextureDown flag to simulate wing flapping animation
		if (isTextureDown) {
			player.getPlayerSprite().setTexture(player.getPlayerTextureDown());
		} else {
			player.getPlayerSprite().setTexture(player.getPlayerTextureUp());
		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		background.draw(batch);
		
		player.draw(batch);
		
		titleText.draw(batch, "Flap-tastrophe", 100f, 550f);
		
		startText.draw(batch, "Click anywhere to start", 400f, 300f);
		
		batch.end();
		
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			// set the player texture to playerTextureDown before switching to the game screen for a smooth transition
			player.getPlayerSprite().setTexture(player.getPlayerTextureDown());
			
			game.setScreen(new GameScreen(game));
			
			this.dispose();
			
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
		batch.dispose();
		background.dispose();
		player.dispose();
		titleText.dispose();
		startText.dispose();
	}
}
