package com.vizor.test.controller;

import com.vizor.test.model.ImageModel;
import com.vizor.test.utils.FileUtils;
import com.vizor.test.view.GalleryView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class GalleryController {
    private final String DIRECTORY_PATH = "src/main/resources/assets";
    private final GalleryView galleryView;

    public GalleryController(GalleryView galleryView) {
        this.galleryView = galleryView;
        loadImages();
        galleryView.getUploadButton().addActionListener(e -> uploadImages());
    }

    private void loadImages() {
        List<ImageModel> images = FileUtils.loadImagesFromAssets(DIRECTORY_PATH);
        galleryView.displayImages(images);
    }

    private void uploadImages() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int returnValue = fileChooser.showOpenDialog(galleryView);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            for (File file : selectedFiles) {
                try {
                    Files.copy(file.toPath(), new File(DIRECTORY_PATH + file.getName()).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            loadImages();
        }
    }
}
