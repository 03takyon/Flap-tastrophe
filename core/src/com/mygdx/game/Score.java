package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

public class Score {
	private int score;
	private int highScore;
	private Preferences prefs = Gdx.app.getPreferences("prefs");
	private boolean collisionCheck = false;
	private float scalingTimer = 0;
	private float scoreTimer = 0;
	
	public Score() {
		// get the highest score from prefs defaulting to 0 if nothing is found
		highScore = prefs.getInteger("highScore", 0);
		
		EventManager.subscribe(EventTypes.COLLISION, this::handleCollision);
	}
	
	public void calculateScore(float deltaTime) {
		// increment score by 1 every second as long as player collision is not detected
		scoreTimer += deltaTime;
		
		if (!collisionCheck && scoreTimer >= 1f) {
			scoreTimer = 0;
			
			score++;
			
			notifyDifficulty();
		}
	}
	
	public void saveHighScore() {
		if (score > highScore) {
			// update highScore in prefs with new score
			prefs.putInteger("highScore", score);
			// commit the changes to the storage to ensure persistence
			prefs.flush(); 
			
			// update highScore for the current game session
			highScore = score;
		}
	}
	
	public void updateScalingTimer(float deltaTime) {
		scalingTimer += deltaTime;
	}
	
	// returns an integer (1-4) based on the score to determine the amount of difficulty scaling
	public int scalingAmount() {
		if (scalingTimer >= 5f) {
			scalingTimer = 0;
			
			if (score >= 25) {
				return MathUtils.random(2, 4);
			} else {
				return Math.min(score / 5, 4);
			}
		}
		
		return 0;
	}
	
	// notifies subscribed classes on score changes to update difficulty scaling
	public void notifyDifficulty() {
		int scalingAmount = scalingAmount();
		
		if (scalingAmount > 0) {
			EventManager.notify(EventTypes.SCORE_CHANGE, scalingAmount);
		}
	}
	
	private void handleCollision(Object data) {
		collisionCheck = true;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getHighScore() {
		return highScore;
	}
}
