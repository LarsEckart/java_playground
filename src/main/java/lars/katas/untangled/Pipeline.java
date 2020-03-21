package lars.katas.untangled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** https://github.com/tomphp/untangled-conditionals-kata/ */
class Pipeline {
  private final Config config;
  private final Emailer emailer;
  private static final Logger log = LoggerFactory.getLogger(Pipeline.class);

  public Pipeline(Config config, Emailer emailer) {
    this.config = config;
    this.emailer = emailer;
  }

  public void run(Project project) {
    boolean testsPassed;
    boolean deploySuccessful;

    if (project.hasTests()) {
      if ("success".equals(project.runTests())) {
        log.info("Tests passed");
        testsPassed = true;
      } else {
        log.error("Tests failed");
        testsPassed = false;
      }
    } else {
      log.info("No tests");
      testsPassed = true;
    }

    if (testsPassed) {
      if ("success".equals(project.deploy())) {
        log.info("Deployment successful");
        deploySuccessful = true;
      } else {
        log.error("Deployment failed");
        deploySuccessful = false;
      }
    } else {
      deploySuccessful = false;
    }

    if (config.sendEmailSummary()) {
      log.info("Sending email");
      if (testsPassed) {
        if (deploySuccessful) {
          emailer.send("Deployment completed successfully");
        } else {
          emailer.send("Deployment failed");
        }
      } else {
        emailer.send("Tests failed");
      }
    } else {
      log.info("Email disabled");
    }
  }
}
