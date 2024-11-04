package com.vizor.test.model;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

import static com.vizor.test.constant.ViewConstants.IMAGE_THUMBNAIL_HEIGHT;
import static com.vizor.test.constant.ViewConstants.IMAGE_THUMBNAIL_WIDTH;

public class ImageModel {
    private final String name;
    private final ImageIcon thumbnail;
    private final File file;
    private final ImageIcon fullImage;

    public ImageModel(String name, File file) {
        this.name = name;
        this.file = file;
        this.thumbnail = createThumbnail(file);
        this.fullImage = new ImageIcon(file.getPath());
    }

    private ImageIcon createThumbnail(File file) {
        return new ImageIcon(new ImageIcon(file.getPath()).getImage().getScaledInstance(IMAGE_THUMBNAIL_WIDTH, IMAGE_THUMBNAIL_HEIGHT, Image.SCALE_SMOOTH));
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

    public ImageIcon getFullImage() {
        return fullImage;
    }
}
