package assignments.project.threads;

//BlockingQueue<String> queue = new LinkedBlockingQueue<>();

import java.util.concurrent.BlockingQueue;

class Producer implements Runnable {
    private final BlockingQueue<String> q;
    Producer(BlockingQueue<String> q) { this.q = q; }

    public void run() {
        try {
            q.put("data");      // thread-safe operation; blocks if full
        } catch (InterruptedException ignored) {}
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<String> q;
    Consumer(BlockingQueue<String> q) { this.q = q; }

    public void run() {
        try {
            String x = q.take(); // waits until Producer supplies
            System.out.println(x);
        } catch (InterruptedException ignored) {}
    }
}
