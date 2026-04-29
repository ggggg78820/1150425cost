 public class IsSubsequence {
 
 //程式進入點
    public static void main(String[] args) {
        IsSubsequence solution = new IsSubsequence();
        String s = "abc";
        String t = "ahbgdc";
        boolean result = solution.isSubsequence(s, t);
        System.out.println("Is '" + s + "' a subsequence of '" + t + "'? " + result);
    }
    public boolean isSubsequence(String s, String t) {
        int i = 0; // Pointer for s
        int j = 0; // Pointer for t
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++; // Move pointer in s if characters match
            }
            j++; // Always move pointer in t
        }
        return i == s.length(); // If we've matched all characters in s
    }
 }
 