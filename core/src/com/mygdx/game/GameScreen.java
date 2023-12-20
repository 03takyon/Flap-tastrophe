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

public class GameScreen implements Screen {
	private Game game;
	private OrthographicCamera camera;
	private Player player;
	private PipeManager pipeManager;
	private BitmapFont scoreText;
	private Score score;
	private Background background;
	private BitmapFont gameOverText;
	private BitmapFont restartText;
	private Fonts fonts;
	private SpriteBatch batch;
	
	public GameScreen(Game game) {
		this.game = game;
	}
	
	@Override
	public void show () {		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800f, 600f);
		
		batch = new SpriteBatch();
		
		pipeManager = new PipeManager();
		
		player = new Player(pipeManager);
		
		scoreText = new BitmapFont();
		scoreText.setColor(0, 0, 0, 1f);
		
		score = new Score();
		
		background = new Background();
		background.playMusic();
		
		fonts = new Fonts();
		
		gameOverText = fonts.createFont(72, 3, Color.RED);
		
		restartText = fonts.createFont(24, 0, Color.BLACK);
	}

	@Override
	public void render (float delta) {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		ScreenUtils.clear(0, 0, 0, 1f);
		
		pipeManager.spawn(deltaTime);
		pipeManager.updatePipeTimers(deltaTime);
		
		score.calculateScore(deltaTime);
		score.updateScalingTimer(deltaTime);
		
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
				
				Pipe.setMoveSpeed(800f);
				
				game.setScreen(new GameScreen(game));
				
				dispose();
				
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
		player.dispose();
		pipeManager.dispose();
		background.dispose();
		scoreText.dispose();
		gameOverText.dispose();
		restartText.dispose();
		fonts.dispose();
		batch.dispose();
	}
}