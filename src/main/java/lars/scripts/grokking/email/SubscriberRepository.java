package lars.scripts.grokking.email;

import java.util.List;

public interface SubscriberRepository {

  List<Subscriber> fetchSubscribersFromDB();
}
