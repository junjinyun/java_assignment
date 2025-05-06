package gameplay.AdditionalEffects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameplay.Party.AllyStatusManager;

public class StatModifierEffect implements Effect {
	private String name;
	private int power;
	private int duration;
	private boolean applied = false;
	private Map<String, Integer> originalStatValues = new HashMap<>();

	public StatModifierEffect(String name, int power, int duration) {
		this.name = name;
		this.power = power;
		this.duration = duration;
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
		return "StatModifier";
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	private List<String> extractStatNames() {
		List<String> stats = new ArrayList<>();
		if (name.contains("공"))
			stats.add("damage");
		if (name.contains("방"))
			stats.add("defense");
		if (name.contains("회피"))
			stats.add("evasion");
		if (name.contains("속도"))
			stats.add("speed");
		return stats;
	}

	@Override
	public void apply(AllyStatusManager target) {
		if (applied)
			return;

		List<String> statNames = extractStatNames();
		// 본 효과 적용
		for (String stat : statNames) {
			applyToStat(target, stat, true);
		}

		applied = true;
	}

	public boolean canMerge(StatModifierEffect other) {
		return this.name.equals(other.name);
	}

	public void merge(StatModifierEffect other, AllyStatusManager target) {
	    boolean reapplied = false;

	    if (other.power > this.power) {
	        // 기존 효과 제거 (원래 스탯으로 복원)
	        applyToAllStats(target, false);

	        // power 업데이트 및 재적용 플래그 설정
	        this.power = other.power;
	        this.applied = false;
	        reapplied = true;
	    }

	    if (other.duration > this.duration) {
	        this.duration = other.duration;
	    }

	    if (reapplied) {
	        // 더 강한 효과를 다시 적용
	        this.apply(target);
	    }
	}
	private void applyToAllStats(AllyStatusManager target, boolean apply) {
	    for (String stat : extractStatNames()) {
	        applyToStat(target, stat, apply);
	    }
	}
	@Override
	public void onTurnEnd(AllyStatusManager target) {
		duration--;
		if (isExpired()) {
			for (String stat : extractStatNames()) {
				applyToStat(target, stat, false);
			}
		}
	}

	private void applyToStat(AllyStatusManager target, String stat, boolean apply) {
		if (apply) {
			double multiplier = 1 + (isDebuff() ? -power : power) / 100.0;

			switch (stat) {
			case "damage" -> {
				int original = target.getBaseStats().getAttack();
				originalStatValues.put("damage", original);
				target.getBaseStats().setAttack((int) (original * multiplier));
			}
			case "defense" -> {
				int original = target.getBaseStats().getDefense();
				originalStatValues.put("defense", original);
				target.getBaseStats().setDefense((int) (original * multiplier));
			}
			case "evasion" -> {
				int original = target.getBaseStats().getEvasion();
				originalStatValues.put("evasion", original);
				target.getBaseStats().setEvasion((int) (original * multiplier));
			}
			case "speed" -> {
				int original = target.getCurrentSpeed();
				originalStatValues.put("speed", original);
				target.setCurrentSpeed((int) (original * multiplier));
			}
			}
		} else {
			Integer original = originalStatValues.get(stat);
			if (original != null) {
				switch (stat) {
				case "damage" -> target.getBaseStats().setAttack(original);
				case "defense" -> target.getBaseStats().setDefense(original);
				case "evasion" -> target.getBaseStats().setEvasion(original);
				case "speed" -> target.setCurrentSpeed(original);
				}
			}
		}
	}

	@Override
	public boolean isExpired() {
		return duration <= 0;
	}

	public void setApplied(boolean applied) {
		this.applied = applied;
	}

	public boolean isDebuff() {
		return name.contains("감소");
	}

	// 이름에서 스탯을 추출하는 메서드
	private String extractStatName(String effectName) {
		// "방어증가"에서 "방어"와 같은 스탯 이름만 추출
		return effectName.replaceAll("[증가|감소]", "").trim();
	}

	// 이름에서 효과 타입을 추출하는 메서드
	private String extractEffectType(String effectName) {
		// "증가"와 "감소"만 추출
		if (effectName.contains("증가")) {
			return "증가";
		} else if (effectName.contains("감소")) {
			return "감소";
		}
		return "";
	}

	// "방어증가"에서 "방어" 반환
	public String getStatName() {
		return extractStatName(this.name);
	}

	// "방어증가"에서 "증가" 반환
	public String getEffectType() {
		return extractEffectType(this.name);
	}

	public void forceRemove(AllyStatusManager target) {
		// 원래 값으로 복원
		for (String stat : extractStatNames()) {
			applyToStat(target, stat, false);
		}
	}
}
