package gameplay;

import dungeon.Ally;
import java.util.ArrayList;
import java.util.List;

// 아군 상태를 관리하는 클래스
public class AllyStatusManager {
	private Ally baseStats;
	private List<StatusEffect> statusEffects;
	private int position;

	public AllyStatusManager(Ally ally, int position) {
		this.baseStats = ally;
		this.statusEffects = new ArrayList<>();
		this.position = position;
	}

	// 상태이상 추가: 중복이면 위력은 합산, 지속시간은 더 긴 쪽
	public void addStatusEffect(StatusEffect newEffect) {
		for (StatusEffect effect : statusEffects) {
			if (effect.getName().equals(newEffect.getName())) {
				effect.setPower(effect.getPower() + newEffect.getPower());
				effect.setDuration(Math.max(effect.getDuration(), newEffect.getDuration()));
				return;
			}
		}
		statusEffects.add(newEffect);
	}

	// 상태이상 효과 적용: 체력 감소 + 지속시간 감소 + 제거
	public void applyStatusEffects() {
		List<StatusEffect> expired = new ArrayList<>();

		for (StatusEffect effect : statusEffects) {
			// 체력 감소
			int currentHp = baseStats.getHealth();
			baseStats.setHealth(currentHp - effect.getPower());

			// 지속시간 감소
			effect.setDuration(effect.getDuration() - 1);

			// 지속시간 만료
			if (effect.getDuration() <= 0) {
				expired.add(effect);
			}
		}

		statusEffects.removeAll(expired);
	}

	public List<StatusEffect> getStatusEffects() {
		return statusEffects;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Ally getBaseStats() {
		return baseStats;
	}

	public String getName() {
		return baseStats.getName();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("위치 ").append(position).append(" - ").append(getName()).append("\n");
		sb.append("체력: ").append(baseStats.getHealth()).append("/").append(baseStats.getMaxHealth()).append("\n");
		sb.append("상태이상: ").append(statusEffects.isEmpty() ? "없음" : "\n");
		for (StatusEffect effect : statusEffects) {
			sb.append("  - ").append(effect.toString()).append("\n");
		}
		return sb.toString();
	}
}