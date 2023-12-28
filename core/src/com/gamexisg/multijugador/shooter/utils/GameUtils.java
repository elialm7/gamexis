package com.gamexisg.multijugador.shooter.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GameUtils {

	private GameUtils() {

	}

	public static BitmapFont generateBitmapFont(int size, Color color) {

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.flip = true;
		parameter.size = size;
		parameter.color = color;
		parameter.magFilter = TextureFilter.Linear;
		parameter.minFilter = TextureFilter.Linear;
		return generator.generateFont(parameter);
	}

	public static void renderCenter(String text, SpriteBatch sb, BitmapFont font) {

		GlyphLayout gl = new GlyphLayout(font, text);
		font.draw(sb, text, GameConstants.SCREEN_WIDTH / 2.0f - gl.width / 2.0f,
				GameConstants.SCREEN_HEIGHT * 0.3f - gl.height / 2);

	}


	public static void renderCenter(String text, SpriteBatch sb, BitmapFont font, float y) {

		GlyphLayout gl = new GlyphLayout(font, text);
		font.draw(sb, text, GameConstants.SCREEN_WIDTH / 2.0f - gl.width / 2.0f,
				GameConstants.SCREEN_HEIGHT * y - gl.height / 2);

	}

}