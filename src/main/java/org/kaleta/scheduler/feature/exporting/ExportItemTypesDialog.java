package org.kaleta.scheduler.feature.exporting;

import org.kaleta.scheduler.backend.entity.UserType;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Stanislav Kaleta on 29.12.2015.
 */
public class ExportItemTypesDialog extends JDialog {
    private BufferedReader input;
    private PrintWriter output;

    public ExportItemTypesDialog(){
        output = null;
        this.setTitle("Import Data");
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setModal(true);
        initComponents();
        this.pack();
    }

    private void initComponents() {
        JButton buttonFindDevice = new JButton("Find Device");
        JLabel labelFoundDeviceName = new JLabel("<NO DEVICE FOUND>");
        JTextField textFieldUserIpHint = new JTextField("192.168.1.*");
        JButton buttonExportTypes = new JButton("Export Item Types");
        buttonExportTypes.setEnabled(false);
        JButton buttonCancel = new JButton("Cancel");

        buttonFindDevice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new SwingWorker<Void,Void>(){
                    @Override
                    protected Void doInBackground() throws Exception {
                        buttonFindDevice.setEnabled(false);
                        textFieldUserIpHint.setEnabled(false);
                        String IpRx = "[0-9]+.[0-9]+.[0-9]+.[0-9]+";
                        String ipHint = (textFieldUserIpHint.getText().matches(IpRx)) ? textFieldUserIpHint.getText() : null;
                        String ip = tryFindDevice(ipHint);
                        if (ip != null){
                            labelFoundDeviceName.setText("Device found at " + ip);
                            buttonExportTypes.setEnabled(true);
                        } else {
                            labelFoundDeviceName.setText("<NO DEVICE FOUND>");
                            buttonFindDevice.setEnabled(true);
                            textFieldUserIpHint.setEnabled(true);
                        }
                        return null;
                    }
                }.execute();
            }
        });

        buttonExportTypes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                buttonExportTypes.setEnabled(false);
                output.println("exportingTypesStarted");
                for (UserType type : Service.configService().getSettings().getItemTypes()){
                    String msg = "item$" + type.getName() +"$"+ type.getSign() +"$";
                    for (String description : type.getPreparedDescriptions()){
                        msg += description + "&";
                    }
                    output.println(msg);
                }
                output.println("exportingTypesFinished");
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (output != null) {
                    output.println("thanks");
                }
                ExportItemTypesDialog.this.dispose();
            }
        });

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonFindDevice)
                                .addGap(5)
                                .addComponent(textFieldUserIpHint, 100, 100, Short.MAX_VALUE))
                        .addComponent(labelFoundDeviceName)
                        .addComponent(buttonExportTypes)
                        .addComponent(buttonCancel, GroupLayout.Alignment.CENTER))
                .addGap(10));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(buttonFindDevice, 25, 25, 25)
                        .addComponent(textFieldUserIpHint, 25, 25, 25))
                .addGap(5)
                .addComponent(labelFoundDeviceName)
                .addGap(5)
                .addComponent(buttonExportTypes)
                .addGap(10)
                .addComponent(buttonCancel)
                .addGap(10));
    }

    private String tryFindDevice(String ipHint) {
        if (ipHint != null){
            try{
                Socket socket = new Socket(ipHint, 7777);
                output = new PrintWriter(socket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                return ipHint;
            } catch  (IOException e) {
                JOptionPane.showMessageDialog(this, "Unable connect to set ip address: " + e.getMessage());
                // continue and start tracing IPs...
            }
        }

        for(int i=0;i<100;i++){
            String ip = "192.168.1." + i;
            try{
                Socket socket = new Socket(ip, 7777);
                output = new PrintWriter(socket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                return ip;
            } catch  (IOException e) {
                // continue tracking...
            }
        }
        return null;
    }
}
