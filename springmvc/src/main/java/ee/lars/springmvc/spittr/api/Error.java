package ee.lars.springmvc.spittr.api;

public class Error {

    private final int code;
    private final String message;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}