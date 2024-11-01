package com.vizor.test.model;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class ImageModel {
    private final String name;
    private final ImageIcon thumbnail;
    private final File file;

    public ImageModel(String name, File file) {
        this.name = name;
        this.file = file;
        this.thumbnail = new ImageIcon(new ImageIcon(file.getPath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    }

    public String getName() {
        return name;
    }

    public ImageIcon getThumbnail() {
        return thumbnail;
    }

    public File getFile() {
        return file;
    }
}