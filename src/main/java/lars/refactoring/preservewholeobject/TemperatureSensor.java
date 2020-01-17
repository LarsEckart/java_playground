package lars.refactoring.preservewholeobject;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Supplier;

class TemperatureSensor implements Supplier<Integer> {

    private ConcurrentLinkedDeque<Integer> stack;

    public TemperatureSensor(ConcurrentLinkedDeque<Integer> stack) {
        this.stack = stack;
    }

    @Override
    public Integer get() {
        return stack.pop();
    }
}
