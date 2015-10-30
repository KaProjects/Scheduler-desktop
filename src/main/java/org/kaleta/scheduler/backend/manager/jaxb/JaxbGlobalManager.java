package org.kaleta.scheduler.backend.manager.jaxb;


import org.kaleta.scheduler.backend.entity.Global;
import org.kaleta.scheduler.backend.manager.GlobalManager;
import org.kaleta.scheduler.backend.manager.ManagerException;
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
 * Date: 24.7.2015
 */
public class JaxbGlobalManager implements GlobalManager {
    private final String schemaUri;
    private final String globalFileUri;

    public JaxbGlobalManager() {
        schemaUri = "/schema/global.xsd";
        globalFileUri = Initializer.DATA_SOURCE + "global.xml";
    }

    @Override
    public void createGlobal() throws ManagerException {
        // just pass new instance to update - JAXB is able to create file
        updateGlobal(new Global());

    }

    @Override
    public Global retrieveGlobal() throws ManagerException {
        Global global = null;
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(this.getClass().getResource(schemaUri));

            JAXBContext context = JAXBContext.newInstance(org.kaleta.scheduler.backend.manager.jaxb.model.Global.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema);

            File file = new File(globalFileUri);
            org.kaleta.scheduler.backend.manager.jaxb.model.Global model =
                    (org.kaleta.scheduler.backend.manager.jaxb.model.Global) unmarshaller.unmarshal(file);
            global = ModelUtil.transformGlobalToData(model);
        } catch (Exception e) {
            throw new ManagerException("Error while retrieving global data: ",e);
        }
        return global;
    }

    @Override
    public void updateGlobal(Global global) throws ManagerException {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(this.getClass().getResource(schemaUri));

            JAXBContext context = JAXBContext.newInstance(org.kaleta.scheduler.backend.manager.jaxb.model.Global.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            org.kaleta.scheduler.backend.manager.jaxb.model.Global model = ModelUtil.transformGlobalToModel(global);
            File file = new File(globalFileUri);

            marshaller.marshal(model,new DefaultHandler());
            marshaller.marshal(model, file);
        } catch (Exception e) {
            throw new ManagerException("Error while updating global data: ",e);
        }
    }

    @Override
    public void deleteGlobal() throws ManagerException {
        throw new ManagerException("Method not implemented!");
    }
}
