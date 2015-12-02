package org.kaleta.scheduler.ex.mgr;

import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.ex.common.ServiceFailureException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 17.7.2014
 * To change this template use File | Settings | File Templates.
 */
public interface MonthManagerEx {
    void createMonth(Month month) throws ServiceFailureException;
    Month retrieveMonth(String name) throws ServiceFailureException;
    List<Month> retrieveAllMonths() throws ServiceFailureException;
    void updateMonth(Month month) throws ServiceFailureException;
    void deleteMonth(Month month) throws ServiceFailureException;
}
