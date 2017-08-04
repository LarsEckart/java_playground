package ee.lars.alerts;

import ee.lars.springmvc.spittr.Spittle;

public interface AlertService {

    void sendSpittleAlert(Spittle spittle);

}
