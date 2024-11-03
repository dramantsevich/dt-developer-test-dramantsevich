package com.vizor.test.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.vizor.test.model.ImageModel;

import static com.vizor.test.constant.ViewConstants.*;

public class GalleryView extends JPanel {
    private final JPanel galleryPanel;
    private final JButton uploadButton;
    private final JTextField searchField;
    private final JButton searchButton;

    public GalleryView() {
        setLayout(new BorderLayout());

        uploadButton = new JButton(BUTTON_UPLOAD);
        galleryPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchButton = new JButton(BUTTON_SEARCH);

        JPanel topPanel = new JPanel();
        topPanel.add(uploadButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
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

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void showErrorMessage(String fileName, String message, String title) {
        JOptionPane.showMessageDialog(this, fileName + " " + message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String fileName, String message, String title) {
        JOptionPane.showMessageDialog(this, fileName + " " + message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}