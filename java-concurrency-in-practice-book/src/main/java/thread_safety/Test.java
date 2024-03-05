package thread_safety;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
//        Thread t1 = new Thread(() -> test.test1());
//        Thread t2 = new Thread(() -> test.test2());
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
        test.cachedFactorizer();
    }

    private synchronized void test1() {

        System.out.println("Starting Thread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Ending Thread: " + Thread.currentThread().getName());

    }


    private synchronized void test2() {

        System.out.println("Starting Thread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Ending Thread: " + Thread.currentThread().getName());

    }

    public void cachedFactorizer() throws InterruptedException {
        CachedFactorizer cachedFactorizer = new CachedFactorizer();
        Thread getFactorsThread = new Thread(() -> System.out.println("Factors: " + cachedFactorizer.getFactors(20)));
        Thread getFactorsThread2 = new Thread(getFactorsThread);
        Thread getCacheHitRatio = new Thread(() -> System.out.println("Cache hit ratio: " + cachedFactorizer.getCacheHitRatio()));
        getFactorsThread.start();
        getFactorsThread2.start();
        Thread.sleep(1000);
        getCacheHitRatio.start();
        getFactorsThread.join();
        getFactorsThread2.join();
        getCacheHitRatio.join();
    }
}
