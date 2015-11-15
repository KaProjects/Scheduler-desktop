package org.kaleta.scheduler.feature.importing.version1;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.frontend.Initializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Stanislav Kaleta on 13.11.2015.
 */
public class ImportDialog extends JDialog {
    private boolean result;
    private Document document;

    private JFileChooser fileChooser;

    public ImportDialog(){
        result = false;
        document = null;
        fileChooser = new JFileChooser();
        this.setTitle("Import Data");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        this.setModal(true);
        this.pack();

    }

    private void initComponents() {
        JButton buttonSelectFile = new JButton("Select File");

        JPanel panelState = new JPanel();
        panelState.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelState.setBackground(Color.LIGHT_GRAY);
        JLabel labelFileName = new JLabel("<NO FILE SELECTED>");
        JLabel labelInfo = new JLabel("");
        JButton buttonLoadData = new JButton("Load");
        buttonLoadData.setEnabled(false);
        panelState.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 5));
        panelState.add(buttonLoadData);
        panelState.add(labelFileName);
        panelState.add(labelInfo);

        JTextArea textAreaData = new JTextArea();
        textAreaData.setFont(new Font(textAreaData.getFont().getName(),0,15));
        textAreaData.setEditable(false);
        JScrollPane scrollPaneData = new JScrollPane(textAreaData);
        scrollPaneData.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //TODO solve scrollpane resize after scroll bar appear (bar blocking view)
        scrollPaneData.setVisible(false);

        JButton buttonImport = new JButton("Import");
        buttonImport.setEnabled(false);

        JButton buttonCancel = new JButton("Cancel");

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(buttonSelectFile)
                        .addComponent(panelState)
                        .addComponent(scrollPaneData)
                        .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                .addComponent(buttonImport, 80, 80, 80)
                                .addGap(5)
                                .addComponent(buttonCancel, 80, 80, 80)))
                .addGap(10));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addComponent(buttonSelectFile, 25, 25, 25)
                .addGap(5)
                .addComponent(panelState, 35, 35, 35)
                .addGap(5)
                .addComponent(scrollPaneData, 50, 200, Short.MAX_VALUE)
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(buttonImport, 25, 25, 25)
                        .addComponent(buttonCancel, 25, 25, 25))
                .addGap(10));

        buttonSelectFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                buttonImport.setEnabled(false);
                scrollPaneData.setVisible(false);
                labelFileName.setText("<NO FILE SELECTED>");
                labelInfo.setText("");
                textAreaData.setText("");
                ImportDialog.this.pack();
                int result = fileChooser.showOpenDialog(ImportDialog.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                //if (selectedFile.getName().contains(".xml")) {
                    document = tryParseFile(selectedFile);

                    labelFileName.setText(selectedFile.getName());
                    if (document == null) {
                        labelInfo.setText("NOT VALID");
                        labelInfo.setForeground(Color.RED);
                        buttonLoadData.setEnabled(false);
                    } else {
                        labelInfo.setText("VALID");
                        labelInfo.setForeground(Color.GREEN);
                        buttonLoadData.setEnabled(true);
                    }
                    ImportDialog.this.pack();
                }
               // }
            }
        });
        buttonLoadData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textAreaData.append("# Loading data...\n");

                Month month = loadMonth(document);
                textAreaData.append("# Month Name: " + month.getName() + "\n");
                String day = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
                        .getDateFormatSymbols().getWeekdays()[month.getDayStartsWith() - 1];
                textAreaData.append("# Month Description: begins in:" + day + " days:" + month.getDaysNumber() + "\n");
                textAreaData.append("# Public Free Days: ");
                for (Integer pfd : month.getPublicFreeDays()){
                    textAreaData.append(pfd + ", ");
                }
                textAreaData.append("\n");
                textAreaData.append("# Loading item records...\n");
                textAreaData.append("# " + month.getItems().size() + " items found.\n");
                textAreaData.append("-----------\n");
                for (Item item : month.getItems()){
                    String sign = (item.getIncome()) ? "Income" : "Expense";
                    textAreaData.append("Item: in day:"+item.getDay()+" | "+sign+" | amount="+item.getAmount()
                            +" | type="+item.getType()+"\n");
                }

                scrollPaneData.setVisible(true);
                ImportDialog.this.pack();
                buttonLoadData.setEnabled(false);
                buttonImport.setEnabled(true);
            }
        });
        buttonImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result = true;
                ImportDialog.this.dispose();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result = false;
                ImportDialog.this.dispose();
            }
        });
    }

    private Document tryParseFile(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.setSchema(schemaFactory.newSchema(this.getClass().getResource("/schema/month1.0.xsd")));
            DocumentBuilder builder = factory.newDocumentBuilder();
            factory.getSchema().newValidator().validate(new StreamSource(file));
            return builder.parse(file);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Initializer.LOG.warning("Parsing \"" + file.getName() + "\" finished with error: " + e.getMessage());
            return null;
        }
    }

    private Month loadMonth(Document document){
        Month month = new Month();

        Element root = document.getDocumentElement();

        String name = root.getElementsByTagName("name").item(0).getTextContent();
        month.setName(name);

        String daysNumber = root.getElementsByTagName("dayscount").item(0).getTextContent();
        month.setDaysNumber(Integer.parseInt(daysNumber));

        String startingDay = root.getElementsByTagName("startingday").item(0).getTextContent();
        month.setDayStartsWith(Integer.parseInt(startingDay));

        NodeList items =  root.getElementsByTagName("accounting").item(0).getChildNodes();
        List<Item> itemList = new ArrayList<>();
        for (int i=0;i<items.getLength();i++){
            NamedNodeMap itemAttrs = items.item(i).getAttributes();
            Item item = new Item();
            item.setId(Integer.parseInt(itemAttrs.getNamedItem("id").getTextContent()));
            item.setDay(Integer.parseInt(itemAttrs.getNamedItem("day").getTextContent()));
            item.setIncome(Boolean.parseBoolean(itemAttrs.getNamedItem("income").getTextContent()));
            item.setType(itemAttrs.getNamedItem("type").getTextContent());
            item.setAmount(new BigDecimal(itemAttrs.getNamedItem("amount").getTextContent()));
            //default description
            item.setDescription("");
            itemList.add(item);
        }
        month.getItems().addAll(itemList);
        //compute weekends
        for (int i=1;i<=month.getDaysNumber();i++){
            int dayInWeek = (i + month.getDayStartsWith() - 1) % 7;
            if (dayInWeek == 6 || dayInWeek == 0){
                month.getPublicFreeDays().add(i);
            }
        }

        return month;
    }

    public boolean getResult(){
        return result;
    }

    public Month getImportedMonth(){
        return loadMonth(document);
    }
}
