package org.kaleta.scheduler.backend.manager.jaxb;

import org.kaleta.scheduler.backend.entity.Settings;
import org.kaleta.scheduler.backend.manager.ManagerException;
import org.kaleta.scheduler.backend.manager.SettingsManager;
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
import java.util.Arrays;

/**
 * Created by Stanislav Kaleta on 24.07.2015.
 */
public class JaxbSettingsManager implements SettingsManager {
    private final String schemaUri;
    private final String settingsFileUri;

    public JaxbSettingsManager() {
        schemaUri = "/schema/settings.xsd";
        settingsFileUri = Initializer.DATA_SOURCE + "settings.xml";
    }

    @Override
    public void createSettings() throws ManagerException {
        // just pass new instance with default settings to update - JAXB is able to create file
        Settings settings = new Settings();
        settings.setFirstUse(Boolean.TRUE);
        settings.setUserName("UNDEFINED");
        settings.setUiSchemeSelected("UNDEFINED");
        settings.setLastMonthSelectedId(-1);
        settings.setLastDaySelected(-1);
        settings.setLanguage("UNDEFINED");
        settings.setCurrency("UNDEFINED");
        updateSettings(settings);
    }

    @Override
    public Settings retrieveSettings() throws ManagerException {
        Settings settings = null;
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(this.getClass().getResource(schemaUri));

            JAXBContext context = JAXBContext.newInstance(org.kaleta.scheduler.backend.manager.jaxb.model.Settings.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema);

            File file = new File(settingsFileUri);
            org.kaleta.scheduler.backend.manager.jaxb.model.Settings model =
                    (org.kaleta.scheduler.backend.manager.jaxb.model.Settings) unmarshaller.unmarshal(file);
            settings = ModelUtil.transformSettingsToData(model);
        } catch (Exception e) {
            throw new ManagerException("Error while retrieving settings: ",e);
        }
        return settings;
    }

    @Override
    public void updateSettings(Settings settings) throws ManagerException {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(this.getClass().getResource(schemaUri));

            JAXBContext context = JAXBContext.newInstance(org.kaleta.scheduler.backend.manager.jaxb.model.Settings.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            org.kaleta.scheduler.backend.manager.jaxb.model.Settings model = ModelUtil.transformSettingsToModel(settings);
            File file = new File(settingsFileUri);

            marshaller.marshal(model,new DefaultHandler());
            marshaller.marshal(model, file);
        } catch (Exception e) {
            throw new ManagerException("Error while updating settings: ",e);
        }
    }

    @Override
    public void deleteSettings() throws ManagerException {
        throw new ManagerException("Method not implemented!");
    }
}
