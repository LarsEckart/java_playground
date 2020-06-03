package lars;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class MyArchitectureTest {

  @Disabled("investigate another time")
  @Test
  public void some_architecture_rule() {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("lars");

    ArchRule rule =
        classes()
            .that()
            .resideInAPackage("lars..")
            .should()
            .onlyHaveDependentClassesThat()
            .resideOutsideOfPackage("..shaded..");

    rule.check(importedClasses);
  }
}
