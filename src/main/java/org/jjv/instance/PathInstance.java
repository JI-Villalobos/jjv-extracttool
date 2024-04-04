package org.jjv.instance;

import org.jjv.collections.FileType;

public class PathInstance {
    private static String path;
    private static String target;

    public static void setPath(String f){
        path = f;
    }
    public static void setTarget(String t) {
        target = t;
    }

    public static String getPath(){
        return path;
    }
    public static String getTarget(){
        return target;
    }

    public static boolean checkFileExtension(FileType type){
        return path.endsWith(type.getExtension());
    }
}
