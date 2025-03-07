package src.kafka.thread;

public class OddEven {

    //Improve further
    private final Object lock = new Object();
    private boolean numberTurn = true; // Indicates whether it's the number thread's turn

    public static void main(String[] args) {
        OddEven printer = new OddEven();
        Thread numberThread = new Thread(() -> printer.printNumbers());
        Thread letterThread = new Thread(() -> printer.printLetters());
        letterThread.start();
        numberThread.start();
    }

    public void printNumbers() {
        for (int i = 1; i <= 26; i++) { // Adjust the range as needed
            synchronized (lock) {
                while (!numberTurn) {
                    try {
                        lock.wait(); // Wait until it's this thread's turn
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.print(i + " ");
                numberTurn = false; // Pass the turn to the letter thread
                lock.notifyAll(); // Notify the waiting thread
            }
        }
    }

    public void printLetters() {
        for (char c = 'A'; c <= 'Z'; c++) { // Adjust the range as needed
            synchronized (lock) {
                while (numberTurn) {
                    try {
                        lock.wait(); // Wait until it's this thread's turn
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.print(c + " ");
                numberTurn = true; // Pass the turn to the number thread
                lock.notifyAll(); // Notify the waiting thread
            }
        }
    }
}
