package org.jjv.instance;

import org.jjv.collections.FileType;

public class PathInstance {
    private static String path;

    public static void setPath(String f){
        path = f;
    }

    public static String getPath(){
        return path;
    }

    public static boolean checkFileExtension(FileType type){
        return path.endsWith(type.getExtension());
    }
}
