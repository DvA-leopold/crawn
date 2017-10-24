package com.crawn.game.utils.resource.manager;


import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.crawn.game.model.PlayAccount;
import com.crawn.game.utils.resource.manager.loaders.FreeTypeFontLoader;
import com.crawn.game.utils.resource.manager.loaders.FreeTypeFontSkinLoader;
import com.crawn.game.utils.resource.manager.loaders.PlayAccountLoader;

import java.io.FileNotFoundException;
import java.util.*;


public class ResourceManager {
    private ResourceManager() {
        assetManager = new AssetManager();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(BitmapFont.class, new FreeTypeFontLoader());
        assetManager.setLoader(PlayAccount.class, new PlayAccountLoader());
        assetManager.setLoader(Skin.class, new FreeTypeFontSkinLoader());

        mimeFileTypes = new Hashtable<>();
        mimeFileTypes.put("png", Texture.class);
        mimeFileTypes.put("jpeg", Texture.class);
        mimeFileTypes.put("jpg", Texture.class);
        mimeFileTypes.put("bmp", Texture.class);

        mimeFileTypes.put("pack", TextureAtlas.class);
        mimeFileTypes.put("atlas", TextureAtlas.class);

        mimeFileTypes.put("mp3", Music.class);
        mimeFileTypes.put("ogg", Audio.class);

        mimeFileTypes.put("fnt", BitmapFont.class);
        mimeFileTypes.put("ttf", BitmapFont.class);

        mimeFileTypes.put("json", Skin.class);
        mimeFileTypes.put("cjson", PlayAccount.class);

        mimeFileTypes.put("properties", I18NBundle.class);
    }

    public static ResourceManager instance() {
        return SingletonHolder.instance;
    }

    /**
     * load all files in section folder and sub folder of this section
     * @param section path to the folder
     * @param sync if this is true then we will wait till all files are load
     */
    public void loadSection(String section, boolean sync) {
        FileHandle sectionRoot = Gdx.files.internal(section);
        try {
            FileHandle[] allFiles = getFiles(sectionRoot);
            for (FileHandle file : allFiles) {
                String fileName = file.file().getName();
                String extension = getExtension(fileName);
                if (mimeFileTypes.containsKey(extension)) {
                    switch (extension) {
                        case "properties":
                            assetManager.load(file.pathWithoutExtension(), I18NBundle.class);
                            break;
                        case "ttf":
                        {
                            FreetypeFontLoader.FreeTypeFontLoaderParameter params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
                            params.fontFileName = sectionRoot.path() + "/" + fileName;
                            assetManager.load(file.path(), BitmapFont.class, params);
                        }
                        default:
                            assetManager.load(file.path(), mimeFileTypes.get(extension));
                    }
                }
            }
            if (sync) {
                assetManager.finishLoading();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * unload chosen files in a folder and files in a sub-folders
     * @param section path to the folder
     */
    public void unloadSection(String section) {
        FileHandle sectionRoot = Gdx.files.internal(section);
        try {
            FileHandle[] allFiles = getFiles(sectionRoot);
            for (FileHandle allFile : allFiles) {
                assetManager.unload(allFile.path());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * load separate file from a directory
     * @param fileName name of the file fo downloading
     * @param sync if this parameter is <code>true</code> than stop and wait loading finished/
     */
    public void loadFile(String fileName, boolean sync) {
        String fileExtension = getExtension(fileName);
        if (mimeFileTypes.containsKey(fileExtension)) {
            assetManager.load(fileName, mimeFileTypes.get(fileExtension));
        } else {
            Gdx.app.error("load file", "this file extension does not exits: " + fileExtension);
        }
        if (sync) {
            assetManager.finishLoading();
        }
    }

    public void unloadFile(String fileName) {
        assetManager.unload(fileName);
    }

    public Object get(String fileName) {
        if (assetManager.isLoaded(fileName)) {
            return assetManager.get(fileName);
        } else {
            System.err.println(fileName + " was not loaded");
            return null;
        }
    }

    public float updateAndGetProgress() {
        assetManager.update();
        return assetManager.getProgress();
    }

    public void dispose() {
        assetManager.dispose();
    }

    private FileHandle[] getFiles(FileHandle sectionForLoading) throws FileNotFoundException {
        if (!sectionForLoading.isDirectory()) {
            throw new FileNotFoundException(" this is not a directory: " + sectionForLoading.path());
        }
        Queue<FileHandle> fileHandles = new LinkedList<>();
        LinkedList<FileHandle> filesList = new LinkedList<>();

        fileHandles.add(sectionForLoading);
        while (!fileHandles.isEmpty()) {
            FileHandle[] filesInFolder = fileHandles.poll().list();
            for (FileHandle aFilesInFolder : filesInFolder) {
                if (aFilesInFolder.isDirectory()) {
                    fileHandles.add(aFilesInFolder);
                } else {
                    filesList.add(aFilesInFolder);
                }
            }
        }
        FileHandle[] filesListArray = new FileHandle[filesList.size()];
        filesList.toArray(filesListArray);
        return filesListArray;
    }

    private String getExtension(String filePath) throws NoSuchElementException {
        int index = filePath.lastIndexOf(".");
        if (index == -1) {
            throw new NoSuchElementException("this file had no extension");
        }
        return filePath.substring(index + 1);
    }

    private static class SingletonHolder {
        private static final ResourceManager instance = new ResourceManager();
    }


    final private AssetManager assetManager;
    final private Hashtable<String, Class> mimeFileTypes;
}