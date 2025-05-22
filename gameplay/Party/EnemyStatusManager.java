package gameplay.Party;

import loaddata.Enemy;
import loaddata.EnemySkills;
import loaddata.SkillManager;
import gameplay.AdditionalEffects.StatusEffect;
import gameplay.AdditionalEffects.StatModifierEffect;
import gameplay.AdditionalEffects.Effect;

import java.util.ArrayList;
import java.util.List;

public class EnemyStatusManager {
    private Enemy baseStats;
    private List<StatusEffect> statusEffects;
    private List<StatModifierEffect> statModifierEffects;
    private int position;
    private int currentSpeed;
    private int actionOrder;
    private String mappingId;
    private List<EnemySkills> skillList;

    public EnemyStatusManager(Enemy enemy, String mappingId) {
        this.baseStats = enemy;
        this.statusEffects = new ArrayList<>();
        this.statModifierEffects = new ArrayList<>();
        this.position = Integer.parseInt(mappingId.replaceAll("[^0-9]", ""));
        this.mappingId = mappingId;
        this.skillList = new ArrayList<>();

        loadSkillsForEnemy(); // 스킬 로드
    }

    private void loadSkillsForEnemy() {
        List<EnemySkills> skills = SkillManager.loadUsableEnemySkillsByType(baseStats.getCategory(), baseStats.getId());
        for (EnemySkills skill : skills) {
            addSkill(skill);
        }
    }

    public void addSkill(EnemySkills newSkill) {
        for (EnemySkills existingSkill : skillList) {
            if (existingSkill.getName().equalsIgnoreCase(newSkill.getName())) {
                return;
            }
        }
        skillList.add(newSkill);
    }

    public void addEffects(String data) {
	    String[] parts = data.split(" ");
	    if (parts.length != 3) {
	        System.out.println("잘못된 형식의 데이터입니다: " + data);
	        return;
	    }

	    String name = parts[0];
	    int power;
	    int duration;

	    try {
	        power = Integer.parseInt(parts[1]);
	        duration = Integer.parseInt(parts[2]);
	    } catch (NumberFormatException e) {
	        System.out.println("숫자 변환 실패: " + data);
	        return;
	    }

	    if (name.contains("증가") || name.contains("감소")) {
	        StatModifierEffect buff = new StatModifierEffect(name, power, duration);
	        addStatModifierEffect(buff);
	    } else {
	        StatusEffect effect = new StatusEffect(name, power, duration);
	        addStatusEffect(effect);
	    }
	}

    // 스탯 조정 효과 추가
    public void addStatModifierEffect(StatModifierEffect newEffect) {
        List<StatModifierEffect> toRemove = new ArrayList<>();

        String newStat = newEffect.getStatName();
        String newType = newEffect.getEffectType();

        for (StatModifierEffect existing : statModifierEffects) {
            if (newStat.equals(existing.getStatName()) && !newType.equals(existing.getEffectType())) {
                toRemove.add(existing);
            }
        }

        if (!toRemove.isEmpty()) {
            for (StatModifierEffect effect : toRemove) {
                effect.forceRemove(this);
                statModifierEffects.remove(effect);
            }
            return;
        }

        for (Effect e : statModifierEffects) {
            if (e instanceof StatModifierEffect existing && existing.canMerge(newEffect)) {
                existing.merge(newEffect, this);
                return;
            }
        }

        newEffect.apply(this);
        statModifierEffects.add(newEffect);
    }

    public void checkStatModifierEffectsEnd() {
        List<StatModifierEffect> expired = new ArrayList<>();
        for (StatModifierEffect effect : statModifierEffects) {
            effect.onTurnEnd(this);
            if (effect.isExpired()) {
                expired.add(effect);
            }
        }
        statModifierEffects.removeAll(expired);
    }

    public void printStatModifierEffects() {
        if (statModifierEffects.isEmpty()) {
            System.out.println("스텟 증감 효과: 없음");
        } else {
            for (StatModifierEffect effect : statModifierEffects) {
                System.out.println("스텟 증감: " + effect.getName() + ", 위력: " + effect.getPower() + ", 지속시간: " + effect.getDuration());
            }
        }
    }

