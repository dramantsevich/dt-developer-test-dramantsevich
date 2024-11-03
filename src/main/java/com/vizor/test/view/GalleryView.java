package com.vizor.test.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.vizor.test.constant.ViewConstants;
import com.vizor.test.model.ImageModel;

public class GalleryView extends JPanel {
    private final JPanel galleryPanel;
    private final JButton uploadButton;

    public GalleryView() {
        setLayout(new BorderLayout());

        uploadButton = new JButton(ViewConstants.BUTTON_UPLOAD);
        galleryPanel = new JPanel(new FlowLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(uploadButton);
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

    public JButton getUploadButton() {
        return uploadButton;
    }

    public void showErrorMessage(String fileName, String message, String title) {
        JOptionPane.showMessageDialog(this, fileName + " " + message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String fileName, String message, String title) {
        JOptionPane.showMessageDialog(this, fileName + " " + message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}