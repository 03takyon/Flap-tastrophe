package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Timer;

public class Score {
	private int score;
	private int highScore;
	private Player player;
	private Preferences prefs = Gdx.app.getPreferences("prefs");
	
	public Score(Player player) {
		this.player = player;
		
		// get the highest score from prefs defaulting to 0 if nothing is found
		highScore = prefs.getInteger("highScore", 0);
	}
	
	public void calculateScore() {
		// increment score by 1 every second as long as player collision is not detected
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				if (player.getCanFlap()) {
					score++;
				} else {
					this.cancel();
				}
			}
		}, 1f, 1f);
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
	
	public int getScore() {
		return score;
	}
	
	public int getHighScore() {
		return highScore;
	}
	
	public void dispose() {
		player.dispose();
	}
}