    public void addStatusEffect(StatusEffect newEffect) {
        for (int i = 0; i < statusEffects.size(); i++) {
            StatusEffect existingEffect = statusEffects.get(i);
            StatusEffect mergedEffect = StatusEffect.mergeEffects(existingEffect, newEffect);
            if (mergedEffect != null) {
                statusEffects.set(i, mergedEffect);
                return;
            }
        }
        statusEffects.add(newEffect);
    }

	public void applyStatusEffects() {
	    int totalRecovery = 0;
	    int totalDamage = 0;
	    List<StatusEffect> expired = new ArrayList<>();

	    for (StatusEffect effect : statusEffects) {
	        if (effect.isRecoveryEffect()) {
	            totalRecovery += effect.getPower();
	        } else {
	            totalDamage += effect.getPower();
	        }
	        effect.setDuration(effect.getDuration() - 1);
	        if (effect.isExpired()) {
	            expired.add(effect);
	        }
	    }

	    int netEffect = totalRecovery - totalDamage;
	    int currentHp = baseStats.getHealth();
	    int maxHp = baseStats.getMaxHealth();

	    if (netEffect > 0) {
	        baseStats.setHealth(Math.min(maxHp, currentHp + netEffect));
	    } else if (netEffect < 0) {
	        baseStats.setHealth(Math.max(0, currentHp + netEffect)); // netEffect 음수이므로 더함
	    }

	    statusEffects.removeAll(expired);
	}

    public void printStatusEffects() {
        if (statusEffects.isEmpty()) {
            System.out.println("상태이상: 없음");
        } else {
            for (StatusEffect effect : statusEffects) {
                System.out.println("상태이상: " + effect.getName() + ", 위력: " + effect.getPower() + ", 지속시간: " + effect.getDuration());
            }
        }
    }

    public List<Effect> getEffects() {
        List<Effect> all = new ArrayList<>();
        all.addAll(statModifierEffects);
        all.addAll(statusEffects);
        return all;
    }

    public void removeEffect(Effect effect) {
        if (effect instanceof StatModifierEffect) {
            statModifierEffects.remove(effect);
        } else if (effect instanceof StatusEffect) {
            statusEffects.remove(effect);
        }
    }

    // Getter/Setter
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public int getActionOrder() {
        return actionOrder;
    }

    public void setActionOrder(int actionOrder) {
        this.actionOrder = actionOrder;
    }

    public Enemy getBaseStats() {
        return baseStats;
    }

    public String getName() {
        return baseStats.getName();
    }

    public int getId() {
        return baseStats.getId();
    }

    public boolean isAlive() {
        return baseStats.isAlive();
    }

    public void setAlive(boolean alive) {
        baseStats.setAlive(alive);
    }

    public boolean isAttackable() {
        return baseStats.isAttackable();
    }

    public void setAttackable(boolean isAttackable) {
        baseStats.setAttackable(isAttackable);
    }

    public String getMappingId() {
        return mappingId;
    }

    public List<EnemySkills> getSkillList() {
        return skillList;
    }

    public List<StatusEffect> getStatusEffects() {
        return statusEffects;
    }

    public List<StatModifierEffect> getStatModifierEffects() {
        return statModifierEffects;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("위치 ").append(position).append(" - ").append(getName()).append("\n");
        sb.append("체력: ").append(baseStats.getHealth()).append("/").append(baseStats.getMaxHealth()).append("\n");
        sb.append("속도: ").append(currentSpeed).append(" / 행동순서: ").append(actionOrder).append("\n");
        sb.append("상태이상: ").append(statusEffects.isEmpty() ? "없음" : "\n");
        sb.append("스킬: ").append(skillList.isEmpty() ? "없음" : "\n");

        for (EnemySkills skill : skillList) {
            sb.append("    Skill Name: ").append(skill.getName()).append("\n");
        }

        return sb.toString();
    }
}
