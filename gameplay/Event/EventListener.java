package gameplay.Event;

public interface EventListener {
    void onEvent(String eventType, Object data);
}
