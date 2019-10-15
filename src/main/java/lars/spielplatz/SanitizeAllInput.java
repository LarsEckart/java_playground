package lars.spielplatz;

import org.owasp.encoder.Encode;

public class SanitizeAllInput {

    public static void main(String[] args) {
        String untrusted = "<script> alert(1); </script>";
        System.out.println(Encode.forHtml(untrusted));
    }
}
