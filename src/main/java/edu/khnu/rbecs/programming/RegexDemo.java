package edu.khnu.rbecs.programming;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo {
    static void main() {
        String regex = "\\(\\d{3,4}\\)";
        String str = "tel: +38(123)123-45-66 afdg(456)  (sdf)  (1234) fg(777)fg";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            System.out.println(m.start() + ":" + m.end() + "=" + m.group());
        }
        String s = "abr.aca.dab.ra";
        System.out.println(s);
        System.out.println(s.replace(".", ""));
        System.out.println(s.replaceAll("([a-z]+).([a-z]+)", "[$2]{$1}"));
    }
}
