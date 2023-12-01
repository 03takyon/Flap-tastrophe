package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

public class PipeManager {
	private ArrayList<Pipe> pipes = new ArrayList<Pipe>();
	private Texture pipeTexture;
	private boolean collisionCheck = false;
	private float initialDelay = 1f;
	private float spawnInterval = 2f;
	
	public PipeManager() {
		pipeTexture = new Texture(Gdx.files.internal("pipe.png"));
		
		EventManager.subscribe(EventTypes.COLLISION, this::handleCollision);
	}
	
	public void spawn() {
		// spawn pipes every 2 seconds after a 1-second initial delay
		// stop spawning pipes by canceling the task if collision is detected
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				if (!collisionCheck) {
					Pipe pipe = new Pipe(800f, pipeTexture);
					
					pipes.add(pipe);
				} else {
					this.cancel();
				}
			}
		}, initialDelay, spawnInterval);
	}
	
	public void draw(SpriteBatch batch, float deltaTime) {
		// iterate through each pipe updating their position and rendering them on the screen
		for (Pipe p : pipes) {
			p.movePipe(deltaTime, collisionCheck);
			p.getPipeSprite().draw(batch);
		}
	}
	
	public void remove() {
		// use an iterator to safely remove pipes that have gone off-screen during the game loop
		Iterator<Pipe> iter = pipes.iterator();
		
		while (iter.hasNext()) {
			Pipe p = iter.next();
			
			// check if the current pipe is no longer visible on the screen
			if (p.isOffScreen()) {
				iter.remove();
			}
		}
	}
	
	private void handleCollision(Object data) {
		collisionCheck = true;
	}
	
	public ArrayList<Pipe> getPipes() {
		return pipes;
	}
	
	public void dispose() {
		pipeTexture.dispose();
	}
}
