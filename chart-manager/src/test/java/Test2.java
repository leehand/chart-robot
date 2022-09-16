/**
 * @author zhongl<br>
 * @createDate 2020/10/28 17:04 <br>
 */
public class Test2 {

    public static void main(String[] args) {
        int [] arr={2,5,8,12,34,56,88,434};
        Integer integer = binarySearch(arr, 88);
        System.out.println(integer);

    }



    public static Integer binarySearch(int[] arr,int key){
        int low=0;
        int high=arr.length-1;

        while(low<high){
            int mid=(high+low)/2;
            int midValue=arr[mid];

            if(key<midValue){
                high=mid-1;
            }else  if(key>midValue){
                low=mid+1;
            }else{
                return mid;
            }
        }
        return -1;
    }
}
