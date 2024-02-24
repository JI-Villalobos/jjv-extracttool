package org.jjv.views;

import org.jjv.collections.FileType;
import org.jjv.instance.PathInstance;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileFilter;

public class FileDialog {
    public static boolean showFileDialog(JFrame parent, String title, FileType fileType){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF File", fileType.getExtension());
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(parent) != JFileChooser.CANCEL_OPTION){
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            PathInstance.setPath(path);
            return true;
        }
        return false;
    }
}
