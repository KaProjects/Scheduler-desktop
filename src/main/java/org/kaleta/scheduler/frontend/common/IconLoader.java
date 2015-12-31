package org.kaleta.scheduler.frontend.common;

import org.kaleta.scheduler.frontend.Initializer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Stanislav Kaleta on 27.07.2015.
 */
public class IconLoader {
    public static final int ERROR_ICON = 1;

    /**
     * Returns icon according to specified constant.
     * @param iconConstant specific constant
     */
    public static BufferedImage getIcon(int iconConstant){
        switch (iconConstant){
            case ERROR_ICON:
                return getIcon("error_icon.png");

            default:
                throw new IllegalArgumentException("Illegal icon constant!");
        }
    }

    private static BufferedImage getIcon(String fileName){
        BufferedImage defaultIcon;
        BufferedImage icon;

        try {
            defaultIcon = ImageIO.read(IconLoader.class.getResource("/icons/no_icon.png"));
        } catch (IOException e) {
            Initializer.LOG.warning("System cant load default icon!\n" + e.getMessage());
            defaultIcon = null;
        }

        try {
            URL iconUrl = IconLoader.class.getResource("/icons/" + fileName);
            if (iconUrl == null){
                Initializer.LOG.warning("System cant find icon \n" + fileName + "\". URL is null!");
                icon = defaultIcon;
            } else {
                icon = ImageIO.read(iconUrl);
            }
        } catch (IOException e) {
            Initializer.LOG.warning("System cant find icon \n" + fileName + "\"\n" + e.getMessage());
            icon = defaultIcon;
        }

        return icon;
    }
}
