package gameplay.AdditionalEffects;

import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;

import java.util.*;

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

	@Override
	public void apply(Object target) {
		if (applied) return;

		for (String stat : extractStatNames()) {
			applyToStat(target, stat, true);
		}
		applied = true;
	}

	@Override
	public void onTurnEnd(Object target) {
	    if (applied) {
	        duration--;
	        if (isExpired()) {
	            for (String stat : extractStatNames()) {
	                applyToStat(target, stat, false);
	            }
	        }
	    }
	}


	private List<String> extractStatNames() {
		List<String> stats = new ArrayList<>();
		if (name.contains("공")) stats.add("damage");
		if (name.contains("방")) stats.add("defense");
		if (name.contains("회피")) stats.add("evasion");
		if (name.contains("속도")) stats.add("speed");
		return stats;
	}

	private void applyToStat(Object target, String stat, boolean apply) {
		double multiplier = 1 + (isDebuff() ? -power : power) / 100.0;

		if (apply) {
			if (target instanceof AllyStatusManager ally) {
				switch (stat) {
					case "damage" -> {
						int original = ally.getBaseStats().getAttack();
						originalStatValues.putIfAbsent("damage", original);
						ally.getBaseStats().setAttack((int) (original * multiplier));
					}
					case "defense" -> {
						int original = ally.getBaseStats().getDefense();
						originalStatValues.putIfAbsent("defense", original);
						ally.getBaseStats().setDefense((int) (original * multiplier));
					}
					case "evasion" -> {
						int original = ally.getBaseStats().getEvasion();
						originalStatValues.putIfAbsent("evasion", original);
						ally.getBaseStats().setEvasion((int) (original * multiplier));
					}
					case "speed" -> {
						int original = ally.getCurrentSpeed();
						originalStatValues.putIfAbsent("speed", original);
						ally.setCurrentSpeed((int) (original * multiplier));
					}
				}
			} else if (target instanceof EnemyStatusManager enemy) {
				switch (stat) {
					case "damage" -> {
						int original = enemy.getBaseStats().getAttack();
						originalStatValues.putIfAbsent("damage", original);
						enemy.getBaseStats().setAttack((int) (original * multiplier));
					}
					case "defense" -> {
						int original = enemy.getBaseStats().getDefense();
						originalStatValues.putIfAbsent("defense", original);
						enemy.getBaseStats().setDefense((int) (original * multiplier));
					}
					case "evasion" -> {
						int original = enemy.getBaseStats().getEvasion();
						originalStatValues.putIfAbsent("evasion", original);
						enemy.getBaseStats().setEvasion((int) (original * multiplier));
					}
					case "speed" -> {
						int original = enemy.getCurrentSpeed();
						originalStatValues.putIfAbsent("speed", original);
						enemy.setCurrentSpeed((int) (original * multiplier));
					}
				}
			}
		} else {
			Integer original = originalStatValues.get(stat);
			if (original == null) return;

			if (target instanceof AllyStatusManager ally) {
				switch (stat) {
					case "damage" -> ally.getBaseStats().setAttack(original);
					case "defense" -> ally.getBaseStats().setDefense(original);
					case "evasion" -> ally.getBaseStats().setEvasion(original);
					case "speed" -> ally.setCurrentSpeed(original);
				}
			} else if (target instanceof EnemyStatusManager enemy) {
				switch (stat) {
					case "damage" -> enemy.getBaseStats().setAttack(original);
					case "defense" -> enemy.getBaseStats().setDefense(original);
					case "evasion" -> enemy.getBaseStats().setEvasion(original);
					case "speed" -> enemy.setCurrentSpeed(original);
				}
			}
		}
	}

	public void forceRemove(Object target) {
		for (String stat : extractStatNames()) {
			applyToStat(target, stat, false);
		}
	}

	public boolean canMerge(StatModifierEffect other) {
		return this.name.equals(other.name);
	}

	public void merge(StatModifierEffect other, Object target) {
		boolean reapplied = false;

		if (other.power > this.power) {
			applyToAllStats(target, false);
			this.power = other.power;
			this.applied = false;
			reapplied = true;
		}

		if (other.duration > this.duration) {
			this.duration = other.duration;
		}

		if (reapplied) {
			this.apply(target);
		}
	}

	private void applyToAllStats(Object target, boolean apply) {
		for (String stat : extractStatNames()) {
			applyToStat(target, stat, apply);
		}
	}

	@Override
	public boolean isExpired() {
		return duration <= 0;
	}

	public boolean isDebuff() {
		return name.contains("감소");
	}

	public String getStatName() {
		return name.replaceAll("[증가|감소]", "").trim();
	}

	public String getEffectType() {
		return name.contains("증가") ? "증가" : name.contains("감소") ? "감소" : "";
	}
}
