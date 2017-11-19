package com.crawn.game.utils.resource.manager.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.crawn.game.model.PlayAccount;


final public class PlayAccountLoader extends SynchronousAssetLoader<PlayAccount, PlayAccountLoader.PlayAccountParameter> {
    public PlayAccountLoader() {
        super(new InternalFileHandleResolver());
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, PlayAccountParameter parameter) {
        return null;
    }

    @Override
    public PlayAccount load(AssetManager assetManager, String fileName, FileHandle file, PlayAccountParameter parameter) {
        return new Json().fromJson(PlayAccount.class, file);
    }


    static class PlayAccountParameter extends AssetLoaderParameters<PlayAccount> { }
}
