package lars;

import org.junit.jupiter.api.Test;

class EfficientCounterTest {

    @Test
    void performance() {
        long startTime;
        long endTime;
        long duration;

        // naive approach
        startTime = System.nanoTime();
        new EfficientCounter().naiveApproach();

        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Naive Approach :  " + duration);

        // better approach
        startTime = System.nanoTime();
        new EfficientCounter().improvedApproach();

        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Better Approach:  " + duration);

        // efficient approach
        startTime = System.nanoTime();

        new EfficientCounter().efficientApproach();

        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Efficient Approach:  " + duration);

        // even moreefficient approach
        startTime = System.nanoTime();

        new EfficientCounter().evenMoreEfficientApproach();

        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Even more efficient Approach:  " + duration);
    }
}
