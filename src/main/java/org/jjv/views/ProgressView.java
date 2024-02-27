package org.jjv.views;

import com.adobe.pdfservices.operation.exception.ServiceApiException;
import org.jjv.instance.LogCollectedInstance;
import org.jjv.service.ExtractData;

import javax.swing.*;

import java.awt.Color;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static javax.swing.GroupLayout.*;
import static javax.swing.GroupLayout.Alignment.*;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.Alignment.TRAILING;
import static javax.swing.SwingConstants.*;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.WindowConstants.*;
import static org.jjv.service.ExtractData.*;

public class ProgressView extends JFrame {
    private JLabel label;
    private JPanel panel;
    private JProgressBar progressBar;

    public ProgressView() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Extracting..");
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);

        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        executeTask(this);
        updateProgressBar();
    }
    private void initComponents() {

        panel = new JPanel();
        progressBar = new JProgressBar();
        label = new JLabel();

        label.setForeground(new Color(15, 48, 87));
        label.setHorizontalAlignment(CENTER);
        label.setText("Cargando.....");

        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addGroup(panelLayout.createParallelGroup(LEADING, false)
                                        .addComponent(label, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(progressBar, DEFAULT_SIZE, 199, Short.MAX_VALUE))
                                .addContainerGap(81, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(LEADING)
                        .addGroup(TRAILING, panelLayout.createSequentialGroup()
                                .addContainerGap(31, Short.MAX_VALUE)
                                .addComponent(label)
                                .addGap(18, 18, 18)
                                .addComponent(progressBar, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(26, 26, 26))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(LEADING)
                        .addComponent(panel, TRAILING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(LEADING)
                        .addComponent(panel, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void updateProgressBar(){
       Thread t = new Thread(new Runnable() {
           @Override
           public void run() {
               int value = 0;
               progressBar.setValue(value);

               while (value < 100){
                   try {
                       Thread.sleep(2000);
                       progressBar.setValue(value);
                       if (checkLogStatus() > 1) {
                           value = checkLogStatus() * 10;
                           label.setText(LogCollectedInstance.getLogs().get(checkLogStatus() - 1));
                       }
                       if (checkLogStatus() == 7)
                           value = 100;
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
           }
       });
       t.start();
    }

    private int checkLogStatus(){
        if (LogCollectedInstance.getLogs() == null)
            return 0;
        return LogCollectedInstance.getLogs().size();
    }

    private void executeTask(JFrame frame){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("trying");
                    ExtractTextTable();
                    frame.dispose();
                } catch (ServiceApiException | IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        t.start();
    }
}
