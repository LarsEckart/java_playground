package ee.lars.spielplatz.javaspecialists.issue175;

import java.io.Serializable;

public class MySerializable extends NotSerializable implements Serializable {

    public MySerializable() {
        System.out.println("MySerializable constructor called");
    }
}
