package lars.spielplatz.javaspecialists.issue175;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeserializeTest {

  public static void main(String[] args) throws Exception {

    MySerializable ms = new MySerializable();

    System.out.println("writing ms");
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(ms);
    oos.close();
    byte[] objectInBinaryForm = baos.toByteArray();

    System.out.println("reading ms2");
    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(objectInBinaryForm));

    MySerializable ms2 = (MySerializable) ois.readObject();
    System.out.println("ms == ms2 " + (ms == ms2));
    System.out.println("ms = " + ms);
    System.out.println("ms2 = " + ms2);
  }
}
