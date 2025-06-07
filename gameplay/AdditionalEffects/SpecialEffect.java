package gameplay.AdditionalEffects;

public class SpecialEffect implements Effect{
    protected String name;
    protected int duration;  // 지속 턴 수

    public SpecialEffect(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // 아군 혹은 적 모두 적용 가능하도록 Object 파라미터로 유지
    public void apply(Object target) {
	}

    // 효과 해제 시 처리
    public void forceRemove(Object target) {
	}

    // 매 턴 종료 시 처리
    public void onTurnEnd(Object target) {
        duration--;
        if (duration > 0) {
            apply(target);
        }
    }

    public boolean isExpired() {
        return duration <= 0;
    }

    public boolean canMerge(SpecialEffect other) {
        return this.name.equals(other.name);
    }

    public void merge(SpecialEffect other, Object target) {
        this.duration = Math.max(this.duration, other.duration);
    }

    @Override
    public String toString() {
        return name + " (" + duration + "턴)";
    }

	@Override
	public int getPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType() {
		return "SpecialEffect";
	}
}
