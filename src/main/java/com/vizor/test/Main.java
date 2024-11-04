package com.vizor.test;

import com.vizor.test.constant.ViewConstants;
import com.vizor.test.controller.GalleryController;
import com.vizor.test.service.ImageService;
import com.vizor.test.view.GalleryView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;

import static com.vizor.test.constant.ViewConstants.HEIGHT;
import static com.vizor.test.constant.ViewConstants.WIDTH;

public class Main {
    public void run() {
        JFrame frame = new JFrame(ViewConstants.WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setLocationRelativeTo(null);

        ImageService imageService = new ImageService();
        GalleryView galleryView = new GalleryView();
        GalleryController galleryController = new GalleryController(galleryView, imageService);

        frame.add(galleryView);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main()::run);
    }
}
