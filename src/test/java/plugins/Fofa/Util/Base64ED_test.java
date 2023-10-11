package plugins.Fofa.Util;

public class Base64ED_test {
    public static void main(String[] args) {
        String a = Base64ED.encode("abc");
        System.out.println(a);
        String b = Base64ED.decode("YWJj");
        System.out.println(b);
    }
}
