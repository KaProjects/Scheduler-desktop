package org.kaleta.scheduler.feature.importing.mobile;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.frontend.Initializer;
import org.kaleta.scheduler.frontend.common.ErrorDialog;
import org.kaleta.scheduler.frontend.dialog.SelectMonthDialog;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Stanislav Kaleta on 13.11.2015.
 */
public class ImportCollectedDataDialog extends JDialog{
    private BufferedReader input;
    private PrintWriter output;

    private List<TempMonth> importedMonths;

    public ImportCollectedDataDialog(){
        importedMonths = new ArrayList<>();
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
        JPanel panelLoadedMonths = new JPanel();
        panelLoadedMonths.setLayout(new BoxLayout(panelLoadedMonths, BoxLayout.Y_AXIS));
        JButton buttonCancel = new JButton("Cancel");

        buttonFindDevice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        buttonFindDevice.setEnabled(false);
                        textFieldUserIpHint.setEnabled(false);
                        String IpRx = "[0-9]+.[0-9]+.[0-9]+.[0-9]+";
                        String ipHint = (textFieldUserIpHint.getText().matches(IpRx)) ? textFieldUserIpHint.getText() : null;
                        String ip = tryFindDevice(ipHint);
                        if (ip != null) {
                            labelFoundDeviceName.setText("Device found at " + ip);
                            boolean result = collectData();
                            if (result) {
                                panelLoadedMonths.removeAll();
                                for (TempMonth month : importedMonths) {
                                    JPanel panelBg = new JPanel();
                                    panelBg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                                    String info = "Month \"" + month.getName() + "\" " + month.getNotImportedItems().size()
                                            + " not exported items out of " + month.getTotalItemsCount();
                                    JLabel labelInfo = new JLabel(info);
                                    panelBg.add(labelInfo);
                                    panelLoadedMonths.add(panelBg);
                                    panelBg.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            boolean result = performImport(month);
                                            if (result) {
                                                panelBg.setVisible(false);
                                                ImportCollectedDataDialog.this.pack();
                                            }
                                        }
                                    });
                                    ImportCollectedDataDialog.this.pack();
                                }
                            } else {
                                buttonFindDevice.setEnabled(true);
                                textFieldUserIpHint.setEnabled(true);
                            }
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

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (output != null) {
                    output.println("thanks");
                }
                ImportCollectedDataDialog.this.dispose();
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
                        .addComponent(panelLoadedMonths)
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
                .addComponent(panelLoadedMonths)
                .addGap(5)
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
                Initializer.LOG.warning(e.getMessage());
                new ErrorDialog(e).setVisible(true);
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

    private boolean collectData(){
        output.println("demand");
        try {
            TempMonth tempMonth = null;
            importedMonths.clear();

            boolean transferring = true;
            while (transferring){
                String recordReceived = input.readLine();
                if(recordReceived.startsWith("month")){
                    if (tempMonth == null){
                        tempMonth = new TempMonth();
                        tempMonth.setName(recordReceived.split("\\$")[1]);
                        tempMonth.setTotalItemsCount(Integer.valueOf(recordReceived.split("\\$")[2]));
                    } else {
                        importedMonths.add(tempMonth);
                        tempMonth = new TempMonth();
                        tempMonth.setName(recordReceived.split("\\$")[1]);
                        tempMonth.setTotalItemsCount(Integer.valueOf(recordReceived.split("\\$")[2]));
                    }
                }
                if(recordReceived.startsWith("item")){
                    Item item = new Item();
                    String[] values = recordReceived.split("\\$");
                    item.setIncome(values[1].equals("+"));
                    item.setAmount(BigDecimal.valueOf(Double.valueOf(values[2])));
                    item.setDay(Integer.valueOf(values[3]));
                    item.setType(values[4]);
                    if (values.length < 6){
                        item.setDescription("");
                    } else {
                        item.setDescription(values[5]);
                    }
                    tempMonth.getNotImportedItems().add(item);
                }
                if(recordReceived.equals("done")){
                    transferring = false;
                }
            }

            if (tempMonth != null){
                importedMonths.add(tempMonth);
            }
            return true;
        } catch (IOException e) {
            Initializer.LOG.severe(e.getMessage());
            new ErrorDialog(e).setVisible(true);
            return false;
        }
    }

    private boolean performImport(TempMonth month){
        if (month.getNotImportedItems().size() == 0){
            JOptionPane.showMessageDialog(this,"Nothing to import.");
            return false;
        }
        SelectMonthDialog dialog = new SelectMonthDialog();
        Map<Integer, Integer> orders = Service.monthService().getMonthsOrder();
        String[] monthNames = new String[orders.size()];
        Integer[] monthIds = new Integer[orders.size()];
        int actuallySelectedMonthIndex = -1;
        int index = 0;
        for (Integer key : orders.keySet()) {
            monthNames[index] = Service.monthService().getMonthName(key);
            monthIds[index] = key;
            index++;
        }
        dialog.setMonthNames(monthNames, actuallySelectedMonthIndex);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        if (dialog.getResult()) {
            Integer selectedMonthId = monthIds[dialog.getSelectedMonthIndex()];
            String msg = month.getNotImportedItems().size() + " items from month \"" + month.getName() + "\" will be added to month \""
                    + Service.monthService().getMonthName(selectedMonthId) + "\". Continue?";
            int result = JOptionPane.showConfirmDialog(this, msg,"Adding items",JOptionPane.YES_NO_OPTION);
            if (result == 0){
                for (Item item : month.getNotImportedItems()){
                    Service.itemService().addItem(item, selectedMonthId, item.getDay());
                }
                output.println("imported$"+month.getName());
                return true;
            }
        }
        return false;
    }

    private class TempMonth{
        private String name;
        private Integer totalItemsCount;
        private List<Item> notImportedItems;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getTotalItemsCount() {
            return totalItemsCount;
        }

        public void setTotalItemsCount(Integer totalItemsCount) {
            this.totalItemsCount = totalItemsCount;
        }

        public List<Item> getNotImportedItems() {
            if (notImportedItems == null){
                notImportedItems = new ArrayList<>();
            }
            return notImportedItems;
        }
    }
}
