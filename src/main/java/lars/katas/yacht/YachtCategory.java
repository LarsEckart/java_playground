package lars.katas.yacht;

import org.eclipse.collections.api.bag.primitive.IntBag;
import org.eclipse.collections.api.tuple.primitive.IntIntPair;

enum YachtCategory {
  ONES {
    @Override
    public int score(IntBag bag) {
      return bag.occurrencesOf(1);
    }
  },
  TWOS {
    @Override
    public int score(IntBag bag) {
      return bag.occurrencesOf(2) * 2;
    }
  },
  FOURS {
    @Override
    public int score(IntBag bag) {
      return bag.occurrencesOf(4) * 4;
    }
  },
  THREES {
    @Override
    public int score(IntBag bag) {
      return bag.occurrencesOf(3) * 3;
    }
  },
  FIVES {
    @Override
    public int score(IntBag bag) {
      return 0;
    }
  },
  SIXES {
    @Override
    public int score(IntBag bag) {
      return bag.occurrencesOf(6) * 6;
    }
  },
  FULL_HOUSE {
    @Override
    public int score(IntBag bag) {
      IntIntPair three = bag.topOccurrences(1).getAny();
      IntIntPair two = bag.bottomOccurrences(1).getAny();
      if (three.getTwo() == 3 && two.getTwo() == 2) {
        return three.getOne() * 3 + two.getOne() * 2;
      }
      return 0;
    }
  },
  FOUR_OF_A_KIND {
    @Override
    public int score(IntBag bag) {
      if (bag.topOccurrences(1).getAny().getTwo() == 4
          || bag.topOccurrences(1).getAny().getTwo() == 5) {
        int one = bag.topOccurrences(1).getAny().getOne();
        return one * 4;
      }
      return 0;
    }
  },
  LITTLE_STRAIGHT {
    @Override
    public int score(IntBag bag) {
      if (bag.containsAll(1, 2, 3, 4, 5)) {
        return 30;
      }
      return 0;
    }
  },
  BIG_STRAIGHT {
    @Override
    public int score(IntBag bag) {
      if (bag.containsAll(6, 2, 3, 4, 5)) {
        return 30;
      }
      return 0;
    }
  },
  CHOICE {
    @Override
    public int score(IntBag bag) {
      return Math.toIntExact(bag.sum());
    }
  },
  YACHT {
    @Override
    public int score(IntBag bag) {
      if (bag.sizeDistinct() == 1) {
        return 50;
      }
      return 0;
    }
  };

  public abstract int score(IntBag bag);
}
