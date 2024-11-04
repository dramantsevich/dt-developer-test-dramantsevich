package com.vizor.test.controller;

import com.vizor.test.model.ImageModel;
import com.vizor.test.service.ImageService;
import com.vizor.test.view.GalleryView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static com.vizor.test.constant.MessageConstants.*;
import static com.vizor.test.constant.ViewConstants.IMAGES_PER_PAGE;

public class GalleryController {
    private final GalleryView galleryView;
    private final ImageService imageService;
    private List<ImageModel> currentImages = new ArrayList<>();
    private int currentPage = 0;

    public GalleryController(GalleryView galleryView, ImageService imageService) {
        this.galleryView = galleryView;
        this.imageService = imageService;

        loadImages();
        setupListeners();
        updateGallery();
    }

    private void setupListeners() {
        galleryView.getUploadButton().addActionListener(e -> uploadImages());
        galleryView.getSearchButton().addActionListener(e -> searchImages(galleryView.getSearchField().getText()));
        galleryView.getNextButton().addActionListener(e -> nextPage());
        galleryView.getPreviousButton().addActionListener(e -> previousPage());
    }

    private void loadImages() {
        currentImages = imageService.getAllImages();
    }

    private void updateGallery() {
        List<ImageModel> imagesToDisplay = imageService.getImagesForDisplay(currentImages, currentPage, IMAGES_PER_PAGE);

        if (imagesToDisplay.isEmpty()) {
            galleryView.showMessage(ERROR_UPDATE_MESSAGE, ERROR_UPDATE, JOptionPane.ERROR_MESSAGE);
        }

        galleryView.displayImages(imagesToDisplay);
        updatePagination();
    }

    private void uploadImages() {
        List<String> uploadMessages = imageService.uploadImages();

        currentImages = imageService.getAllImages();

        for (String message : uploadMessages) {
            if(message.contains("Invalid") || message.contains("exists"))
            {
                galleryView.showMessage(message, ERROR_UPLOAD, JOptionPane.ERROR_MESSAGE);
            }
            else {
                galleryView.showMessage(message, SUCCESS_UPLOAD, JOptionPane.NO_OPTION);
            }
        }

        updateGallery();
    }

    public void searchImages(String name) {
        currentImages = imageService.searchImages(name);
        currentPage = 0;

        if (currentImages.isEmpty()) {
            galleryView.showMessage(ERROR_SEARCH_RESULT_MESSAGE + " " + name, SEARCH_RESULT, JOptionPane.ERROR_MESSAGE);
        } else {
            updateGallery();
        }
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
