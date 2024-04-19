package removeKDigits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Solution {
    private static List<Integer> integerList;
    public String removeKDigits(String num, int k){
        Integer[] arr = IntStream.range(0, num.length()).boxed().toArray(Integer[]::new);
        integerList = Arrays.asList(arr);
        if(num.length()== k){
            return "0";
        }else if (num.length()>k){
            for(int inger : integerList){
            }
            return "1";
        }
        return num;
    }

    public int[] twoSum(int[] nums, int target) {
        int [] arr = new int[2];
        for(int i = 0; i< nums.length-1; i++){
            for(int j = i+1; j< nums.length; j++){
                if((nums[i] + nums[j]) == target){
                    arr =  new int[]{i, j};
                    break;
                }
            }
        }
        return arr;
    }

}
