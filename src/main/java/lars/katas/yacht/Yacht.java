package lars.katas.yacht;

import org.eclipse.collections.api.bag.primitive.IntBag;
import org.eclipse.collections.impl.bag.mutable.primitive.IntHashBag;

public class Yacht {

    private final YachtCategory category;
    private final IntBag bag;

    public Yacht(int[] scores, YachtCategory category) {
        this.category = category;
        this.bag = IntHashBag.newBagWith(scores);
    }

    public int score() {
        return category.score(bag);
    }
}
