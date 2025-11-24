package academy.tochkavhoda.base;

import java.util.Locale;
import java.util.StringJoiner;

public class StringOperations {

    public static int getSummaryLength(String[] strings) {
        int length = 0;
        for (String s : strings) length += s.length();
        return length;
    }

    public static String getFirstAndLastLetterString(String string) {
        return "" + string.charAt(0) + string.charAt(string.length() - 1);
    }

    public static boolean isSameCharAtPosition(String string1, String string2, int index) {
        return string1.charAt(index) == string2.charAt(index);
    }

    public static boolean isSameFirstCharPosition(String string1, String string2, char character) {
        return string1.indexOf(character) == string2.indexOf(character);
    }

    public static boolean isSameLastCharPosition(String string1, String string2, char character) {
        return string1.lastIndexOf(character) == string2.lastIndexOf(character);
    }

    public static boolean isSameFirstStringPosition(String string1, String string2, String str) {
        return string1.indexOf(str) == string2.indexOf(str);
    }

    public static boolean isSameLastStringPosition(String string1, String string2, String str) {
        return string1.lastIndexOf(str) == string2.lastIndexOf(str);
    }

    public static boolean isEqual(String string1, String string2) {
        return string1.equals(string2);
    }

    public static boolean isEqualIgnoreCase(String string1, String string2) {
        return string1.equalsIgnoreCase(string2);
    }

    public static boolean isLess(String string1, String string2) {
        return string1.compareTo(string2) < 0;
    }

    public static boolean isLessIgnoreCase(String string1, String string2) {
        return string1.compareToIgnoreCase(string2) < 0;
    }

    public static String concat(String string1, String string2) {
        return string1.concat(string2);
    }

    public static boolean isSamePrefix(String string1, String string2, String prefix) {
        return string1.startsWith(prefix) && string2.startsWith(prefix);
    }

    public static boolean isSameSuffix(String string1, String string2, String suffix) {
        return string1.endsWith(suffix) && string2.endsWith(suffix);
    }

    public static String getCommonPrefix(String string1, String string2) {
        int minLength = Math.min(string1.length(), string2.length());
        for (int i = 0; i < minLength; i++)
            if (string1.charAt(i) != string2.charAt(i)) return string1.substring(0, i);
        return string1.substring(0, minLength);
    }

    public static String reverse(String string) {
        return new StringBuilder(string).reverse().toString();
    }

    public static boolean isPalindrome(String string) {
        return string.equals(reverse(string));
    }

    public static boolean isPalindromeIgnoreCase(String string) {
        return string.equalsIgnoreCase(reverse(string));
    }

    public static String getLongestPalindromeIgnoreCase(String[] strings) {
        String longest = "";
        for (String s : strings)
            if (isPalindromeIgnoreCase(s))
                if (s.length() > longest.length()) longest = s;
        return longest;
    }

    public static boolean hasSameSubstring(String string1, String string2, int index, int length) {
        if (index + length > string1.length() || index + length > string2.length()) return false;
        String sub1 = string1.substring(index, index + length);
        String sub2 = string2.substring(index, index + length);
        return sub1.equals(sub2);
    }

    public static boolean isEqualAfterReplaceCharacters(String string1, char replaceInStr1, char replaceByInStr1, String string2, char replaceInStr2, char replaceByInStr2) {
        String s1 = string1.replace(replaceInStr1, replaceByInStr1);
        String s2 = string2.replace(replaceInStr2, replaceByInStr2);
        return s1.equals(s2);
    }

    public static boolean isEqualAfterReplaceStrings(String string1, String replaceInStr1, String replaceByInStr1, String string2, String replaceInStr2, String replaceByInStr2) {
        String s1 = string1.replace(replaceInStr1, replaceByInStr1);
        String s2 = string2.replace(replaceInStr2, replaceByInStr2);
        return s1.equals(s2);
    }

    public static boolean isPalindromeAfterRemovingSpacesIgnoreCase(String string) {
        String clean = string.replace(" ", "");
        return isPalindromeIgnoreCase(clean);
    }

    public static boolean isEqualAfterTrimming(String string1, String string2) {
        return string1.trim().equals(string2.trim());
    }

    public static String makeCsvStringFromInts(int[] array) {
        return makeCsvStringBuilderFromInts(array).toString();
    }

    public static String makeCsvStringFromDoubles(double[] array) {
        return makeCsvStringBuilderFromDoubles(array).toString();
    }

    public static StringBuilder makeCsvStringBuilderFromInts(int[] array) {
        StringJoiner sj = new StringJoiner(",");
        for (int i : array) sj.add(String.valueOf(i));
        return new StringBuilder(sj.toString());
    }

    public static StringBuilder makeCsvStringBuilderFromDoubles(double[] array) {
        StringJoiner sj = new StringJoiner(",");
        for (double d : array) sj.add(String.format(Locale.ENGLISH, "%.2f", d));
        return new StringBuilder(sj.toString());
    }

    public static StringBuilder removeCharacters(String string, int[] positions) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = positions.length - 1; i >= 0; i--) sb.deleteCharAt(positions[i]);
        return sb;
    }

    public static StringBuilder insertCharacters(String string, int[] positions, char[] characters) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = 0; i < positions.length; i++) sb.insert(positions[i] + i, characters[i]);
        return sb;
    }
}
