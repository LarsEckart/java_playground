package lars.stackoverflow;

public class BinarySearch<E extends Comparable<E>> {

    private int lower;
    private int upper;
    private int returnValue;

    private int lastIndex;

    public int search(E[] a, E key, int lower, int upper) {
        this.lower = lower;
        this.upper = upper;

        if (upper > a.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int ret = binarySearch(a, key, lower, upper);

        return returnValue;
    }

    /**
     * @param a Array, der durchsucht werden soll.
     * @param key Element, nach dem gesucht wird.
     * @param lower untere Grenze des zu durchsuchenden Bereiches.
     * @param upper obere Grenze des zu durchsuchenden Bereiches.
     * @return index der Stelle wo daa Elemnt ist.
     */
    private int binarySearch(E[] a, E key, int lower, int upper) {

        if (lower < upper) {

            int middle = (lower / 2) + (upper / 2);
            int tempInt = a[middle].compareTo(key);

            if (tempInt > 0) {

                return binarySearch(a, key, lower, middle - 1);
            }
            if (tempInt < 0) {
                return binarySearch(a, key, middle + 1, upper);
            }

            this.returnValue = middle;
            if (key.equals(a[middle]) && !key.equals(a[middle - 1])) {
                return middle;
            } else {
                return binarySearch(a, key, lower, middle - 1);
            }
        }

        if (key.equals(a[lower])) {
            this.returnValue = lower;

            int temp = checkForDuplicates(a, key, 0, upper - 1);

            return returnValue;
        }

        int temp = key.compareTo(a[this.upper]);
        if (temp > 0) {
            this.returnValue = (this.upper + 1);
            return (this.upper + 1);
        }
        temp = key.compareTo(a[this.lower]);
        if (temp < 0) {
            this.returnValue = this.lower - 1;
            return (this.lower - 1);
        } else {
            this.returnValue = upper + 1;
        }

        return returnValue;
    }

    private int checkForDuplicates(E[] a, E key, int lower, int upper) {

        if (lower < upper) {

            int middle = (lower / 2) + (upper / 2);
            lastIndex = middle;
            int tempInt = a[middle].compareTo(key);

            if (tempInt < 0) {
                return checkForDuplicates(a, key, middle + 1, upper);
            }

            this.returnValue = middle;
            if (key.equals(a[lower])) {
                this.returnValue = lower;
                checkForDuplicates(a, key, 0, middle - 1);
                return returnValue;
            }

            return -1;
        }

        if (key.equals(a[lower])) {
            this.returnValue = lower;
            return returnValue;
        }

        return -1;
    }
}
