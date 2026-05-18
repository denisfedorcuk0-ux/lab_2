

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String inputString = "Hello, my dear friend! This is a simple test, isn't it? "
                + "Java matches perfectly. OneWord. Go!";

        try {
            System.out.println("--- Оригінальний текст ---");
            System.out.println(inputString);
            System.out.println("\n--- Оброблений текст ---");

            StringBuffer result = processText(inputString);
            System.out.println(result.toString());

        } catch (IllegalArgumentException e) {
            System.err.println("Помилка валідації даних: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Виникла непередбачувана помилка: " + e.getMessage());
        }
    }

    public static StringBuffer processText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Вхідний текст не може бути null.");
        }
        if (text.trim().isEmpty()) {
            return new StringBuffer();
        }

        StringBuffer textBuffer = new StringBuffer(text);
        StringBuffer finalResult = new StringBuffer();

        Pattern sentencePattern = Pattern.compile("[^.!?]+([.!?]+|$)");
        Matcher sentenceMatcher = sentencePattern.matcher(textBuffer);

        while (sentenceMatcher.find()) {
            StringBuffer sentence = new StringBuffer(sentenceMatcher.group());
            finalResult.append(swapFirstAndLastWords(sentence));
        }

        return finalResult;
    }

    private static StringBuffer swapFirstAndLastWords(StringBuffer sentence) {
        Pattern wordPattern = Pattern.compile("[a-zA-Z0-9]+");
        Matcher wordMatcher = wordPattern.matcher(sentence);

        int firstWordStart = -1;
        int firstWordEnd = -1;
        int lastWordStart = -1;
        int lastWordEnd = -1;

        while (wordMatcher.find()) {
            if (firstWordStart == -1) {
                firstWordStart = wordMatcher.start();
                firstWordEnd = wordMatcher.end();
            }
            lastWordStart = wordMatcher.start();
            lastWordEnd = wordMatcher.end();
        }

        if (firstWordStart == -1 || firstWordStart == lastWordStart) {
            return sentence;
        }

        StringBuffer firstWord = new StringBuffer(sentence.substring(firstWordStart, firstWordEnd));
        StringBuffer lastWord = new StringBuffer(sentence.substring(lastWordStart, lastWordEnd));

        sentence.replace(lastWordStart, lastWordEnd, firstWord.toString());
        sentence.replace(firstWordStart, firstWordEnd, lastWord.toString());

        return sentence;
    }
}