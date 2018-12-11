package lars.refactoring.v1;

class Movie {

    static final int CHILDRENS = 2;
    static final int REGULAR = 0;
    static final int NEW_RELEASE = 1;

    private String _title;
    private int _priceCode;

    public Movie(String title, int priceCode) {
        _title = title;
        _priceCode = priceCode;
    }

    int getPriceCode() {
        return _priceCode;
    }

    String getTitle() {
        return _title;
    }
}
