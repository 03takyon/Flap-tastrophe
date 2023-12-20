package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	private float flapAmount = 9f;
	private float velocityY = 0;
	private float gravity = -25f;
	private Texture playerTextureDown;
	private Texture playerTextureUp;
	private Sprite playerSprite;
	private Rectangle playerRectangle;
	private PipeManager pipeManager;
	private boolean canFlap = true;
	
	public Player(PipeManager pipeManager) {
		playerTextureDown = new Texture(Gdx.files.internal("fly1.png"));
		
		playerTextureUp = new Texture(Gdx.files.internal("fly2.png"));
		
		playerSprite = new Sprite(playerTextureDown);
		playerSprite.setOriginCenter();
		playerSprite.setOriginBasedPosition(450f, 600f);
		playerSprite.setSize(89f, 68f);
		
		playerRectangle = new Rectangle();
		playerRectangle.x = playerSprite.getX();
		playerRectangle.width = playerSprite.getWidth();
		playerRectangle.height = playerSprite.getHeight();
		
		this.pipeManager = pipeManager;
	}
	
	public void flap(float deltaTime) {
		// check for collision before handling input
		// set upward velocity and change texture to playerTextureUp if user presses SPACE or clicks left mouse button
		// if player is falling (velocityY < 0) change texture to playerTextureDown
		if (canFlap) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))) {
				velocityY = flapAmount;
					
				playerSprite.setTexture(playerTextureUp);
			} else if (velocityY < 0) {
				playerSprite.setTexture(playerTextureDown);
			}
		}
		
		// apply gravity to player's vertical velocity to simulate the effect of gravity over time
		velocityY += gravity * deltaTime;
		
		// update the playerSprite's vertical position based on the current velocityY
		// moving up or down on the screen
		playerSprite.translateY(velocityY);
		
		// synchronize the playerRectangle's vertical position with the updated playerSprite position for accurate collision detection
		playerRectangle.y = playerSprite.getY();
		
		handleCollision();
	}
	
	public void handleCollision() {
		// check for collision with the ceiling
		if (playerSprite.getY() + 68f > 600f) {
			canFlap = false;
			
			EventManager.notify(EventTypes.COLLISION, null);
			
			playerSprite.setY(600f - 68f);
			playerSprite.setTexture(playerTextureDown);
			
			velocityY = 0;
		}
		
		// check for collision with the ground
		if (playerSprite.getY() < -14f) {
			playerSprite.setY(-14f);
		}
		
		// check for collision with pipes
		for (Pipe p : pipeManager.getPipes()) {
			if (playerRectangle.overlaps(p.getPipeRectangle()) && !pipeManager.getIsGhost()) {
				canFlap = false;
				
				EventManager.notify(EventTypes.COLLISION, null);
				
				playerSprite.setTexture(playerTextureDown);
			}
		}
	}
	
	public void draw(SpriteBatch batch) {
		playerSprite.draw(batch);
	}
	
	public Texture getPlayerTextureDown() {
		return playerTextureDown;
	}
	
	public Texture getPlayerTextureUp() {
		return playerTextureUp;
	}
	
	public Sprite getPlayerSprite() {
		return playerSprite;
	}
	
	public boolean getCanFlap() {
		return canFlap;
	}
	
	public void dispose() {
		playerTextureDown.dispose();
		playerTextureUp.dispose();
	}
}