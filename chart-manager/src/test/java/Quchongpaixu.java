import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author zhongl<br>
 * @createDate 2020/11/10 15:49 <br>
 */
public class Quchongpaixu {
    public static void main(String[] args) {
     /*   Scanner sc=new Scanner(System.in);
        while(sc.hasNext()){

            TreeSet<Integer> set=new TreeSet<Integer>();
            int n=sc.nextInt();
            if(n>0){
                for(int i=0;i<n;i++){
                    set.add(sc.nextInt());
                }
            }
            for(Integer i:set){
                System.out.println(i);
            }
        }*/


        Scanner sc = new Scanner(System.in);

        while(sc.hasNext()){
            String s = new String(sc.nextLine());
            if(s.length()%8 !=0 )
                s = s + "00000000";

            while(s.length()>=8){
                System.out.println(s.substring(0, 8));
                s = s.substring(8);
            }
        }
    }
}
