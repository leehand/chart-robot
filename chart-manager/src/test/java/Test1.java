
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Test1 {
	public static void main(String[] args)  {
//		double a=Math.ceil(50*100f/60)/100;
//		System.out.println(a);
		
//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
//		Date parse1 = dateFormat.parse("2019-01-00");
//		Date parse2 = dateFormat.parse("2018-01-00");
//		System.out.println(parse1.getTime());
//		System.out.println(parse2.getTime());
		
		/*
		 * for (int i = 0; i < 100; i++) {
		 * System.out.println(UUID.fastUUID().toString().replace("-", "")); }
		 */
	
//		String mobile="1802682";
//	
//	 	if(mobile!=null && mobile.length()>=6) {
//    		String subString="";
//    		if(mobile.length()==11) {
//    			subString = mobile.substring(3, mobile.length()-4);
//    		}else {
//    			subString = mobile.substring(2, mobile.length()-3);
//    			
//    		}
//    		String[] str=mobile.split(subString);
//    		subString=subString.replaceAll("\\d", "*");
//    		mobile=str[0]+subString+str[1];
//    	}
//		System.out.println(mobile);
		
		
//		String url="aaaa.jpg";
//		if(url.split("\\.").length>1){
//			System.out.println(333);
//		}
//
//        int[] ints = twoSum(new int[]{2, 5, 7, 9, 12, 3}, 21);
//        System.out.println(ints);\
        String str1 = "abc";

        String str2 = new String("abc");

    }

   static public int[] twoSum(int[] nums, int target) {
	    Map<Integer,Integer> map=new HashMap();
	    int[] result=new int[2];

        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                result[0]=i;
                result[1]=map.get(nums[i]);
                return result;
            }
            map.put(target-nums[i],i);
        }

        return result;

    }

//   static public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//       ListNode node=new ListNode();
//
//
//	    return node;
//
//    }

}
//class ListNode {
//    int val;
//    ListNode next;
//    ListNode() {}
//    ListNode(int val) { this.val = val; }
//    ListNode(int val, ListNode next) { this.val = val; this.next = next;}
//
//}
