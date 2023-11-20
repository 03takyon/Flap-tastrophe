package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Player player;
	private PipeManager pipeManager;
	private BitmapFont scoreText;
	private Score score;
	private Background background;
	private Game game;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter gameOverParameter;
	private BitmapFont gameOverText;
	private FreeTypeFontParameter restartParameter;
	private BitmapFont restartText;
	
	public GameScreen(Game game) {
		this.game = game;
	}
	
	@Override
	public void show () {		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800f, 600f);
		
		batch = new SpriteBatch();
		
		pipeManager = new PipeManager();
		pipeManager.spawn();
		
		player = new Player(pipeManager);
		
		scoreText = new BitmapFont();
		scoreText.setColor(0, 0, 0, 1f);
		
		score = new Score(player);
		score.calculateScore();
		
		background = new Background();
		background.playMusic();
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMario256.ttf"));
		
		gameOverParameter = new FreeTypeFontParameter();
		gameOverParameter.size = 72;
		gameOverParameter.borderColor = Color.BLACK;
		gameOverParameter.borderWidth = 3f;
		
		gameOverText = generator.generateFont(gameOverParameter);
		gameOverText.setColor(1f, 0, 0, 1f);
		
		restartParameter = new FreeTypeFontParameter();
		restartParameter.size = 24;
		
		restartText = generator.generateFont(restartParameter);
		restartText.setColor(0, 0, 0, 1f);
		
		generator.dispose();
	}

	@Override
	public void render (float delta) {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		ScreenUtils.clear(0, 0, 0, 1f);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		background.draw(batch);
		
		scoreText.draw(batch, "Score: " + score.getScore() + " High Score: " + score.getHighScore(), 10f, 590f);
		
		pipeManager.draw(batch, deltaTime);
		
		player.draw(batch);
		
		// check for player collision and if the player's y-position is at the bottom of the screen
		if (!player.getCanFlap() && player.getPlayerSprite().getY() == -14f) {
			gameOverText.draw(batch, "Game Over", 150f, 350f);
			
			restartText.draw(batch, "Click anywhere to restart", 200f, 250f);
			
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				score.saveHighScore();
				
				// restart the game by setting up a new game screen instance
				// ensures all game elements are reset without leftover data
				game.setScreen(new GameScreen(game));
				
				// call dispose to clean up current screen's resources preventing memory leaks
				this.dispose();
				
				return;
			}
		}
		
		batch.end();
		
		pipeManager.remove();
		
		player.flap(deltaTime);
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
	public void dispose () {
		batch.dispose();
		player.dispose();
		pipeManager.dispose();
		background.dispose();
		scoreText.dispose();
		gameOverText.dispose();
		restartText.dispose();
		score.dispose();
	}
}