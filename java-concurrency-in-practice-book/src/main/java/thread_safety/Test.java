package thread_safety;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        CachedFactorizer cachedFactorizer = new CachedFactorizer();
        Thread getFactorsThread = new Thread(()-> System.out.println("Factors: "+cachedFactorizer.getFactors(20)));
        Thread getFactorsThread2 = new Thread(getFactorsThread);
        Thread getCacheHitRatio = new Thread(()-> System.out.println("Cache hit ratio: "+cachedFactorizer.getCacheHitRatio()));
        getFactorsThread.start();
        getFactorsThread2.start();
        Thread.sleep(3000);
        getCacheHitRatio.start();
        getFactorsThread.join();
        getFactorsThread2.join();
        getCacheHitRatio.join();
    }
}
