import java.util.Objects;
import java.util.Scanner;

/**
 * @author zhongl<br>
 * @createDate 2020/11/10 10:21 <br>
 */
//字符串长度统计
public class Zifuchuangchangdu {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
   /* while(in.hasNextLine()){
        String s = in.nextLine();
        String substring = s.substring(s.lastIndexOf(" ")+1);
        String trim = substring.trim();
        System.out.println(trim.length());
    }*/

        String a = in.nextLine();
        String b = in.next();

        int num=0;
        String [] chars = a.split("");
        for (String cha:chars
             ) {
            if(b.equalsIgnoreCase(cha)){
                num++;
            }

        }
        System.out.println(num);


    }



}
