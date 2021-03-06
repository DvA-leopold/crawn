package com.crawn.game.model.resource.manager.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.crawn.game.model.accounts.MyAccount;


final public class PlayAccountLoader extends SynchronousAssetLoader<MyAccount, PlayAccountLoader.PlayAccountParameter> {
    public PlayAccountLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, PlayAccountParameter parameter) {
        return null;
    }

    @Override
    public MyAccount load(AssetManager assetManager, String fileName, FileHandle file, PlayAccountParameter parameter) {
        return new Json().fromJson(MyAccount.class, file);
    }


    static class PlayAccountParameter extends AssetLoaderParameters<MyAccount> { }
}
