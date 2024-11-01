package com.vizor.test.utils;

import com.vizor.test.model.ImageModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<ImageModel> loadImagesFromAssets(String directoryPath) {
        List<ImageModel> images = new ArrayList<>();
        File assetsFolder = new File(directoryPath);

        // Проверяем, существует ли папка
        if (assetsFolder.exists() && assetsFolder.isDirectory()) {
            for (File file : assetsFolder.listFiles()) {
                if (file.isFile() && isImageFile(file)) {
                    images.add(new ImageModel(file.getName(), file));
                }
            }
        }
        return images;
    }

    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
    }
}
