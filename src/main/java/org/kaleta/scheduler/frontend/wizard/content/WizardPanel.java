package org.kaleta.scheduler.frontend.wizard.content;

/**
 * Created by Stanislav Kaleta on 01.08.2015.
 *
 * Provides basic methods for wizard pages. Every wizard page (panel) should implement this.
 */
public interface WizardPanel {

    /**
     * Returns state of wizard page.
     * @return true if page is fully filled, false otherwise.
     */
    boolean isFilled();

    /**
     * Just shows component's method while using interface type.
     */
    void setVisible(boolean flag);
}
