package lars;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"lars.spielplatz.tc", "lars.spielplatz.ttddyydataproxy"})
public class IntegrationTestSuite {


}
