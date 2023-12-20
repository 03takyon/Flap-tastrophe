package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Pipe {
	private float maxY = -20f;
	private float minY = -400f;
	private float randomY;
	private Rectangle pipeRectangle;
	private Sprite pipeSprite;	
	private float velocityX = 0;
	private float velocityY = 0;
	private static float moveSpeed = 800f;
	private float upSpeed = 400f;
	private boolean movingUp;
	
	public Pipe(float screenWidth, Texture pipeTexture) {
		// generate a random y value within specified range for pipe spawning
		randomY = MathUtils.random(minY, maxY);
		
		// setting pipeSprite's position directly to maintain consistency with dynamic values
		pipeSprite = new Sprite(pipeTexture);
		pipeSprite.setPosition(screenWidth + pipeSprite.getWidth(), randomY);
		pipeSprite.setSize(100f, 500f);
		
		pipeRectangle = new Rectangle();
		pipeRectangle.y = pipeSprite.getY();
		pipeRectangle.width = pipeSprite.getWidth();
		pipeRectangle.height = pipeSprite.getHeight();
	}
	
	public boolean isOffScreen() {
		// check if the pipeSprite has moved off screen to the left beyond a certain point (-200f)
		// if so return true to indicate that the pipe can be removed from the game
		if (pipeSprite.getX() < -200f) {
			return true;
		}
		
		return false;
	}
	
	public void movePipe(float deltaTime, boolean collisionCheck, boolean moveUp) {
		// calculate horizontal movement based on defined speed and frame time if collision is not detected
		// move the pipeSprite and its rectangle leftwards to simulate player movement
		if (!collisionCheck) {
			velocityX = moveSpeed * deltaTime;
				
			pipeSprite.translateX(-velocityX);
				
			pipeRectangle.x = pipeSprite.getX();
			
			if (moveUp) {
				velocityY = movingUp ? upSpeed * deltaTime : -upSpeed * deltaTime;
				
				pipeSprite.translateY(velocityY);
				
				pipeRectangle.y = pipeSprite.getY();
				
				if (pipeSprite.getY() >= maxY) {
					movingUp = false;
				} else if (pipeSprite.getY() <= minY) {
					movingUp = true;
				}
			}
		}
	}
	
	public Sprite getPipeSprite() {
		return pipeSprite;
	}
	
	public Rectangle getPipeRectangle() {
		return pipeRectangle;
	}
	
	public static void setMoveSpeed(float moveSpeed) {
		Pipe.moveSpeed = moveSpeed;
	}
}
