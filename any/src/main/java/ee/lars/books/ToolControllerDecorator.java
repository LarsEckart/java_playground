package ee.lars.books;

public class ToolControllerDecorator extends ToolController {

    protected ToolController toolController;

    public ToolControllerDecorator(ToolController toolController) {
        this.toolController = toolController;
    }

    @Override
    public void raise() {
        this.toolController.raise();
    }

    @Override
    public void lower() {
        this.toolController.lower();
    }

    @Override
    public void step() {
        this.toolController.step();
    }

    @Override
    public void on() {
        this.toolController.on();
    }

    @Override
    public void off() {
        this.toolController.off();
    }
}
