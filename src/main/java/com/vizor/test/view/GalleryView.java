package com.vizor.test.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.vizor.test.model.ImageModel;

public class GalleryView extends JPanel {
    private final JPanel galleryPanel;

    public GalleryView() {
        setLayout(new BorderLayout());

        galleryPanel = new JPanel(new FlowLayout());

        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);
        add(galleryPanel, BorderLayout.CENTER);
    }

    public void displayImages(List<ImageModel> images) {
        galleryPanel.removeAll();
        for (ImageModel image : images) {
            JLabel label = new JLabel(image.getThumbnail());
            galleryPanel.add(label);
        }
        galleryPanel.revalidate();
        galleryPanel.repaint();
    }
}