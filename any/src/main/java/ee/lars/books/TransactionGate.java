package ee.lars.books;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Working effectively with legacy code.
 * Sprout method.
 * We need to add code to verify that none of the new entries are already in transactionBundle
 * before we post their dates and add them.
 */
public class TransactionGate {

    private TransactionBundle transactionBundle;

    public void postEntries(List<Entry> entries) {

        List<Entry> entriesToAdd = new LinkedList<>();

        for (Iterator<Entry> it = entries.iterator(); it.hasNext(); ) {
            Entry entry = it.next();
            if (!this.transactionBundle.getListManager().contains(entry)) {
                entry.postData();
                entriesToAdd.add(entry);
            }
        }

        this.transactionBundle.getListManager().addAll(entriesToAdd);
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
