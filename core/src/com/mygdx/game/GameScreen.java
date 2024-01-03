package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
	private Main game;
	private OrthographicCamera camera;
	private Player player;
	private PipeManager pipeManager;
	private BitmapFont scoreText;
	private Score score;
	private BitmapFont gameOverText;
	private BitmapFont restartText;
	private float animTimer;
	private boolean isTextureDown;
	
	public GameScreen(Main game) {
		this.game = game;
	}
	
	@Override
	public void show () {		
		camera = game.camera;
		
		pipeManager = new PipeManager();
		
		player = new Player(pipeManager);
		
		scoreText = new BitmapFont();
		scoreText.setColor(0, 0, 0, 1f);
		
		score = new Score();
		
		game.background.playMusic();
		
		gameOverText = game.fonts.createFont(72, 3, Color.RED);
		
		restartText = game.fonts.createFont(24, 0, Color.BLACK);
	}

	@Override
	public void render (float delta) {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		ScreenUtils.clear(0, 0, 0, 1f);
		
		pipeManager.spawn(deltaTime);
		pipeManager.updatePipeTimers(deltaTime);
		
		score.calculateScore(deltaTime);
		score.updateScalingTimer(deltaTime);
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		game.background.draw(game.batch);
		
		scoreText.draw(game.batch, "Score: " + score.getScore() + " High Score: " + score.getHighScore(), 10f, 590f);
		
		pipeManager.draw(game.batch, deltaTime);
		
		player.draw(game.batch);
		
		if (!player.getCanFlap()) {
			animTimer += deltaTime;
			
			if (animTimer >= 0.5f) {
				animTimer = 0;
				
				isTextureDown = !isTextureDown;
			}
			
			// the player texture is updated based on the isTextureDown flag to simulate death animation
			if (isTextureDown) {
				player.getPlayerSprite().setTexture(player.getPlayerTextureHitDown());
			} else {
				player.getPlayerSprite().setTexture(player.getPlayerTextureHitUp());
			}
		}
		
		// check for player collision and if the player's y-position is at the bottom of the screen
		if (!player.getCanFlap() && player.getPlayerSprite().getY() == -14f) {
			gameOverText.draw(game.batch, "Game Over", 150f, 350f);
			
			restartText.draw(game.batch, "Click anywhere to restart", 200f, 250f);
			
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				score.saveHighScore();
				
				Pipe.setMoveSpeed(800f);
				
				game.setScreen(new GameScreen(game));
				
				dispose();
				
				return;
			}
		}
		
		game.batch.end();
		
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
		scoreText.dispose();
		gameOverText.dispose();
		restartText.dispose();
	}
}