package lars.scripts.grokking.emailv2;

import java.util.List;

public interface SubscriberRepository {

  List<Subscriber> fetchSubscribersFromDB();
}
