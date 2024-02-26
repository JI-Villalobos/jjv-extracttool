package org.jjv.appfile;

import org.jjv.Globals;

import java.io.File;

import static org.jjv.Globals.*;

public class AppDirectory {
    public static boolean createDirectoryIfNotExists(){
        File file = new File(RESOURCES_PATH);
        if (file.exists()){
            return false;
        } else {
            return file.mkdir();
        }
    }
}
