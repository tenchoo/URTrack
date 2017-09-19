package com.urt.web.common.util;

public class ICCID {
	
	/**
	 *  8986 0616 0100 1031 4769
	 */
	
	public static String replaceLastChar(String longNumber){
		longNumber=longNumber.replaceAll(" ", "");
		int length=longNumber.length();
		if(length==19){
			longNumber+="0";
		}


		String nums[] =new String[5];
		for (int i=0;i<nums.length;){
			if (i == 4) {
				nums[i] = longNumber.substring(4*i,4*i+3);
			} else {
				nums[i] = longNumber.substring(4*i,4*i+4);
			}
			i++;
/*			String s=sub(longNumber);
			nums[i]=s;
			longNumber=longNumber.replaceAll(s, "");
			i++;
			if(i==nums.length){
				String ss=nums[i-1].substring(0, nums[i-1].length()-1);
				nums[i-1]=ss;
			}*/
		}
		int index=0;
		int evenSum=0;
		char[] odd=new char[10];
		String copylongNumber="";
		for (int i = 0; i < nums.length; i++) {
			String num = nums[i];
			copylongNumber+=num;
			if(num.length()==4){
				evenSum+=charToInt(num.charAt(1))+charToInt(num.charAt(3));
			}else{
				evenSum+=charToInt(num.charAt(1));
			}

			if(index==10) break;
			odd[index]=num.charAt(0);
			index++;
			odd[index]=num.charAt(2);
			index++;
		}
		//System.out.println(evenSum);
		//8801001346
		int oddSum=0;
		for (int i = 0; i < odd.length; i++) {
			int tmp=charToInt(odd[i])*2;
			if(tmp>=10){
				oddSum+=tmp-9;
			}else{
				oddSum+=tmp;
			}
		}
		//System.out.println(oddSum);
		int totalSum=evenSum+oddSum;
		Integer lastNum=Integer.parseInt(String.valueOf(String.valueOf(totalSum).charAt(1)));
		if(lastNum==0){
			return copylongNumber+0;
		}else{
			return copylongNumber+(10-lastNum);
		}
	}
	
	public static void main(String[] args) {
		String longNumber="8986061702000000020";
		System.out.println(replaceLastChar(longNumber));
//		System.out.println("89860616010010314777".equals(replaceLastChar(longNumber)));
	}
	public static String sub(String longNumber) {
		return longNumber.substring(0, 4);
	}
	public static int charToInt(char cha){
		return Integer.parseInt(String.valueOf(cha));
	}
	
}
