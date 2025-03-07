package src.kafka.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Callables {
    public static void main(String[] args) {
//        new Callables().completableFutureExample();
//        new Callables().completableFutureAllJoinExample();
        new Callables().applyAsyncAndThenAccept();
    }

    void completableFutureExample(){
        ExecutorService executor = Executors.newFixedThreadPool(1);
        //CompletableFuture implements and provides additional features to Future interface
        //Also CompletableFuture provides non blocking operations
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
            return "Hello, world!";
        }, executor);

        future.thenAccept(System.out::println);//Is non blocking. .get() would have been blocking

        System.out.println("Printing from main thread");
        executor.shutdown();
    }

    void applyAsyncAndThenAccept(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

        //thenApplyAsync(): This method is used to process the result of a task asynchronously and return a new CompletableFuture with the transformed result.
        // The processing is done by a separate thread in the ForkJoinPool.commonPool().
        CompletableFuture<Integer> transformedFuture = future.thenApplyAsync(s -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            return s.length();
        });

        transformedFuture.thenAccept(length -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            System.out.println("Length of Hello: " + length);
        });
    }

    void completableFutureAllJoinExample(){
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            // Some long-running operation
            return "Result 1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            // Some long-running operation
            return "Result 2";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            // Some long-running operation
            return "Result 3";
        });

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2, future3);

        allFutures.thenRun(() -> {
            // All futures completed
            String result1 = future1.join();
            String result2 = future2.join();
            String result3 = future3.join();
            System.out.println("result1"+ result1 + ", " + "result2" +result2 + ", " + result3);
        });
    }
}


//CompletableFuture code

//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//MyCallable myCallable = new MyCallable();
//
//// Create a single-thread executor
//ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//try {
//// Submit the Callable task and get a Future
//Future<String> future = executorService.submit(myCallable);
//
//// Get the result and print it
//String result = future.get();
//    System.out.println(result);
//} catch (Exception e) {
//        e.printStackTrace();
//} finally {
//        // Shutdown the executor
//        executorService.shutdown();
//}

