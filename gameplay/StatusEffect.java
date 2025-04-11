package gameplay;

public class StatusEffect {
	private String name;
	private int power;
	private int duration;

	public StatusEffect(String name, int power, int duration) {
		this.name = name;
		this.power = power;
		this.duration = duration;
	}

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