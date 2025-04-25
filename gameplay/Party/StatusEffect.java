package gameplay.Party;

public class StatusEffect {
    private String name;     // 이름 (중독, 화상 등)
    private int power;       // 위력 (적용되는 데미지량)
    private int duration;    // 지속 턴 수

    public StatusEffect(String name, int power, int duration) {
        this.name = name;
        this.power = power;
        this.duration = duration;
    }

    // Getter 및 Setter
    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getDuration() {
        return duration;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return String.format("[%s] 위력: %d / 지속: %d턴", name, power, duration);
    }
}