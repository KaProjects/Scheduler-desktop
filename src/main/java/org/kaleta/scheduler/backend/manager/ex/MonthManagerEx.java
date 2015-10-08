package org.kaleta.scheduler.backend.manager.ex;

import org.kaleta.scheduler.common.ServiceFailureException;
import org.kaleta.scheduler.backend.entity.Month;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 17.7.2014
 * To change this template use File | Settings | File Templates.
 */
public interface MonthManagerEx {
    public void createMonth(Month month) throws ServiceFailureException;
    public Month retrieveMonth(String name) throws ServiceFailureException;
    public List<Month> retrieveAllMonths() throws ServiceFailureException;
    public void updateMonth(Month month) throws ServiceFailureException;
    public void deleteMonth(Month month) throws ServiceFailureException;
}
