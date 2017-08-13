package ee.lars.books;

import java.util.List;

public class StepNotifyingController extends ToolControllerDecorator {

    private List notifees;

    public StepNotifyingController(ToolController toolController, List notifees) {
        super(toolController);
        this.notifees = notifees;
    }

    @Override
    public void step() {
        System.out.println("Notifying all notifees");
        this.toolController.step();
    }
}
