package thread_safety;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@ThreadSafe
public class CachedFactorizer {
    private int hits;
    private int cachedHits;
    private int lastNumber;
    private List<Integer> lastFactors;

    public synchronized int getHits() {
        return hits;
    }

    public synchronized double getCacheHitRatio() { // if same lock (this) is used in getFactors(), read will be blocked if some thread has aquired the lock for writing
        return (double) cachedHits / (double) hits;
    }

    public List<Integer> getFactors(int number) {
        synchronized (this) {
            try {
                Thread.sleep(2000);
                System.out.println("Waited for 2 seconds");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        List<Integer> factors = null;
        synchronized (this) {
            ++hits;
            if (number == lastNumber) {
                ++cachedHits;
                factors = this.lastFactors;
            }
        }

        if (factors == null) {
            factors = calculateFactors(number);
            synchronized (this) {
                this.lastNumber = number;
                this.lastFactors = factors;
            }
        }
        return factors;

    }

    private List<Integer> calculateFactors(int number) {
        Set<Integer> factors = new HashSet<>();
        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                factors.add(i);
            }
        }
        return new ArrayList<>(factors);
    }
}
