package com.crawn.game.utils.resource.manager.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;


public class FreeTypeFontLoader extends FreetypeFontLoader {
    public FreeTypeFontLoader() {
        super(new InternalFileHandleResolver());
    }

    @Override
    public BitmapFont loadSync(AssetManager manager, String fileName, FileHandle file, FreeTypeFontLoaderParameter parameter) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(file);

        parameter.fontFileName = fileName;
        parameter.fontParameters.size = Gdx.graphics.getHeight() * 24 / Gdx.graphics.getWidth();
        parameter.fontParameters.color = Color.FIREBRICK;

        BitmapFont mainFonts = fontGenerator.generateFont(parameter.fontParameters);
        fontGenerator.dispose();
        return mainFonts;
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, FreeTypeFontLoaderParameter parameter) {
        super.loadAsync(manager, fileName, file, parameter);
    }
}
