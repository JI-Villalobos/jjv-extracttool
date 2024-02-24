package org.jjv.views;

import javax.swing.*;
import java.awt.Color;

import static javax.swing.GroupLayout.*;
import static javax.swing.GroupLayout.Alignment.*;
import static javax.swing.LayoutStyle.ComponentPlacement.*;

public class InitialView extends JFrame {
    private JButton chooseFileButton;
    private JComboBox<String> comboBox;
    private JScrollPane jScrollPane1;
    private JTextArea logTextArea;
    private JPanel panel;
    private JLabel selectModelLabel;

    public InitialView(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Iniciar Extracción");
        initComponents();

        chooseFileButton.addActionListener(e -> FileDialog.showFileDialog(this, "Selecciona tu archivo"));
    }

    private void initComponents() {

        panel = new JPanel();
        selectModelLabel = new JLabel();
        comboBox = new JComboBox<>();
        chooseFileButton = new JButton();
        jScrollPane1 = new JScrollPane();
        logTextArea = new JTextArea();

        selectModelLabel.setForeground(new Color(15, 48, 87));
        selectModelLabel.setText("Select your model template");

        comboBox.setForeground(new Color(15, 48, 87));
        comboBox.setModel(new DefaultComboBoxModel<>(new String[] { "RAW", "UNDEFINED" }));

        chooseFileButton.setBackground(new Color(0, 136, 145));
        chooseFileButton.setForeground(new Color(255, 255, 255));
        chooseFileButton.setText("Choose File");

        logTextArea.setEditable(false);
        logTextArea.setColumns(20);
        logTextArea.setForeground(new Color(15, 48, 87));
        logTextArea.setRows(5);
        logTextArea.setEnabled(false);
        jScrollPane1.setViewportView(logTextArea);

        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(LEADING)
                                        .addGroup(panelLayout.createSequentialGroup()
                                                .addGroup(panelLayout.createParallelGroup(LEADING)
                                                        .addGroup(panelLayout.createSequentialGroup()
                                                                .addGap(124, 124, 124)
                                                                .addComponent(selectModelLabel))
                                                        .addGroup(panelLayout.createSequentialGroup()
                                                                .addGap(78, 78, 78)
                                                                .addComponent(comboBox, PREFERRED_SIZE, 228, PREFERRED_SIZE))
                                                        .addGroup(panelLayout.createSequentialGroup()
                                                                .addGap(159, 159, 159)
                                                                .addComponent(chooseFileButton)))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(TRAILING, panelLayout.createSequentialGroup()
                                                .addGap(0, 10, Short.MAX_VALUE)
                                                .addComponent(jScrollPane1, PREFERRED_SIZE, 380, PREFERRED_SIZE)))
                                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(selectModelLabel)
                                .addPreferredGap(RELATED)
                                .addComponent(comboBox, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(chooseFileButton, PREFERRED_SIZE, 34, PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jScrollPane1, DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(LEADING)
                        .addComponent(panel, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(LEADING)
                        .addComponent(panel, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
}
