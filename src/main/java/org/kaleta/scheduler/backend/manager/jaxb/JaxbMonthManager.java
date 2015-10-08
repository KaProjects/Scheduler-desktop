package org.kaleta.scheduler.backend.manager.jaxb;

import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.backend.manager.ManagerException;
import org.kaleta.scheduler.backend.manager.MonthManager;
import org.kaleta.scheduler.backend.manager.jaxb.model.ModelUtil;
import org.kaleta.scheduler.frontend.Initializer;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * Author: Stanislav Kaleta
 * Date: 23.7.2015
 */
public class JaxbMonthManager implements MonthManager {
    private final String schemaUri;
    private final String databaseUri;

    public JaxbMonthManager() {
        schemaUri = "/schema/month.xsd";
        databaseUri = Initializer.DATA_SOURCE + "months-database/";
    }

    @Override
    public void createMonth(Month month) throws ManagerException {
        // just pass to update - JAXB is able to create file
        updateMonth(month);
    }

    @Override
    public Month retrieveMonth(Integer id) throws ManagerException {
        Month month = null;
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(this.getClass().getResource(schemaUri));

            JAXBContext context = JAXBContext.newInstance(org.kaleta.scheduler.backend.manager.jaxb.model.Month.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema);

            File file = new File(databaseUri + "m" + id +".xml");
            org.kaleta.scheduler.backend.manager.jaxb.model.Month model =
                    (org.kaleta.scheduler.backend.manager.jaxb.model.Month) unmarshaller.unmarshal(file);
            month = ModelUtil.transformMonthToData(model);
        } catch (Exception e) {
            throw new ManagerException(e);
        }
        return month;
    }

    @Override
    public void updateMonth(Month month) throws ManagerException {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(this.getClass().getResource(schemaUri));

            JAXBContext context = JAXBContext.newInstance(org.kaleta.scheduler.backend.manager.jaxb.model.Month.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            org.kaleta.scheduler.backend.manager.jaxb.model.Month model = ModelUtil.transformMonthToModel(month);
            File file = new File(databaseUri + "m" + month.getId() +".xml");

            marshaller.marshal(model,new DefaultHandler());
            marshaller.marshal(model, file);
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public void deleteMonth(Month month) throws ManagerException {
        throw new ManagerException("Method not implemented!");
    }
}
