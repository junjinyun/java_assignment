package gameplay.AdditionalEffects;

import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;

public class StatusEffect implements Effect {
	private String name;
	private int power;
	private int duration;

	public StatusEffect(String name, int power, int duration) {
		this.name = name;
		this.power = Math.abs(power);
		this.setDuration(duration);
	}

	@Override
	public void onTurnEnd(Object target) {
		if (target instanceof AllyStatusManager ally) {
			applyEffectToTarget(ally);
		} else if (target instanceof EnemyStatusManager enemy) {
			applyEffectToTarget(enemy);
		}
		setDuration(getDuration() - 1);
	}

	private void applyEffectToTarget(AllyStatusManager target) {
		if (isRecoveryEffect()) {
			int hp = target.getBaseStats().getHealth();
			target.getBaseStats().setHealth(Math.min(target.getBaseStats().getMaxHealth(), hp + power));
		} else {
			int hp = target.getBaseStats().getHealth();
			target.getBaseStats().setHealth(Math.max(0, hp - power));
		}
	}

	private void applyEffectToTarget(EnemyStatusManager target) {
		if (isRecoveryEffect()) {
			int hp = target.getBaseStats().getHealth();
			target.getBaseStats().setHealth(Math.min(target.getBaseStats().getMaxHealth(), hp + power));
		} else {
			int hp = target.getBaseStats().getHealth();
			target.getBaseStats().setHealth(Math.max(0, hp - power));
		}
	}

	public boolean isRecoveryEffect() {
		return name.contains("회복");
	}

	@Override
	public boolean isExpired() {
		return getDuration() <= 0;
	}

	public static StatusEffect mergeEffects(StatusEffect existing, StatusEffect incoming) {
		if (existing.getName().equalsIgnoreCase(incoming.getName())) {
			int combinedPower = existing.getPower() + incoming.getPower();
			int maxDuration = Math.max(existing.getDuration(), incoming.getDuration());
			return new StatusEffect(existing.getName(), combinedPower, maxDuration);
		}
		return null;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
    @Override
    public String toString() {
        return name + " ( 위력 : "+ power + ", " + duration + "턴)";
    }
    @Override
	public String getName() {
		return name;
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public String getType() {
		return "StatusEffect";
	}

	@Override
	public void apply(Object target) {
		// 적용 즉시 처리할 로직 없으면 비워둠
	}
}
