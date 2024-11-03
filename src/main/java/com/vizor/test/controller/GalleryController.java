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
import java.util.stream.Collectors;

import static com.vizor.test.constant.MessageConstants.*;

public class GalleryController {
    private final GalleryView galleryView;
    private final List<ImageModel> allImages;
    private List<ImageModel> currentImages;


    private static final int IMAGES_PER_PAGE = 8;
    private int currentPage = 0;

    public GalleryController(GalleryView galleryView) {
        this.galleryView = galleryView;
        this.allImages = new ArrayList<>();
        this.currentImages = new ArrayList<>();
        loadImages();
        setupListeners();
        updateGallery(); // Отображаем начальную галерею
    }

    private void setupListeners() {
        galleryView.getUploadButton().addActionListener(e -> uploadImages());
        galleryView.getSearchButton().addActionListener(e -> searchImages(galleryView.getSearchField().getText()));
        galleryView.getNextButton().addActionListener(e -> nextPage());
        galleryView.getPreviousButton().addActionListener(e -> previousPage());
    }

    private void loadImages() {
        allImages.addAll(FileUtils.loadImagesFromAssets(PathConstants.DIRECTORY_PATH));
        currentImages.addAll(allImages);
    }

    private void updateGallery() {
        int startIndex = currentPage * IMAGES_PER_PAGE;
        int endIndex = Math.min(startIndex + IMAGES_PER_PAGE, currentImages.size());
        List<ImageModel> imagesToDisplay = currentImages.subList(startIndex, endIndex);
        galleryView.displayImages(imagesToDisplay);
        updatePagination();
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
            updateGallery();
        }
    }

    private void uploadImage(File file) {
        File destinationFile = new File(PathConstants.DIRECTORY_PATH, file.getName());

        try {
            if (FileUtils.isImageFile(file)) {
                boolean alreadyExists = allImages.stream()
                        .anyMatch(imageModel -> imageModel.getName().equals(file.getName()));

                if (!alreadyExists) {
                    Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    ImageModel imageModel = new ImageModel(file.getName(), destinationFile);
                    allImages.add(imageModel);
                    currentImages.add(imageModel);

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
        currentImages = allImages.stream()
                .filter(imageModel -> imageModel.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        currentPage = 0;
        updateGallery();
    }

    private void nextPage() {
        if ((currentPage + 1) * IMAGES_PER_PAGE < currentImages.size()) {
            currentPage++;
            updateGallery();
        }
    }

    private void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            updateGallery();
        }
    }

    private void updatePagination() {
        galleryView.updatePaginationInfo(currentPage, (int) Math.ceil((double) currentImages.size() / IMAGES_PER_PAGE));
    }
}
