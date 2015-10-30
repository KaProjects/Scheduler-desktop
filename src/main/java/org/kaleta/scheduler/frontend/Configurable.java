package org.kaleta.scheduler.frontend;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public interface Configurable {

    /**
     * TODO documentation
     * @param configuration
     */
    public void setConfiguration(Configuration configuration);

    /**
     * TODO documentation
     * @return
     */
    public Configuration getConfiguration();
}
