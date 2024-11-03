package com.vizor.test.controller;

import com.vizor.test.constant.PathConstants;
import com.vizor.test.model.ImageModel;
import com.vizor.test.utils.FileUtils;
import com.vizor.test.view.GalleryView;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static com.vizor.test.constant.MessageConstants.*;

public class GalleryController {
    private final GalleryView galleryView;
    private final List<ImageModel> images;

    public GalleryController(GalleryView galleryView) {
        this.galleryView = galleryView;
        this.images = new ArrayList<>();
        loadImages();
        setupListeners();
    }

    private void setupListeners() {
        galleryView.getUploadButton().addActionListener(e -> uploadImages());
        galleryView.getSearchButton().addActionListener(e -> {
            String searchTerm = galleryView.getSearchField().getText().trim();
            searchImages(searchTerm);
        });
    }

    private void loadImages() {
        List<ImageModel> loadedImages = FileUtils.loadImagesFromAssets(PathConstants.DIRECTORY_PATH);
        images.addAll(loadedImages);
        galleryView.displayImages(images);
    }

    private void uploadImages() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        int returnValue = fileChooser.showOpenDialog(galleryView);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            for (File file : selectedFiles) {
                uploadImage(file);
            }
        }
    }

    private void uploadImage(File file) {
        File destinationFile = new File(PathConstants.DIRECTORY_PATH, file.getName());

        try {
            if (FileUtils.isImageFile(file)) {
                boolean alreadyExists = images.stream()
                        .anyMatch(imageModel -> imageModel.getName().equals(file.getName()));

                if (!alreadyExists) {
                    Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    ImageModel imageModel = new ImageModel(file.getName(), destinationFile);
                    images.add(imageModel);

                    galleryView.displayImages(images);
                    galleryView.showMessage(file.getName(), SUCCESS_UPLOAD_MESSAGE,
                            SUCCESS_UPLOAD);
                } else {
                    galleryView.showErrorMessage(file.getName(), ALREADY_EXIST_MESSAGE,
                            ALREADY_EXIST);
                }
            } else {
                galleryView.showErrorMessage(file.getName(), INVALID_FILE_MESSAGE, INVALID_FILE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchImages(String name) {
        List<ImageModel> filteredImages = new ArrayList<>();
        for (ImageModel image : images) {
            if (image.getName().toLowerCase().contains(name.toLowerCase())) {
                filteredImages.add(image);
            }
        }
        galleryView.displayImages(filteredImages);
    }
}
