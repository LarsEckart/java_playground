package lars.refactoring.documentload;

class DataSource {

    public String getAlbumList(String query) {
        return "{\n"
                + "    \"albums\": [\n"
                + "      {\n"
                + "        \"title\": \"Isla\",\n"
                + "        \"artist\": \"Portico Quartet\",\n"
                + "        \"tracks\": [\n"
                + "          {\"title\": \"Paper Scissors Stone\", \"lengthInSeconds\": 327},\n"
                + "          {\"title\": \"The Visitor\", \"lengthInSeconds\": 330},\n"
                + "          {\"title\": \"Dawn Patrol\", \"lengthInSeconds\": 359},\n"
                + "          {\"title\": \"Line\", \"lengthInSeconds\": 449},\n"
                + "          {\"title\": \"Life Mask (Interlude)\", \"lengthInSeconds\": 75},\n"
                + "          {\"title\": \"Clipper\", \"lengthInSeconds\": 392},\n"
                + "          {\"title\": \"Life Mask\", \"lengthInSeconds\": 436},\n"
                + "          {\"title\": \"Isla\", \"lengthInSeconds\": 310},\n"
                + "          {\"title\": \"Shed Song (Improv No 1)\", \"lengthInSeconds\": 503},\n"
                + "          {\"title\": \"Su-Bo's Mental Meltdown\", \"lengthInSeconds\": 347}\n"
                + "        ]\n"
                + "      },\n"
                + "      {\n"
                + "        \"title\": \"Horizon\",\n"
                + "        \"artist\": \"Eyot\",\n"
                + "        \"tracks\": [\n"
                + "          {\"title\": \"Far Afield\", \"lengthInSeconds\": 423},\n"
                + "          {\"title\": \"Stone upon stone upon stone\", \"lengthInSeconds\": 479},\n"
                + "          {\"title\": \"If I could say what I want to\", \"lengthInSeconds\": 167},\n"
                + "          {\"title\": \"All I want to say\", \"lengthInSeconds\": 337},\n"
                + "          {\"title\": \"Surge\", \"lengthInSeconds\": 620},\n"
                + "          {\"title\": \"3 Months later\", \"lengthInSeconds\": 516},\n"
                + "          {\"title\": \"Horizon\", \"lengthInSeconds\": 616},\n"
                + "          {\"title\": \"Whale song\", \"lengthInSeconds\": 344},\n"
                + "          {\"title\": \"It's time to go home\", \"lengthInSeconds\": 539}\n"
                + "        ]\n"
                + "      }\n"
                + "  \n"
                + "    ]\n"
                + "  }";
    }
}
