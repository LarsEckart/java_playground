package lars.refactoring.documentload;

class Assortment {

    private String json;

    public static Assortment fromJson(String json) {
        Assortment result = new Assortment();
        result.json = json;
        return result;
    }

    public String toJson() {
        return json;
    }
}
