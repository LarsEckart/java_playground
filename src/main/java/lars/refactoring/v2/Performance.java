package lars.refactoring.v2;

class Performance {

    private String playID;
    private int audience;

    Performance(String playID, int audience) {
        this.playID = playID;
        this.audience = audience;
    }

    String getPlayID() {
        return playID;
    }

    int getAudience() {
        return audience;
    }
}

