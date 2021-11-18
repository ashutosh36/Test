package src.Helper;

public class ArrayHelper {

    public static String printArray(int[] arr){
        StringBuilder sb = new StringBuilder();

        for(int a: arr){
            sb.append(a).append(", ");
        }
        return sb.toString();
    }

}
