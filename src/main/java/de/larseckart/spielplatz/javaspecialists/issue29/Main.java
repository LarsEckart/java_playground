package de.larseckart.spielplatz.javaspecialists.issue29;

public class Main {

    public static void main(String[] args) {
        MemoryTestBench testBench = new MemoryTestBench();

        testBench.showMemoryUsage(new BasicObjectFactory());
        testBench.showMemoryUsage(() -> new BasicObjectFactory());

        testBench.showMemoryUsage(new ByteFactory());
        testBench.showMemoryUsage(new ThreeByteFactory());

        testBench.showMemoryUsage(new SixtyFourBooleanFactory());
        testBench.showMemoryUsage(new BooleanArrayFactory());
        testBench.showMemoryUsage(new PrimitiveByteArrayFactory());

        testBench.showMemoryUsage(new StringFactory());

    }
}
