package lars.advent2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Day 4 of advent of code 2017.
 */
public class PassphraseChecker {

    public boolean isAcceptedNoDuplicates(String input) {
        Passphrase passphrase = new Passphrase(input);
        int distinctWordsCount = new HashSet<>(passphrase.getWords()).size();
        int wordsCount = new ArrayList<>(passphrase.getWords()).size();

        return distinctWordsCount == wordsCount;
    }

    public long countValidPassphrases(List<String> passphrases) {
        return passphrases.stream().filter(this::isAcceptedNoDuplicates).distinct().count();
    }

    public boolean isAnagram(String first, String second) {
        char[] firstSorted = first.toCharArray();
        Arrays.sort(firstSorted);
        char[] secondSorted = second.toCharArray();
        Arrays.sort(secondSorted);
        return Arrays.equals(firstSorted, secondSorted);
    }

    public boolean isValidNoAnagrams(String passphrase) {
        List<String> words = new Passphrase(passphrase).getWords();
        for (String word : words) {
            List<String> wordsToCheckAgainst = new ArrayList<>(words);
            wordsToCheckAgainst.remove(word);
            for (int i = 0; i < wordsToCheckAgainst.size(); i++) {
                if (isAnagram(word, wordsToCheckAgainst.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public long countValidPassphrasesNoAnagram(List<String> passphrases) {
        return passphrases.stream().filter(this::isValidNoAnagrams).distinct().count();
    }

    private static class Passphrase {

        private String passphrase;

        public Passphrase(String passphrase) {
            this.passphrase = passphrase;
        }

        public List<String> getWords() {
            return List.of(passphrase.split(" "));
        }
    }
}
