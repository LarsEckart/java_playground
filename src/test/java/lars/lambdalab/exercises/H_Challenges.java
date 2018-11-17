package lars.lambdalab.exercises;

import org.junit.Ignore;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.RandomAccess;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class H_Challenges {

    /**
     * Denormalize this map. The input is a map whose keys are the number of legs of an animal
     * and whose values are lists of names of animals. Run through the map and generate a
     * "denormalized" list of strings describing the animal, with the animal's name separated
     * by a colon from the number of legs it has. The ordering in the output list is not
     * considered significant.
     *
     * Input is Map<Integer, List<String>>:
     *   { 4=["ibex", "hedgehog", "wombat"],
     *     6=["ant", "beetle", "cricket"],
     *     ...
     *   }
     *
     * Output should be a List<String>:
     *   [ "ibex:4",
     *     "hedgehog:4",
     *     "wombat:4",
     *     "ant:6",
     *     "beetle:6",
     *     "cricket:6",
     *     ...
     *   ]
     */
    @Test @Ignore
    public void h1_denormalizeMap() {
        Map<Integer, List<String>> input = new HashMap<>();
        input.put(4, Arrays.asList("ibex", "hedgehog", "wombat"));
        input.put(6, Arrays.asList("ant", "beetle", "cricket"));
        input.put(8, Arrays.asList("octopus", "spider", "squid"));
        input.put(10, Arrays.asList("crab", "lobster", "scorpion"));
        input.put(750, Arrays.asList("millipede"));

        List<String> result = null; // TODO

        assertEquals(13, result.size());
        assertTrue(result.contains("ibex:4"));
        assertTrue(result.contains("hedgehog:4"));
        assertTrue(result.contains("wombat:4"));
        assertTrue(result.contains("ant:6"));
        assertTrue(result.contains("beetle:6"));
        assertTrue(result.contains("cricket:6"));
        assertTrue(result.contains("octopus:8"));
        assertTrue(result.contains("spider:8"));
        assertTrue(result.contains("squid:8"));
        assertTrue(result.contains("crab:10"));
        assertTrue(result.contains("lobster:10"));
        assertTrue(result.contains("scorpion:10"));
        assertTrue(result.contains("millipede:750"));
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // There are several ways to approach this. You could use a stream of map keys,
    // a stream of map entries, or nested forEach() methods.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // If you use streams, consider using Stream.flatMap().
    // </editor-fold>


    /**
     * Invert a "multi-map". (From an idea by Paul Sandoz)
     *
     * Given a Map<X, Set<Y>>, convert it to Map<Y, Set<X>>.
     * Each set member of the input map's values becomes a key in
     * the result map. Each key in the input map becomes a set member
     * of the values of the result map. In the input map, an item
     * may appear in the value set of multiple keys. In the result
     * map, that item will be a key, and its value set will be
     * its corresopnding keys from the input map.
     *
     * In this case the input is Map<String, Set<Integer>>
     * and the result is Map<Integer, Set<String>>.
     *
     * For example, if the input map is
     *     {p=[10, 20], q=[20, 30]}
     * then the result map should be
     *     {10=[p], 20=[p, q], 30=[q]}
     * irrespective of ordering. Note that the Integer 20 appears
     * in the value sets for both p and q in the input map. Therefore,
     * in the result map, there should be a mapping with 20 as the key
     * and p and q as its value set.
     *
     * It is possible to accomplish this task using a single stream
     * pipeline (not counting nested streams), that is, in a single pass
     * over the input, without storing anything in a temporary collection.
     */
    @Test @Ignore
    public void h2_invertMultiMap() {
        Map<String, Set<Integer>> input = new HashMap<>();
        input.put("a", new HashSet<>(Arrays.asList(1, 2)));
        input.put("b", new HashSet<>(Arrays.asList(2, 3)));
        input.put("c", new HashSet<>(Arrays.asList(1, 3)));
        input.put("d", new HashSet<>(Arrays.asList(1, 4)));
        input.put("e", new HashSet<>(Arrays.asList(2, 4)));
        input.put("f", new HashSet<>(Arrays.asList(3, 4)));

        Map<Integer, Set<String>> result = null; // TODO

        assertEquals(new HashSet<>(Arrays.asList("a", "c", "d")), result.get(1));
        assertEquals(new HashSet<>(Arrays.asList("a", "b", "e")), result.get(2));
        assertEquals(new HashSet<>(Arrays.asList("b", "c", "f")), result.get(3));
        assertEquals(new HashSet<>(Arrays.asList("d", "e", "f")), result.get(4));
        assertEquals(4, result.size());
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // A general approach is to flatten the input structure in one stage
    // of the pipeline and then to create the result structure using a collector.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // A useful intermediate data structure after the flattening step
    // is a pair of items. You can write your own pair class, or you can
    // use a pre-existing class like AbstractMap.SimpleEntry.
    // </editor-fold>


    /**
     * Select the longest words from an input stream. That is, select the words
     * whose lengths are equal to the maximum word length. For this exercise,
     * you must compute the result in a single pass over the input stream.
     * The type of the input is a Stream, so you cannot access elements at random.
     * The stream is run in parallel, so the combiner function must be correct.
     */
    @Test @Ignore
    public void h3_selectLongestWordsOnePass() {
        Stream<String> input = Stream.of(
            "alfa", "bravo", "charlie", "delta",
            "echo", "foxtrot", "golf", "hotel").parallel();

        List<String> result = input.collect(
            Collector.of(null, null, null, null));
        // TODO implement a collector by replacing the nulls above

        assertEquals(Arrays.asList("charlie", "foxtrot"), result);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // There are several ways to solve this exercise, but one approach is to
    // create a helper class with four functions, and then pass method
    // references to these functions to the Collector.of() method.
    // </editor-fold>



    /**
     * Given a string, split it into a list of strings consisting of
     * consecutive characters from the original string. Note: this is
     * similar to Python's itertools.groupby function, but it differs
     * from Java's Collectors.groupingBy() collector.
     */
    @Test @Ignore
    public void h4_splitCharacterRuns() {
        String input = "aaaaabbccccdeeeeeeaaafff";

        List<String> result = null; // TODO

        assertEquals("[aaaaa, bb, cccc, d, eeeeee, aaa, fff]", result.toString());
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // One possibility is a two-pass approach: one pass to gather data about
    // the boundaries between the runs, and the second to create the substrings
    // based on output from the first.
    // </editor-fold>

    /**
     * Given a parallel stream of strings, collect them into a collection in reverse order.
     * Since the stream is parallel, you MUST write a proper combiner function in order to get
     * the correct result.
     */
    @Test @Ignore
    public void h5_reversingCollector() {
        Stream<String> input =
            IntStream.range(0, 100).mapToObj(String::valueOf).parallel();

        Collection<String> result =
            input.collect(Collector.of(null, null, null));
            // TODO fill in collector functions above

        assertEquals(
            IntStream.range(0, 100)
                     .map(i -> 99 - i)
                     .mapToObj(String::valueOf)
                     .collect(Collectors.toList()),
            new ArrayList<>(result));
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // ArrayDeque supports fast insertion at the front.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Be careful with ordering of the arguments and results in the combiner.
    // </editor-fold>

    /**
     * Given an array of int, find the int value that occurs a majority
     * of times in the array (that is, strictly more than half of the
     * elements are that value), and return that int value in an OptionalInt.
     * Note, return the majority int value, not the number of times it occurs.
     * If there is no majority value, return an empty OptionalInt.
     *
     * For example, given an input array [11, 12, 12] the result should be
     * an OptionalInt containing 12. Given an input array [11, 12, 13]
     * the result should be an empty OptionalInt.
     */

    OptionalInt majority(int[] array) {
        return null; // TODO
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // A two-pass approach may be called for here: a counting pass
    // and a majority-finding pass.
    // </editor-fold>

    @Test @Ignore
    public void h6_majority() {
        int[] array1 = { 13, 13, 24, 35, 24, 24, 35, 24, 24 };
        int[] array2 = { 13, 13, 24, 35, 24, 24, 35, 24 };

        OptionalInt result1 = majority(array1);
        OptionalInt result2 = majority(array2);

        assertEquals(OptionalInt.of(24), result1);
        assertFalse(result2.isPresent());
    }

    /**
     * Write a method that takes an IntFunction and returns a Supplier.
     * An IntFunction takes an int as an argument and returns some object.
     * A Supplier takes no arguments and returns some object. The object
     * type in this case is a Shoe that has a single attribute, its size.
     * The goal is to write a lambda expression that uses the IntFunction
     * and size values provided as arguments, and that returns a Supplier
     * that embodies both of them. This is an example of a functional
     * programming concept called "partial application."
     */
    Supplier<Shoe> makeShoeSupplier(IntFunction<Shoe> ifunc, int size) {
        return null; // TODO
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // You don't want to return the result of calling the IntFunction.
    // Instead, you want to return a lambda that calls the IntFunction.
    // </editor-fold>

    static class Shoe {
        final int size;
        public Shoe(int size) { this.size = size; }
        public int hashCode() { return size ^ 0xcafebabe; }
        public boolean equals(Object other) {
            return (other instanceof Shoe) && this.size == ((Shoe)other).size;
        }
    }

    @Test @Ignore
    public void h7_shoemaker() {
        Supplier<Shoe> sup1 = makeShoeSupplier(Shoe::new, 9);
        Supplier<Shoe> sup2 = makeShoeSupplier(Shoe::new, 13);

        Shoe shoe1 = sup1.get();
        Shoe shoe2 = sup1.get();
        Shoe shoe3 = sup2.get();
        Shoe shoe4 = sup2.get();

        assertTrue(shoe1 != shoe2);
        assertTrue(shoe3 != shoe4);
        assertEquals(new Shoe(9), shoe1);
        assertEquals(shoe1, shoe2);
        assertEquals(new Shoe(13), shoe3);
        assertEquals(shoe3, shoe4);
    }

    /**
     * Write a method that extracts all the superclasses of ArrayList and
     * their implemented classes. Filter out the abstract classes, then
     * create a map with two boolean keys, true is associated to the interfaces
     * and false with the concrete classes.
     */
    @Test @Ignore
    public void h8_mapOfClassesAndInterfaces() {

        Class<?> origin = ArrayList.class;
        Map<Boolean, Set<Class<?>>> result = null; // TODO

        assertEquals(Map.of(false, Set.of(ArrayList.class, Object.class),
                            true,  Set.of(List.class, RandomAccess.class, Cloneable.class,
                                          Serializable.class, Collection.class)),
                     result);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // The beginning of this challenge begins with the same kind of pattern
    // as the E8 intermediate exercise.
    // The interfaces are returned in an array, so one can put them in a stream
    // using Arrays.stream(). To add the class to that stream, you can also
    // use Stream.of() and flatMap the result to have the final stream.
    // Writing the filter step is just a matter of creating the right predicate.
    // Then the partioningBy collector will build the map.
    // </editor-fold>

    /**
     * Write a method that extracts all the superclasses and
     * their implemented classes. Filter out the abstract classes, then
     * create a map with two boolean keys, true is associated to the interfaces
     * and false with the concrete classes. Do that for the provided classes, and
     * arrange the result in a Map<Class, ...> with those classes as the keys.
     */
    @Test @Ignore
    public void h9_mapOfMapsOfClassesAndInterfaces() {

        List<Class<?>> origin = List.of(ArrayList.class, HashSet.class, LinkedHashSet.class);
        Map<Class<?>, Map<Boolean, Set<Class<?>>>> result = null; // TODO

        assertEquals(
            Map.of(
                ArrayList.class,
                    Map.of(false, Set.of(ArrayList.class, Object.class),
                           true,  Set.of(List.class, RandomAccess.class, Cloneable.class,
                                         Serializable.class, Collection.class)),
                HashSet.class,
                    Map.of(false, Set.of(HashSet.class, Object.class),
                           true,  Set.of(Set.class, Cloneable.class,
                                         Serializable.class, Collection.class)),
                LinkedHashSet.class,
                    Map.of(false, Set.of(LinkedHashSet.class, HashSet.class, Object.class),
                           true,  Set.of(Set.class, Cloneable.class,
                                         Serializable.class, Collection.class))),
            result);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // The trick here is to write the whole processing of the previous
    // G8 challenge as a single collector. Once this is done, just pass
    // this collector as the downstream collector of a groupingBy.
    // A filtering collector and a flatMapping collector have been added
    // to JDK9.
    // </editor-fold>
}
