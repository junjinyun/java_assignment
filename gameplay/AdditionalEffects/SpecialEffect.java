package gameplay.AdditionalEffects;

public class SpecialEffect implements Effect {
    private String name;  // 상태 이름 (예: "마킹", "반격", "도발")
    private int duration; // 지속 시간

    public SpecialEffect(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(Object target) {
        // 실제로 효과를 적용하지 않음
        // 이름만을 기준으로 다른 코드에서 상태를 체크할 수 있도록 함
    }

    @Override
    public void onTurnEnd(Object target) {
        duration--;
    }

    @Override
    public boolean isExpired() {
        return duration <= 0;
    }

	@Override
	public int getPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
