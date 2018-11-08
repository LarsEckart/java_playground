package lars.katas.spellcheck;

interface SpellChecker {

    public void addWord(String word);

    public boolean checkCorrectness(String word, MultiMap<Integer, String> nearMatches);
}
