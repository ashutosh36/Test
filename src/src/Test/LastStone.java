package src.Test;

//https://leetcode.com/problems/last-stone-weight-ii/
public class LastStone {

    public static void main(String[] args) {
        int[] inp= {2,7,4,1,8,1};
        System.out.println(new LastStone().lastStoneWeightII(inp));

    }
    public int lastStoneWeightII(int[] stones) {
        int len= stones.length;

        int sum=0;
        for(int s: stones) sum+=s;

        boolean[][] dp = new boolean[len+1][sum+1];

        for(int i=0;i<=len; i++){
            dp[i][0]=true;
        }

        for(int j=0;j<=sum;j++){
            dp[0][j]=false;
        }

        for(int i=1;i<=sum;i++){
            for(int j=1;j<=len;j++){
                dp[i][j]= dp[i-1][j];
                if(stones[i] <= j){
                    dp[i][j] |= dp[i-1][j-stones[i]];
                }
            }
        }
        int diff= sum;
        for(int j=sum/2; j>=0;j--){
            if(dp[len][j]){
                diff = sum= j*2;
                break;
            }
        }

        return diff;
    }
}
