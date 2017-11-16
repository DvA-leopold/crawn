package com.crawn.game.model.content;


import java.util.HashMap;
import java.util.NoSuchElementException;


final public class ContentTypeConverter {
    static {
        typesMapping = new HashMap<String, ContentType>() {{
            put("video", ContentType.VIDEO);
            put("music", ContentType.MUSIC);
            put("photo", ContentType.PHOTO);
        }};
    }

    public static ContentType stringToType(final String contentType) throws NoSuchElementException {
        if (!typesMapping.containsKey(contentType)) {
            throw new NoSuchElementException("no such element in map: " + contentType);
        }

        return typesMapping.get(contentType);
    }

    public enum ContentType {
        VIDEO,
        PHOTO,
        MUSIC
    }


    private static HashMap<String, ContentType> typesMapping;
}
