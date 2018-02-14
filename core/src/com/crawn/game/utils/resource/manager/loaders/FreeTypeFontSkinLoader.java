package com.crawn.game.utils.resource.manager.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;

import java.io.File;


final public class FreeTypeFontSkinLoader extends SkinLoader {
    public FreeTypeFontSkinLoader() {
        super(new InternalFileHandleResolver());
    }

    @Override
    public Skin loadSync(AssetManager manager, String fileName, FileHandle file, SkinParameter parameter) {
        String textureAtlasPath = file.pathWithoutExtension() + ".atlas";
        ObjectMap<String, Object> resources = null;

        if (parameter != null) {
            if (parameter.textureAtlasPath != null) {
                textureAtlasPath = parameter.textureAtlasPath;
            }

            if (parameter.resources != null) {
                resources = parameter.resources;
            }
        }
        final Skin skin = new Skin(manager.get(textureAtlasPath, TextureAtlas.class));
        final String fontName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
        skin.add(fontName, generateFont(file.pathWithoutExtension() + ".ttf"));

        if (resources != null) {
            for (ObjectMap.Entry<String, Object> entry: resources.entries()) {
                skin.add(entry.key, entry.value);
            }
        }
        skin.load(file);

        return skin;
    }

    private BitmapFont generateFont(String fileName) {
        FileHandle file = new FileHandle(new File(fileName));

        final FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(file);
        final FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter.fontFileName = fileName;
        parameter.fontParameters.size = Gdx.graphics.getHeight() * 16 / Gdx.graphics.getWidth();
        parameter.fontParameters.color = Color.BLACK;
        parameter.fontParameters.borderColor = Color.WHITE;
        parameter.fontParameters.borderWidth = 2;

        BitmapFont mainFonts = fontGenerator.generateFont(parameter.fontParameters);
        fontGenerator.dispose();
        return mainFonts;
    }
}
