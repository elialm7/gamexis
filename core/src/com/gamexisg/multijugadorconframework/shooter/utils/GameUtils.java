package com.gamexisg.multijugadorconframework.shooter.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

	/*public static void renderCenter(String text, Stage stage, BitmapFont font) {

		renderCenter(text, stage, font, 0.5f );

	}


	public static void renderCenter(String text, Stage stage, BitmapFont font, float y) {

		Table table = new Table();
		table.setFillParent(true);

		Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
		Label label = new Label(text, labelStyle);
		table.add(label).expand().center().padTop(stage.getHeight() * y);

		stage.addActor(table);
	}*/

}