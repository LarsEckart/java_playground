package ee.lars.books;

import java.util.ArrayList;

public class ToolControlerMain {

    public static void main(String[] args) {
        ToolController toolController = new StepNotifyingController(new ToolController(), new ArrayList());

        toolController.step();
    }

}
