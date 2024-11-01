package com.vizor.test.controller;

import com.vizor.test.model.ImageModel;
import com.vizor.test.utils.FileUtils;
import com.vizor.test.view.GalleryView;

import java.util.List;

public class GalleryController {
    private final GalleryView galleryView;

    public GalleryController(GalleryView galleryView) {
        this.galleryView = galleryView;
        loadImages();
    }

    private void loadImages() {
        List<ImageModel> images = FileUtils.loadImagesFromAssets("src/main/resources/assets");
        galleryView.displayImages(images);
    }
}
