package lars.katas.spellcheck;

import java.util.Collection;

public class ParsingSpellChecker {

    /**
     * Whether we can spell check, which we can't until we get a word or a
     * dictionary (which is a thing with a lot of words).
     */
    private boolean canCheck;

    /**
     * The spell checker, which doesn't parse.
     */
    private final SpellChecker checker;

    /**
     * The current string we're working on.
     */
    private String str;

    /**
     * The length of the current string.
     */
    private int len;

    /**
     * The current position within the string.
     */
    private int pos;

    public ParsingSpellChecker(SpellChecker checker) {
        this.checker = checker;
    }

    public void addWord(String word) {
        this.checker.addWord(word);
        this.canCheck = true;
    }

    public void check(String str) {
        if (this.canCheck) {
            this.str = str;
            this.len = this.str.length();
            this.pos = 0;

            while (hasMore()) {
                skipToWord();
                if (hasMore()) {
                    if (Character.isLetter(currentChar())) {
                        checkCurrentWord();
                    } else {
                        // not at an alpha character. Might be some screwy formatting or
                        // a nonstandard tag.
                        skipThroughWord();
                    }
                }
            }
        }
    }

    /**
     * Consumes from one string to another.
     */
    private void consumeFromTo(String from, String to) {
        if (consume(from)) {
            consumeTo(to);
        }
    }

    /**
     * Skips content between pairs of brackets, a la HTML, such as, oh,
     * "<html>foobar</html>." Doesn't handle slash-gt, such as "<html
     * style="lackthereof" />".
     */
    private void skipBracketedSection(String section) {
        consumeFromTo("<" + section + ">", "</" + section + ">");
    }

    private void skipLink() {
        consumeFromTo("{@link", "}");
    }

    private void skipBlanks() {
        while (this.pos + 2 < this.len
                && currentChar() != '<'
                && !this.str.substring(this.pos, this.pos + 2).equals("{@")
                && !Character.isLetterOrDigit(currentChar())) {
            ++this.pos;
        }
    }

    private void skipToWord() {
        skipBracketedSection("code");
        skipBracketedSection("pre");
        skipLink();
        consume("&nbsp;");
    }

    private void checkWord(String word, int position) {
        MultiMap<Integer, String> nearMatches = new MultiMap<Integer, String>();
        boolean valid = this.checker.checkCorrectness(word, nearMatches);
        if (!valid) {
            wordMisspelled(word, position, nearMatches);
        }
    }

    private void wordMisspelled(String word, int position, MultiMap<Integer, String> nearMatches) {
        int nPrinted = 0;
        final int printGoal = 15;
        for (int i = 0; nPrinted < printGoal && i < 4; ++i) { // 4 == max edit distance
            Collection<String> matches = nearMatches.get(i);
            if (matches != null) {
                for (String match : matches) {
                    // This is not debugging output -- this is actually wanted.
                    System.out.println("    near match '" + match + "': " + i);
                    ++nPrinted;
                }
            }
        }
    }

    private boolean consume(String what) {
        skipBlanks();
        if (this.pos + what.length() < this.len && this.str.substring(this.pos).startsWith(what)) {
            this.pos += what.length();
            return true;
        } else {
            return false;
        }
    }

    private void consumeTo(String what) {
        int len = this.str.length();
        while (this.pos < len && this.pos + what.length() < len && !this.str.substring(this.pos).startsWith(what)) {
            ++this.pos;
        }
    }

    private Character currentChar() {
        return this.str.charAt(this.pos);
    }

    private boolean hasMore() {
        return this.pos < this.len;
    }

    private void checkCurrentWord() {
        StringBuffer word = new StringBuffer();
        word.append(currentChar());

        int startingPosition = this.pos;
        boolean canCheck = true;

        ++this.pos;

        // spell check words that do not have:
        //     - mixed case (varName)
        //     - embedded punctuation ("wouldn't", "pkg.foo")
        //     - numbers (M16, BR549)
        while (hasMore()) {
            char ch = currentChar();
            if (Character.isWhitespace(ch)) {
                break;
            } else if (Character.isLowerCase(ch)) {
                word.append(ch);
                ++this.pos;
            } else if (Character.isUpperCase(ch)) {
                skipThroughWord();
                return;
            } else if (Character.isDigit(ch)) {
                skipThroughWord();
                return;
            } else {
                // must be punctuation, which we can check it if there's nothing
                // but punctuation up to the next space or end of string.
                if (this.pos + 1 == this.len) {
                    // that's OK to check
                    break;
                } else {
                    ++this.pos;
                    while (hasMore() && !Character.isWhitespace(currentChar()) && !Character.isLetterOrDigit(currentChar())) {
                        // skipping through punctuation
                        ++this.pos;
                    }
                    if (this.pos == this.len || Character.isWhitespace(currentChar())) {
                        // punctuation ended the word, so we can check this
                        break;
                    } else {
                        // punctuation did NOT end the word, so we can NOT check this
                        skipThroughWord();
                        return;
                    }
                }
            }
        }

        // has to be more than one character:
        if (canCheck && this.pos - startingPosition > 1) {
            checkWord(word.toString(), startingPosition);
        }
    }

    private void skipThroughWord() {
        ++this.pos;
        while (hasMore() && !Character.isWhitespace(currentChar())) {
            ++this.pos;
        }
    }
}

