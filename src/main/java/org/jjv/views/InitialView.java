package org.jjv.views;

import org.jjv.collections.FileType;
import org.jjv.instance.PathInstance;
import org.jjv.service.Decompress;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.Alignment.TRAILING;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.GroupLayout.PREFERRED_SIZE;
import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;
import static org.jjv.instance.LogCollectedInstance.getLogs;

public class InitialView extends JFrame {
    private JButton chooseFileButton;
    private JComboBox<String> comboBox;
    private JScrollPane jScrollPane1;
    private JTextArea logTextArea;
    private JPanel panel;
    private JLabel selectModelLabel;

    private boolean isJobExecuting;

    public InitialView(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Iniciar Servicio");
        setResizable(false);
        setLocationRelativeTo(null);
        initComponents();

        chooseFileButton.addActionListener(e -> setTargetFilePath());

        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                loadLogs();
                unzipFile();
            }
        });
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

        logTextArea.setColumns(20);
        logTextArea.setForeground(new Color(0,0,0));
        logTextArea.setRows(5);
        logTextArea.setEnabled(true);
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

    private void setTargetFilePath(){
        boolean selectedPath =FileDialog.showFileDialog(this, "Selecciona tu archivo", FileType.PDF);

        if (selectedPath){
            ControllerView.connectProgressView();
        }

    }

    private void loadLogs(){
        List<String> logs = getLogs();
        if (logs == null){
            logTextArea.setText("-----------JJV PDF Extract Tool--------------");
        } else {
            logs.forEach(log -> logTextArea.append("- " + log + "\r\n"));
        }

    }

    private void unzipFile(){
        if (PathInstance.getTarget() != null){
            try {
                Decompress.apply();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "An Error occurred and was not possible unzip the file",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
