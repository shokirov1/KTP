package lab_1;

public class Palindrome {
    public static void main(String[] args) {
        System.out.println("Строка является палиндромом?\n" + reverseString("лепс спел"));
    }

    public static boolean reverseString(String str) {
        String answ = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            answ += str.charAt(i);
        }

        return isPalindrome(answ, str);
    }

    public static boolean isPalindrome(String str, String str2) {
        if (str.equals(str2)) {
            return true;
        } else
            return false;
    }
}