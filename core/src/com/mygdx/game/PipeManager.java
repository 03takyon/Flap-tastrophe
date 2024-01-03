package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PipeManager {
	private ArrayList<Pipe> pipes = new ArrayList<Pipe>();
	private Texture pipeTexture;
	private boolean collisionCheck = false;
	private float spawnTimer = 0;
	private float spawnDelay = 2f;
	private boolean isGhost = false;
	private boolean setAlpha = false;
	private float ghostTimer = 1f;
	private boolean moveUp = false;
	private boolean activateGhostTimer = false;
	
	public PipeManager() {
		pipeTexture = new Texture(Gdx.files.internal("pipe.png"));
		
		EventManager.subscribe(EventTypes.COLLISION, this::handleCollision);
		EventManager.subscribe(EventTypes.SCORE_CHANGE, this::difficultyScaling);
	}
	
	public void updatePipeTimers(float deltaTime) {
		spawnTimer += deltaTime;
		
		if (activateGhostTimer) {
			ghostTimer += deltaTime;
			
			if (ghostTimer >= 1f) {
				ghostTimer = 0;
				
				setAlpha = !setAlpha;
				
				isGhost = !isGhost;
			}
		}
	}
	
	public void spawn(float deltaTime) {
		if (!collisionCheck && spawnTimer >= spawnDelay) {
			spawnTimer = 0;
			
			Pipe pipe = new Pipe(800f, pipeTexture);
			
			pipes.add(pipe);
		}
	}
	
	public void draw(SpriteBatch batch, float deltaTime) {
		// iterate through each pipe updating their position and rendering them on the screen
		for (Pipe p : pipes) {
			p.movePipe(deltaTime, collisionCheck, moveUp);
			
			// updates the pipe's opacity based on the setAlpha flag
			if (setAlpha) {
				p.getPipeSprite().setAlpha(0.5f);
			} else {
				p.getPipeSprite().setAlpha(1f);
			}
			
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
	
	private void difficultyScaling(Object data) {
		switch ((int) data) {
			case 1:
				spawnDelay = 1f;
				
				break;
			case 2:
				moveUp = false;
				
				Pipe.setMoveSpeed(800f);
				
				activateGhostTimer = true;
				
				break;
			case 3:
				activateGhostTimer = false;
				
				setAlpha = false;
				
				isGhost = false;
				
				moveUp = true;
				
				Pipe.setMoveSpeed(800f);
				
				break;
			case 4:
				activateGhostTimer = false;
				
				setAlpha = false;
				
				isGhost = false;
				
				moveUp = false;
				
				Pipe.setMoveSpeed(1100f);
				
				break;		
		}
	}
	
	public ArrayList<Pipe> getPipes() {
		return pipes;
	}
	
	public boolean getIsGhost() {
		return isGhost;
	}
	
	public void dispose() {
		pipeTexture.dispose();
	}
}
