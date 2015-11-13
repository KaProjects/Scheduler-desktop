package org.kaleta.scheduler.backend.manager.ex;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.backend.entity.Task;
import org.kaleta.scheduler.ex.common.ServiceFailureException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 17.7.2014
 * To change this template use File | Settings | File Templates.
 */
public class MonthManagerExImpl implements MonthManagerEx {
    private String databaseSource;

    public MonthManagerExImpl(String databaseSource) {
        this.databaseSource = databaseSource;
    }

    @Override
    public void createMonth(Month month) throws ServiceFailureException {
        try {
            File dir = new File(databaseSource);
            for (File f : dir.listFiles()){
                if (f.getName().equals(month.getName()+".xml")){
                    throw new IllegalArgumentException("month with name \"" + month.getName() + "\" already exists!");
                }
            }

            DocumentBuilderFactory bFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = bFactory.newDocumentBuilder();
            Document newMonthDoc = builder.newDocument();

            Element rootE = newMonthDoc.createElement("month");
            newMonthDoc.appendChild(rootE);
            Element specificationE = newMonthDoc.createElement("specification");
            rootE.appendChild(specificationE);
            Element nameE = newMonthDoc.createElement("name");
            nameE.appendChild(newMonthDoc.createTextNode(month.getName()));
            specificationE.appendChild(nameE);
            Element daysCountE = newMonthDoc.createElement("dayscount");
            daysCountE.appendChild(newMonthDoc.createTextNode(String.valueOf(month.getDaysNumber())));
            specificationE.appendChild(daysCountE);
            Element startingDayE = newMonthDoc.createElement("startingday");
            startingDayE.appendChild(newMonthDoc.createTextNode(String.valueOf(month.getDayStartsWith())));
            specificationE.appendChild(startingDayE);
            Element tasksE = newMonthDoc.createElement("tasks");
            rootE.appendChild(tasksE);
            Element accountingE = newMonthDoc.createElement("accounting");
            rootE.appendChild(accountingE);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(newMonthDoc);
            String newFilePath = databaseSource + month.getName() + ".xml";
            StreamResult result = new StreamResult(newFilePath);
            transformer.transform(source, result);
            System.out.println("CREATE:  " + month.getName() + ".xml");  /*TODO log*/
        } catch (TransformerConfigurationException ex){
            /*TODO log*/
            throw new ServiceFailureException("error creating new month",ex);
        } catch (TransformerException ex){
            /*TODO log*/
            throw new ServiceFailureException("error creating new month",ex);
        } catch (ParserConfigurationException ex){
            /*TODO log*/
            throw new ServiceFailureException("error creating new month", ex);
        }
    }

    @Override
    public Month retrieveMonth(String monthName) throws ServiceFailureException {
        try {
            DocumentBuilderFactory bFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = bFactory.newDocumentBuilder();

            Month month = new Month();
            for(File file : new File(databaseSource).listFiles()){
                Document doc = builder.parse(file);
                String name = doc.getDocumentElement().getElementsByTagName("name").item(0).getTextContent();
                if (name.equals(monthName)){
                    month.setName(name);
                    month.setDaysNumber(Integer.parseInt(doc.getDocumentElement().getElementsByTagName("dayscount").item(0).getTextContent()));
                    month.setDayStartsWith(Integer.parseInt(doc.getDocumentElement().getElementsByTagName("startingday").item(0).getTextContent()));
                    NodeList items =  doc.getDocumentElement().getElementsByTagName("accounting").item(0).getChildNodes();
                    List<Item> itemList = new ArrayList<Item>();
                    for (int i=0;i<items.getLength();i++){
                        NamedNodeMap itemAttrs = items.item(i).getAttributes();
                        Item item = new Item();
                        item.setId(Integer.parseInt(itemAttrs.getNamedItem("id").getTextContent()));
                        item.setDay(Integer.parseInt(itemAttrs.getNamedItem("day").getTextContent()));
                        item.setIncome(Boolean.parseBoolean(itemAttrs.getNamedItem("income").getTextContent()));
                        item.setType(itemAttrs.getNamedItem("type").getTextContent());
                        item.setAmount(new BigDecimal(itemAttrs.getNamedItem("amount").getTextContent()));
                        itemList.add(item);
                    }
                    month.getItems().addAll(itemList);
                    List<Task> taskList = new ArrayList<Task>();
                    NodeList tasks =  doc.getDocumentElement().getElementsByTagName("tasks").item(0).getChildNodes();
                    for (int i=0;i<tasks.getLength();i++){
                        NamedNodeMap taskAttrs = tasks.item(i).getAttributes();
                        Task task = new Task();
                        /*TODO task*/
                        taskList.add(task);
                    }
                    month.getTasks().addAll(taskList);
                }
            }
            return month;
        } catch (ParserConfigurationException ex) {
            /*TODO log*/
            throw new ServiceFailureException("error retrieving month",ex);
        } catch (SAXException ex){
           /*TODO log*/
            throw new ServiceFailureException("error retrieving month",ex);
        } catch (IOException ex){
            /*TODO log*/
            throw new ServiceFailureException("error retrieving month",ex);
        } catch (NullPointerException ex){
            /*TODO log*/
            throw new ServiceFailureException("error retrieving month",ex);
        }
    }

