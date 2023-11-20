package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
	private Texture backgroundTexture;
	private Sprite backgroundSprite;
	private Music backgroundMusic;
	
	public Background() {
		backgroundTexture = new Texture(Gdx.files.internal("background.png"));
		
		backgroundSprite = new Sprite(backgroundTexture);
		backgroundSprite.setOriginCenter();
		backgroundSprite.setOriginBasedPosition(800f, 600f);
		backgroundSprite.setSize(1536f, 768f);
		
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		backgroundMusic.setVolume(0.09f);
		backgroundMusic.setLooping(true);
	}
	
	public void draw(SpriteBatch batch) {
		backgroundSprite.draw(batch);
	}
	
	public void playMusic() {
		backgroundMusic.play();
	}
	
	public void stopMusic() {
		backgroundMusic.stop();
	}
	
	public void dispose() {
		backgroundTexture.dispose();
		backgroundMusic.dispose();
	}
}
