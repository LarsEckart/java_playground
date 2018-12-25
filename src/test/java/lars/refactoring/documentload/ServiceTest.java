package lars.refactoring.documentload;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceTest {

    @Test
    void scenario_expectedbehaviour() {
        Service service = new Service(new DataSource());

        String result = service.tuesdayMusic("any");

        assertThat(result).isEqualTo("{\"albums\":[{\"artist\":\"Portico Quartet\",\"title\":\"Isla\",\"tracks\":[{\"title\":\"Paper Scissors Stone\",\"lengthInSeconds\":327},{\"title\":\"The Visitor\",\"lengthInSeconds\":330},{\"title\":\"Dawn Patrol\",\"lengthInSeconds\":359},{\"title\":\"Line\",\"lengthInSeconds\":449},{\"title\":\"Life Mask (Interlude)\",\"lengthInSeconds\":75},{\"title\":\"Clipper\",\"lengthInSeconds\":392},{\"title\":\"Life Mask\",\"lengthInSeconds\":436},{\"title\":\"Isla\",\"lengthInSeconds\":310},{\"title\":\"Shed Song (Improv No 1)\",\"lengthInSeconds\":503},{\"title\":\"Su-Bo's Mental Meltdown\",\"lengthInSeconds\":347}]},{\"artist\":\"Eyot\",\"title\":\"Horizon\",\"tracks\":[{\"title\":\"Far Afield\",\"lengthInSeconds\":423},{\"title\":\"Stone upon stone upon stone\",\"lengthInSeconds\":479},{\"title\":\"If I could say what I want to\",\"lengthInSeconds\":167},{\"title\":\"All I want to say\",\"lengthInSeconds\":337},{\"title\":\"Surge\",\"lengthInSeconds\":620},{\"title\":\"3 Months later\",\"lengthInSeconds\":516},{\"title\":\"Horizon\",\"lengthInSeconds\":616},{\"title\":\"Whale song\",\"lengthInSeconds\":344},{\"title\":\"It's time to go home\",\"lengthInSeconds\":539}]}]}");
    }
}
