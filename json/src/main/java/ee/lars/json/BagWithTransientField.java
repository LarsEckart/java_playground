package ee.lars.json;

public class BagWithTransientField {

    private String value1 = "hello";
    private transient String value2 = "world";

    public BagWithTransientField() {
    }

    public String getValue1() {
        return this.value1;
    }

    public String getValue2() {
        return this.value2;
    }
}
