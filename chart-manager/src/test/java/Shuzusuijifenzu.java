import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * @author zhongl<br>
 * @createDate 2020/11/12 15:49 <br>
 */
public class Shuzusuijifenzu {
    public static Random random = new Random();
    public static void main(String[] args) {
        String[] arr={"1 ","2 ","3 ","4 ","5 ","6 ","7 ","8 ","9 ","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27"};

        Map<Integer, List<String>> ramdomArr = getRamdomArr(arr, 3);
        System.out.println(JSON.toJSONString(ramdomArr));
    }

    public static Map<Integer,List<String>> getRamdomArr(String [] sourceArr,int subArrNum){
       int length= sourceArr.length;

       Map<Integer, List<String>> map=new HashMap<>();
       List<Integer> intList=new ArrayList<>();

       //遍历数组分配数据
        for(int i=length;i>0;i--){
           int index = random.nextInt(length);

           //下标去重
           while(intList.contains(index) && index>=0 && index<length){
               if(index==length-1){
                   index=0;
               }else{
                   index++;
               }
           }

           //轮询把名称分配到不同的列表
           int mapKey=i%subArrNum;
           if(map.get(mapKey)==null){
               List<String> subList=new ArrayList();
               subList.add(sourceArr[index]);
               map.put(mapKey,subList);
           }else{
               map.get(mapKey).add(sourceArr[index]);
           }

           //把已经取过数的下标存到去重列表里
            intList.add(index);
       }
        return map;
    }
}
