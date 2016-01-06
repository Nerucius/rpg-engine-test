package net.nerucius.rpgtest.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontLoader {

	public static BitmapFont load(String path, int size) {
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(path));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

		params.size = size;
		params.minFilter = Texture.TextureFilter.Nearest;
		params.magFilter = Texture.TextureFilter.Nearest;


		params.color = Color.LIGHT_GRAY;
//        params.shadowColor = Color.GRAY;
//        params.shadowOffsetX = -1;
//        params.shadowOffsetY = -1;

		BitmapFont font = gen.generateFont(params);
		gen.dispose();
		return font;
	}
}
