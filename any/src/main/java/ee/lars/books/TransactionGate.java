package ee.lars.books;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Working effectively with legacy code.
 * Sprout method.
 */
public class TransactionGate {

    private TransactionBundle transactionBundle;

    public void postEntries(List<Entry> entries) {

        for (Iterator<Entry> it = entries.iterator(); it.hasNext(); ) {
            Entry entry = it.next();
            entry.postData();
        }

        this.transactionBundle.getListManager().addAll(entries);
    }

    private class Entry {

        public void postData() {

        }
    }

    private class TransactionBundle {

        private List<Entry> list = new ArrayList<>();

        public List<Entry> getListManager() {
            return list;
        }
    }
}
