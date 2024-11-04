package com.vizor.test.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import com.vizor.test.model.ImageModel;

import static com.vizor.test.constant.ViewConstants.*;

public class GalleryView extends JPanel {
    private final JPanel galleryPanel;
    private final JButton uploadButton;
    private final JTextField searchField;
    private final JButton searchButton;
    private final JButton nextButton;
    private final JButton previousButton;
    private final JLabel pageInfoLabel;

    public GalleryView() {
        setLayout(new BorderLayout());

        galleryPanel = new JPanel(new GridLayout(GALLERY_PANEL_ROWS, GALLERY_PANEL_COLUMNS, GALLERY_PANEL_HGAP, GALLERY_PANEL_VGAP));
        uploadButton = new JButton(BUTTON_UPLOAD);
        searchField = new JTextField(SEARCH_FIELD_SIZE);
        searchButton = new JButton(BUTTON_SEARCH);
        previousButton = new JButton(BUTTON_PREVIOUS);
        nextButton = new JButton(BUTTON_NEXT);
        pageInfoLabel = new JLabel();

        addComponents();
    }

    private void addComponents() {
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        add(galleryPanel, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.add(uploadButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(previousButton);
        topPanel.add(pageInfoLabel);
        topPanel.add(nextButton);
        return topPanel;
    }

    public void displayImages(List<ImageModel> images) {
        galleryPanel.removeAll();
        addImageLabels(images);
        galleryPanel.revalidate();
        galleryPanel.repaint();
    }

    private void addImageLabels(List<ImageModel> images) {
        for (ImageModel image : images) {
            JLabel label = new JLabel(image.getThumbnail());
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showFullImage(image);
                }
            });
            galleryPanel.add(label);
        }
    }

    private void showFullImage(ImageModel image) {
        JDialog currentDialog = new JDialog();
        currentDialog.setTitle(image.getName());
        currentDialog.setModal(true);

        JLabel imageLabel = new JLabel(image.getFullImage());
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createEmptyBorder(IMAGE_DIALOG_PADDING, IMAGE_DIALOG_PADDING, IMAGE_DIALOG_PADDING, IMAGE_DIALOG_PADDING));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        currentDialog.add(imagePanel);
        currentDialog.pack();

        currentDialog.setLocationRelativeTo(this);
        currentDialog.setVisible(true);
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

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getPreviousButton() {
        return previousButton;
    }

    public void updatePaginationInfo(int currentPage, int totalPages) {
        pageInfoLabel.setText("Page " + (currentPage + 1) + " of " + totalPages);
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}