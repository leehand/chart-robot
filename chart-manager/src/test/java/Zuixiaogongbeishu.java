import java.util.Scanner;

/**
 * @author zhongl<br>
 * @createDate 2020/11/10 10:21 <br>
 */
//最小公倍数
public class Zuixiaogongbeishu {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        int b = in.nextInt();
        System.out.println(lcm(a,b));

    }

    public static int lcm(int a,int b){

        return a*b/glc(a,b);

    }


    public static int glc(int a,int b){
        if (b==0){return a;}
        return glc(b,a%b);
    }


}