    @Override
    public List<Month> retrieveAllMonths() throws ServiceFailureException {
        try {
            DocumentBuilderFactory bFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = bFactory.newDocumentBuilder();

            List<Month> output = new ArrayList<Month>();
            for(File file : new File(databaseSource).listFiles()){
                Document doc = builder.parse(file);

                Month month = new Month();
                month.setName(doc.getDocumentElement().getElementsByTagName("name").item(0).getTextContent());
                month.setDaysNumber(Integer.parseInt(doc.getDocumentElement().getElementsByTagName("dayscount").item(0).getTextContent()));
                month.setDayStartsWith(Integer.parseInt(doc.getDocumentElement().getElementsByTagName("startingday").item(0).getTextContent()));
                NodeList items =  doc.getDocumentElement().getElementsByTagName("accounting").item(0).getChildNodes();
                List<Item> itemList = new ArrayList<Item>();
                for (int i=0;i<items.getLength();i++){
                    NamedNodeMap itemAttrs = items.item(i).getAttributes();
                    Item item = new Item();
                    item.setId(Integer.parseInt(itemAttrs.getNamedItem("id").getTextContent()));
                    item.setDay(Integer.parseInt(itemAttrs.getNamedItem("day").getTextContent()));
                    item.setIncome(Boolean.parseBoolean(itemAttrs.getNamedItem("income").getTextContent()));
                    item.setType(itemAttrs.getNamedItem("type").getTextContent());
                    item.setAmount(new BigDecimal(itemAttrs.getNamedItem("amount").getTextContent()));
                    itemList.add(item);
                }
                month.getItems().addAll(itemList);
                List<Task> taskList = new ArrayList<Task>();
                NodeList tasks =  doc.getDocumentElement().getElementsByTagName("tasks").item(0).getChildNodes();
                for (int i=0;i<tasks.getLength();i++){
                    NamedNodeMap taskAttrs = tasks.item(i).getAttributes();
                    Task task = new Task();
                        /*TODO task*/
                    taskList.add(task);
                }
                month.getTasks().addAll(taskList);
                output.add(month);
            }
            return output;
        } catch (ParserConfigurationException ex) {
            /*TODO log*/
            throw new ServiceFailureException("error retrieving month",ex);
        } catch (SAXException ex){
           /*TODO log*/
            throw new ServiceFailureException("error retrieving month",ex);
        } catch (IOException ex){
            /*TODO log*/
            throw new ServiceFailureException("error retrieving month",ex);
        }
    }

    @Override
    public void updateMonth(Month month) throws ServiceFailureException {
        try {
            DocumentBuilderFactory bFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = bFactory.newDocumentBuilder();
            Document monthDoc = null;
            File dir = new File(databaseSource);
            boolean fileCondition = true;

            for (File f : dir.listFiles()){
                if (f.getName().equals(month.getName()+".xml")){
                    monthDoc = builder.parse(f);
                    fileCondition = false;
                }
            }
            if (fileCondition) {throw new IllegalArgumentException("month with name \"" + month.getName() + "\" not exists!");}

            Element rootE = monthDoc.getDocumentElement();

            Element accountingE = (Element)rootE.getElementsByTagName("accounting").item(0);
            NodeList accountingNodes = accountingE.getChildNodes();
            List<String> ids = new ArrayList<String>();
            for (int i=0;i<accountingNodes.getLength();i++){
                NamedNodeMap itemAttrs = accountingNodes.item(i).getAttributes();
                ids.add(itemAttrs.getNamedItem("id").getTextContent());
            }
            for (Item item : month.getItems()){
                boolean idCondition = true;
                for (String idFromDoc : ids){
                    if(idFromDoc.equals(String.valueOf(item.getId()))){

                        idCondition = false;
                    }
                }
                if (idCondition) {
                    Element itemE = monthDoc.createElement("item");
                    Attr idA = monthDoc.createAttribute("id");
                    idA.setValue(String.valueOf(item.getId()));
                    itemE.setAttributeNode(idA);
                    Attr dayA = monthDoc.createAttribute("day");
                    dayA.setValue(String.valueOf(item.getDay()));
                    itemE.setAttributeNode(dayA);
                    Attr incomeA = monthDoc.createAttribute("income");
                    incomeA.setValue(String.valueOf(item.getIncome()));
                    itemE.setAttributeNode(incomeA);
                    Attr typeA = monthDoc.createAttribute("type");
                    typeA.setValue(item.getType());
                    itemE.setAttributeNode(typeA);
                    Attr amountA = monthDoc.createAttribute("amount");
                    amountA.setValue(String.valueOf(item.getAmount()));
                    itemE.setAttributeNode(amountA);
                    accountingE.appendChild(itemE);
                } else {
                    /*TODO pripadny edit existujucej polozky */
                }
            }


            /*TODO tasks*/

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(monthDoc);
            String filePath = databaseSource + month.getName() + ".xml";
            StreamResult result = new StreamResult(filePath);
            transformer.transform(source, result);
            System.out.println("UPDATE: " + month.getName() + ".xml"); /*TODO log*/
        } catch (TransformerConfigurationException ex){
            /*TODO log*/
            throw new ServiceFailureException("error creating new month",ex);
        } catch (TransformerException ex){
            /*TODO log*/
            throw new ServiceFailureException("error creating new month",ex);
        } catch (ParserConfigurationException ex){
            /*TODO log*/
            throw new ServiceFailureException("error creating new month", ex);
        } catch (SAXException ex) {
            /*TODO log*/
            throw new ServiceFailureException("error creating new month", ex);
        } catch (IOException ex) {
            /*TODO log*/
            throw new ServiceFailureException("error creating new month", ex);
        }
    }

    @Override
    public void deleteMonth(Month month) throws ServiceFailureException {
        /*TODO*/
    }
}
