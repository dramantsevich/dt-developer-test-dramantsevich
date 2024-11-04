package com.vizor.test.service;

import com.vizor.test.constant.PathConstants;
import com.vizor.test.model.ImageModel;
import com.vizor.test.utils.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vizor.test.constant.MessageConstants.*;

public class ImageService {
    private final List<ImageModel> allImages;

    public ImageService() {
        this.allImages = loadImages();
    }

    private List<ImageModel> loadImages() {
        return FileUtils.loadImagesFromAssets(PathConstants.DIRECTORY_PATH);
    }

    public List<ImageModel> getAllImages() {
        return new ArrayList<>(allImages);
    }

    public List<ImageModel> getImagesForDisplay(List<ImageModel> currentImages, int currentPage, int imagesPerPage) {
        if (currentImages.isEmpty()) {
            return new ArrayList<>();
        }

        int startIndex = currentPage * imagesPerPage;
        int endIndex = Math.min(startIndex + imagesPerPage, currentImages.size());

        if (startIndex < currentImages.size()) {
            return currentImages.subList(startIndex, endIndex);
        } else {
            return new ArrayList<>();
        }
    }

    public List<String> uploadImages() {
        List<String> uploadMessages = new ArrayList<>();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            for (File file : selectedFiles) {
                try {
                    uploadImage(file);
                    uploadMessages.add(file.getName() + " " + SUCCESS_UPLOAD_MESSAGE);
                } catch (IOException e) {
                    uploadMessages.add(e.getMessage());
                }
            }
        }
        return uploadMessages;
    }

    public void uploadImage(File file) throws IOException {
        File destinationFile = new File(PathConstants.DIRECTORY_PATH, file.getName());

        if (FileUtils.isImageFile(file)) {
            boolean alreadyExists = allImages.stream()
                    .anyMatch(imageModel -> imageModel.getName().equals(file.getName()));

            if (!alreadyExists) {
                Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                ImageModel imageModel = new ImageModel(file.getName(), destinationFile);

                allImages.add(imageModel);
            } else {
                throw new IOException( ALREADY_EXIST_MESSAGE + " " + file.getName());
            }
        } else {
            throw new IOException(INVALID_FILE_MESSAGE + " " + file.getName());
        }
    }

    public List<ImageModel> searchImages(String name) {
        return allImages.stream()
                .filter(imageModel -> imageModel.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}
