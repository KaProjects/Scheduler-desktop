package org.kaleta.scheduler.ex;

import org.kaleta.scheduler.backend.entity.Item;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 3.10.2014
 * To change this template use File | Settings | File Templates.
 */
public class FrameImportData extends JFrame {
    private final String NO_FILE_SELECTED_TEXT;
    private JButton buttonAcceptImport;
    private JFileChooser fileChooser;
    private JButton buttonToChooseFile;
    private File file;
    private JLabel labelFileName;
    private JTextArea importingLog;
    private JScrollPane logScrollPane;


    private List<Item> items;


    public FrameImportData(JButton acceptButton, String noFileSelectedText, String buttonSelectText){
        NO_FILE_SELECTED_TEXT = noFileSelectedText;
        buttonAcceptImport = acceptButton;
        fileChooser = new JFileChooser();
        buttonToChooseFile = new JButton();
        buttonToChooseFile.setText(buttonSelectText);
        file = null;
        labelFileName = new JLabel(NO_FILE_SELECTED_TEXT);
        labelFileName.setFont(new Font("times new roman",1,15));
        importingLog = new JTextArea();
        logScrollPane = new JScrollPane(importingLog);


        items = new ArrayList<Item>();

        initLayout();

        buttonToChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(FrameImportData.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile.getName().contains(".txt")) {
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                            if (br.readLine().equals("INIT")) {
                                file = selectedFile;
                                labelFileName.setText(file.getName());
                                items = parseFile();
                            }
                            br.close();
                        } catch (IOException ex) {
                            /*TODO log*/
                        }
                    }
                }
            }
        });

        setSize(new Dimension(300,500));
        setResizable(false);
        setVisible(false);

    }

    private void initLayout(){
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(labelFileName, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonToChooseFile, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                        .addComponent(logScrollPane)
                        .addComponent(buttonAcceptImport, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                            .addComponent(labelFileName,GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonToChooseFile,GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                    .addComponent(logScrollPane)
                    .addComponent(buttonAcceptImport)
        );
    }

    private List<Item> parseFile(){
        /*TODO start parsing*/
        List<Item> newItems = new ArrayList<Item>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null){
                if (line.equals("INIT")) {
                    importingLog.append(" > Loading file..." + "\n");
                } else {
                    String[] strings = line.split(";");
                    Item item = new Item();
                    item.setDay(Integer.parseInt(strings[0]));
                    item.setType(strings[1]);
                    item.setAmount(new BigDecimal(strings[2]));
                    /*TODO to app -/+ */item.setIncome(false);
                    importingLog.append(" > day: " + strings[0] + " = " + strings[1] + " " + strings[2] + "\n");
                    newItems.add(item);
                }
            }
            importingLog.append(" > Loading done! ");
            br.close();
        } catch (IOException ex){
            /*TODO log*/
        }
        return newItems;
    }

    public List<Item> getImportedData(){
        List<Item> newItems = new ArrayList<Item>();
        newItems.addAll(items);
        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println("INIT");
            writer.close();
            items.clear();
            file = null;
            importingLog.setText("");
            labelFileName.setText(NO_FILE_SELECTED_TEXT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();   /*TODO log*/
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();    /*TODO log*/
        }
        return newItems;



    }

}
