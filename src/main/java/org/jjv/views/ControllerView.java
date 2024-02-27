package org.jjv.views;

import org.jjv.Globals;
import org.jjv.appfile.AppDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ControllerView {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerView.class);
    public static void connectMainView(){
        if (AppDirectory.createDirectoryIfNotExists())
            LOGGER.info("Directory created");
        InitialView initialView = new InitialView();
        initialView.setVisible(true);
    }

    public static void connectProgressView(){
        ProgressView progressView = new ProgressView();
        progressView.setVisible(true);
    }
}
