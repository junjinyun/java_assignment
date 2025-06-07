package gameplay;
public interface MemoryObserver {
    void onMemoryThresholdExceeded(long usedMemory, long maxMemory);
}
