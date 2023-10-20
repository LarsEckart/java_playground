package lars;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Integration Tests")
@SelectPackages({"lars.spielplatz.tc", "lars.spielplatz.ttddyydataproxy"})
// @IncludeClassNamePatterns(".*Tests")
public class IntegrationTestSuite {}
