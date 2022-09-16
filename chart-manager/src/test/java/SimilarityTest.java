import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import org.junit.Test;


public class SimilarityTest {

    public static void main(String[] args) {
        SimilarityStrategy strategy = new JaroWinklerStrategy();
        String target = "发起调岗申请";
        String [] arr=new String[]{"积分在哪里申请？",

                "我的积分可以在哪里查看？",

                "在哪里发起调岗申请？",

                "员工内购在哪里申请呢？",

                "招聘需求在哪里提申请呢？"};
        for(String source:arr){
            StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
            double score = service.score(source, target); // Score is 0.90
            System.out.print(source);
            System.out.println(score);
        }



    }


    @Test
    public void test2(){
//        double result = Similarity.cilinSimilarity("电动车", "自行车");
//        System.out.println(result);
//
//        String word = "混蛋";
//        HownetWordTendency hownetWordTendency = new HownetWordTendency();
//        result = hownetWordTendency.getTendency(word);
//        System.out.println(word + "  词语情感趋势值：" + result);
    }
}
