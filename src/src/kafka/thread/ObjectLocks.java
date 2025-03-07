package src.kafka.thread;

import java.util.concurrent.locks.StampedLock;

public class ObjectLocks implements Runnable {

//    public void run() { lock(); }
    public void run() { classLock(); }
    public void lock()
    {
        System.out.println(
                Thread.currentThread().getName());
        synchronized (this)
        {
            System.out.println(
                    "in block "
                            + Thread.currentThread().getName());
            System.out.println(
                    "in block "
                            + Thread.currentThread().getName()
                            + " end");
        }
    }

    public void classLock()
    {
        System.out.println(
                Thread.currentThread().getName());
        synchronized (ObjectLocks.class)
        {
            System.out.println(
                    "in block "
                            + Thread.currentThread().getName());
            System.out.println(
                    "in block "
                            + Thread.currentThread().getName()
                            + " end");
        }
    }

    public static void main(String[] args)
    {
        ObjectLocks ol = new ObjectLocks();
        Thread t1 = new Thread(ol);
        Thread t2 = new Thread(ol);
        ObjectLocks ol2 = new ObjectLocks();
        Thread t3 = new Thread(ol2);
        t1.setName("t1");
        t2.setName("t2");
        t3.setName("t3");
        t1.start();
        t2.start();
        t3.start();
    }
}


//Stamped locking
class BankAccount {
    private double balance = 1000.0; // Initial balance
    private StampedLock lock = new StampedLock(); //provides read, write and stamp based validation lock object

    public double getBalance() {
        long stamp = lock.tryOptimisticRead();
        double currentBalance = balance;
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                currentBalance = balance;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return currentBalance;
    }

    public void updateBalance(double amount) {
        long stamp = lock.writeLock();
        try {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " updated the balance. New balance: " + balance);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
}

class ClientThread extends Thread {
    private BankAccount account;

    public ClientThread(BankAccount account) {
        this.account = account;
    }

    @Override
    public void run() {
        double balance = account.getBalance();
        System.out.println(Thread.currentThread().getName() + " viewed the balance: " + balance);
        account.updateBalance(100); // Add $100 to the balance
    }
}

class StampedLockExample {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        // Create multiple client threads to view and update the balance
        for (int i = 1; i <= 3; i++) {
            ClientThread clientThread = new ClientThread(account);
            clientThread.start();
        }
    }
}
