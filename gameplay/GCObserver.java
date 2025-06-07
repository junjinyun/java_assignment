package gameplay;
public class GCObserver implements MemoryObserver {
    @Override
    public void onMemoryThresholdExceeded(long usedMemory, long maxMemory) {
        System.out.println("메모리 사용량 초과: " + usedMemory + "/" + maxMemory + " → GC 실행");
        System.gc();
    }
}
