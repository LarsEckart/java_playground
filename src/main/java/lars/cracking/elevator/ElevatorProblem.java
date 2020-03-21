package lars.cracking.elevator;

import java.util.ArrayDeque;
import java.util.Queue;

public class ElevatorProblem {

  public long solve(int[] A, int[] B, int M, int X, int Y) {
    return this.countElevatorStops(A, B, M, X, Y);
  }

  private long countElevatorStops(
      int[] weight, int[] targetFloor, int maxFloor, int capacity, int weightLimit) {
    Queue<Person> queue = setupQueue(weight, targetFloor);

    Elevator elevatorPassengers = new Elevator();
    long totalStops = 0L;
    while (!queue.isEmpty()) {
      final Person peek = queue.peek();

      if (isWithinWeightLimit(weightLimit, elevatorPassengers, peek)
          && isWithinCapacity(capacity, elevatorPassengers)) {
        personEntersElevator(queue, elevatorPassengers);
      } else {
        totalStops = updateTotalStops(totalStops, elevatorPassengers);
      }
      if (queue.isEmpty()) {
        totalStops = updateTotalStops(totalStops, elevatorPassengers);
      }
    }
    return totalStops;
  }

  private Queue<Person> setupQueue(int[] weight, int[] targetFloor) {
    Queue<Person> queue = new ArrayDeque<>();
    for (int i = 0; i < weight.length; i++) {
      queue.add(new Person(weight[i], targetFloor[i]));
    }
    return queue;
  }

  private boolean isWithinWeightLimit(int maxWeight, Elevator elevatorPassengers, Person peek) {
    return elevatorPassengers.getCurrentWeightSum() + peek.getWeight() <= maxWeight;
  }

  private boolean isWithinCapacity(int capacity, Elevator elevatorPassengers) {
    return elevatorPassengers.getCurrentCapacity() + 1 <= capacity;
  }

  private void personEntersElevator(Queue<Person> queue, Elevator elevatorPassengers) {
    final Person poll = queue.poll();
    elevatorPassengers.add(poll);
  }

  private long updateTotalStops(long totalStops, Elevator elevatorPassengers) {
    totalStops += elevatorPassengers.stream().mapToInt(p -> p.getTargetFloor()).distinct().count();
    totalStops++;
    elevatorPassengers.clear();
    return totalStops;
  }

  private static class Elevator extends ArrayDeque<Person> {

    int getCurrentWeightSum() {
      return this.stream().mapToInt(p -> p.getWeight()).sum();
    }

    int getCurrentCapacity() {
      return this.size();
    }
  }

  private static class Person {

    private final int weight;
    private final int targetFloor;

    Person(int weight, int targetFloor) {
      this.weight = weight;
      this.targetFloor = targetFloor;
    }

    public int getWeight() {
      return this.weight;
    }

    public int getTargetFloor() {
      return this.targetFloor;
    }
  }
}
