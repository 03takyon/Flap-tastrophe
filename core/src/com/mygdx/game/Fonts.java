package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Fonts {
	private FreeTypeFontGenerator generator;
	
	public Fonts() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMario256.ttf"));
	}
	
	public BitmapFont createFont(int size, int borderWidth, Color color) {
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		parameter.color = color;
		parameter.borderWidth = borderWidth;
		
		return generator.generateFont(parameter);
	}
	
	public void dispose() {
		generator.dispose();
	}
}
