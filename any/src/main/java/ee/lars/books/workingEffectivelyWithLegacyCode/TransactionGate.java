package ee.lars.books.workingEffectivelyWithLegacyCode;

import java.util.ArrayList;
import java.util.Iterator;
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
        List<Entry> entriesToAdd = uniqueEntries(entries);

        for (Iterator<Entry> it = entriesToAdd.iterator(); it.hasNext(); ) {
            Entry entry = it.next();
            entry.postData();
        }

        this.transactionBundle.getListManager().addAll(entriesToAdd);
    }

    List<Entry> uniqueEntries(List entries) {
        List<Entry> result = new ArrayList<>();
        for (Iterator it = entries.iterator(); it.hasNext(); ) {
            Entry entry = (Entry) it.next();
            if (!this.transactionBundle.getListManager().contains(entry)) {
                result.add(entry);
            }
        }
        return result;
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
