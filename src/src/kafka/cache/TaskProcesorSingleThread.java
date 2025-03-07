package src.kafka.cache;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class TaskProcesorSingleThread {
//https://leetcode.com/problems/single-threaded-cpu/

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new TaskProcesorSingleThread().getOrder(new int[][]{{7,10},{7,12},{7,5},{7,4},{7,2}})));
    }

    public int[] getOrder(int[][] tasks) {
        int len= tasks.length;
        int[] res= new int[len];
        Task[] arr= new Task[len];

        for (int i = 0; i < len; i++) {
            Task task= new Task(i, tasks[i][0], tasks[i][1]);
            arr[i]=task;
        }

        Arrays.sort(arr, Comparator.comparingInt(a->a.enqueueTime));

        PriorityQueue<Task> pq= new PriorityQueue<>((a, b)-> a.processingTime==b.processingTime?a.idx-b.idx: a.processingTime-b.processingTime);

        int ansIdx=0, taskIdx=0, currTime=0;

        while (ansIdx< len){
            while(taskIdx < len && arr[taskIdx].enqueueTime <= currTime){
                pq.offer(arr[taskIdx++]);
            }
            if(pq.isEmpty()){
                currTime= arr[taskIdx].enqueueTime;
            }else{
                Task pollTask= pq.poll();
                currTime+= pollTask.processingTime;
                res[ansIdx++]= pollTask.idx;
            }
        }

//         System.out.println(Arrays.toString(res));
        return res;
    }

    class Task {
        int idx;
        int enqueueTime;
        int processingTime;

        Task(int idx , int en , int pro){
            this.idx = idx;
            this.enqueueTime = en;
            this.processingTime = pro;
        }
    }
}
