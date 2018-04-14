package com.crawn.game.model.resource.manager;


import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLoader;
import com.badlogic.gdx.assets.AssetManager;
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
import com.crawn.game.model.accounts.MyAccount;
import com.crawn.game.model.resource.manager.loaders.FreeTypeFontLoader;
import com.crawn.game.model.resource.manager.loaders.SkinWithGeneratedFreeTypeFontLoader;
import com.crawn.game.model.resource.manager.loaders.PlayAccountLoader;

import java.io.FileNotFoundException;
import java.util.*;


final public class ResourceManager {
    private ResourceManager() {
        assetManager = new AssetManager();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(assetManager.getFileHandleResolver()));
        assetManager.setLoader(BitmapFont.class, new FreeTypeFontLoader(assetManager.getFileHandleResolver()));
        assetManager.setLoader(MyAccount.class, new PlayAccountLoader(assetManager.getFileHandleResolver()));
        assetManager.setLoader(Skin.class, new SkinWithGeneratedFreeTypeFontLoader(assetManager.getFileHandleResolver()));
        assetManager.setLoader(BehaviorTree.class, new BehaviorTreeLoader(assetManager.getFileHandleResolver()));

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
        mimeFileTypes.put("cjson", MyAccount.class);

        mimeFileTypes.put("properties", I18NBundle.class);

        mimeFileTypes.put("btree", BehaviorTree.class);
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
        FileHandle sectionRoot = Gdx.files.local(section);
        try {
            for (FileHandle file: getFiles(sectionRoot)) {
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
                            assetManager.load(file.path(), BitmapFont.class, params); // TODO try to refactor
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
            Gdx.app.error(getClass().getCanonicalName(), e.getMessage());
        }
    }

    /**
     * unload chosen files in a folder and files in a sub-folders
     * @param section path to the folder
     */
    public void unloadSection(String section) {
        FileHandle sectionRoot = Gdx.files.internal(section);
        try {
            for (FileHandle allFile: getFiles(sectionRoot)) {
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
            Gdx.app.error(getClass().getCanonicalName(), "this file extension does not exits: " + fileExtension);
        }

        if (sync) {
            assetManager.finishLoading();
        }

        Gdx.app.log(getClass().getCanonicalName(), "loading finished for: " + fileName);
    }

    public void unloadFile(String fileName) {
        assetManager.unload(fileName);
    }

    public Object get(String fileName) {
        return assetManager.get(fileName);
    }

    public float updateAndGetProgress() {
        assetManager.update();
        return assetManager.getProgress();
    }

    public void dispose() {
        assetManager.dispose();
    }

    private ArrayList<FileHandle> getFiles(FileHandle sectionForLoading) throws FileNotFoundException {
        if (!sectionForLoading.isDirectory()) {
            throw new FileNotFoundException(" this is not a directory: " + sectionForLoading.path() + " type: " + sectionForLoading.type());
        }
        ArrayDeque<FileHandle> directoriesQueue = new ArrayDeque<>();
        ArrayList<FileHandle> filesList = new ArrayList<>(50);

        directoriesQueue.add(sectionForLoading);
        while (!directoriesQueue.isEmpty()) {
            FileHandle handle = directoriesQueue.poll();
            if (handle == null)
                continue;

            for (FileHandle file: handle.list()) {
                if (file.isDirectory()) {
                    directoriesQueue.add(file);
                } else {
                    filesList.add(file);
                }
            }
        }
        return filesList;
    }

    private String getExtension(String filePath) {
        int index = filePath.lastIndexOf(".");
        return (index == -1) ? "" : filePath.substring(index + 1);
    }

    private static class SingletonHolder {
        private static final ResourceManager instance = new ResourceManager();
    }


    final private AssetManager assetManager;
    final private Hashtable<String, Class> mimeFileTypes;
}