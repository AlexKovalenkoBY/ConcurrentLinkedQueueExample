//from https://java-online.ru/concurrent-queue-noblock.xhtml
package com.wbozon.wbozon;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueExample 
{
    private Queue<com.wbozon.wbozon.OutObject> queue = null;

    private volatile boolean cycle = true;

    ConcurrentLinkedQueueExample()
    {
        queue = new ConcurrentLinkedQueue<com.wbozon.wbozon.OutObject>();

        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());
        producer.start();
        consumer.start();

        while (consumer.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    class Producer implements Runnable {

        public void run(ArrayList<com.wbozon.wbozon.OutObject> list) {
            System.out.println("Producer started");
            try {
                for (int i = 1; i <= 10; i++) {
                    OutObject str = list.get(i); 
                    queue.add(str);
                    System.out.println("Producer added : "
                                                   + str);
                    Thread.sleep(200);
                }
                cycle = false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            
        }
    }
    class Consumer implements Runnable {
        public void run() {
            OutObject str;
            System.out.println("Consumer started\n");
            while (cycle || queue.size() > 0) {
                if ((str = queue.poll()) != null)
                  System.out.println("  consumer removed : "
                                                     + str);
                try {
                    Thread.sleep(500);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) 
    {
        new ConcurrentLinkedQueueExample();
    }
}