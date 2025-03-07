package src.kafka;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class KafkaLikeMultiProducerConsumer {
        public static void main(String[] args) {
            int bufferSize = 10;
            BlockingQueue<String> buffer = new ArrayBlockingQueue<>(bufferSize);

            int numProducers = 2;
            int numConsumers = 2;

            Thread[] producerThreads = new Thread[numProducers];
            Thread[] consumerThreads = new Thread[numConsumers];

            for (int i = 0; i < numProducers; i++) {
                producerThreads[i] = new Thread(new Producer(buffer));
                producerThreads[i].setName("Thread-"+i);
                producerThreads[i].start();
            }

            for (int i = 0; i < numConsumers; i++) {
                consumerThreads[i] = new Thread(new Consumer(buffer));
                consumerThreads[i].setName("Thread-"+i);
                consumerThreads[i].start();
            }

            // Allow the threads to run for a while
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Stop the threads gracefully
            for (Thread producerThread : producerThreads) {
                producerThread.interrupt();
            }

            for (Thread consumerThread : consumerThreads) {
                consumerThread.interrupt();
            }
        }
    }

class Producer implements Runnable {
    private final BlockingQueue<String> buffer;
    private int messageCount;

    public Producer(BlockingQueue<String> buffer) {
        this.buffer = buffer;
        this.messageCount = 0;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = "Producer-" + Thread.currentThread().getName() + " Message " + messageCount++;
                buffer.put(message);
                System.out.println("Produced: " + message);
                Thread.sleep(1000); // Simulate message production delay
            }
        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted:"+ Thread.currentThread().getName());
        }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<String> buffer;

    public Consumer(BlockingQueue<String> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = buffer.take();
                System.out.println("Consumer-" + Thread.currentThread().getName() + " Consumed: " + message);
                // Simulate message processing
                Thread.sleep(1500);
            }
        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
            System.out.println("Consumer Thread interrupted:"+ Thread.currentThread().getName());
        }
    }
}

