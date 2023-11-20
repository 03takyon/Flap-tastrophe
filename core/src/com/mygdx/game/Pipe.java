package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Pipe {
	private float maxY = 150f;
	private float randomY;
	private float velocityX = 0;
	private float moveSpeed = 800f;
	private Rectangle pipeRectangle;
	private Sprite pipeSprite;
	
	public Pipe(float screenWidth, Texture pipeTexture) {
		// generate a random y value within specified range for pipe spawning
		randomY = MathUtils.random(-200f, maxY);
		
		pipeSprite = new Sprite(pipeTexture);
		pipeSprite.setOriginCenter();
		pipeSprite.setOriginBasedPosition(screenWidth + pipeSprite.getWidth(), randomY);
		pipeSprite.setSize(100f, 500f);
		
		pipeRectangle = new Rectangle();
		pipeRectangle.y = pipeSprite.getY();
		pipeRectangle.width = pipeSprite.getWidth();
		pipeRectangle.height = pipeSprite.getHeight();
	}
	
	public void movePipe(float deltaTime, boolean playerCollision) {
		// calculate horizontal movement based on defined speed and frame time if collision is not detected
		// move the pipeSprite and its rectangle leftwards to simulate player movement
		if (!playerCollision) {
			velocityX = moveSpeed * deltaTime;
			
			pipeSprite.translateX(-velocityX);
			
			pipeRectangle.x = pipeSprite.getX();
		}
	}
	
	public boolean isOffScreen() {
		// check if the pipeSprite has moved off screen to the left beyond a certain point (-200f)
		// if so return true to indicate that the pipe can be removed from the game
		if (pipeSprite.getX() < -200f) {
			return true;
		}
		
		return false;
	}
	
	public Sprite getPipeSprite() {
		return pipeSprite;
	}
	
	public Rectangle getPipeRectangle() {
		return pipeRectangle;
	}
}
