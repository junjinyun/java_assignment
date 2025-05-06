import java.util.ArrayList;
import java.util.List;

public class AllyStatusManager {
    private Ally baseStats;
    private List<StatusEffect> statusEffects;
    private List<StatModifierEffect> statModifierEffects;
    private List<SpecialEffect> specialEffects;
    private int position;
    private int currentSpeed;
    private int actionOrder;
    private List<AllySkills> skillList;
    private List<AllySkills> selectedSkills;

    public AllyStatusManager(Ally ally, int position) {
        this.baseStats = ally;
        this.position = position;
        this.statusEffects = new ArrayList<>();
        this.statModifierEffects = new ArrayList<>();
        this.specialEffects = new ArrayList<>();
        this.skillList = new ArrayList<>();
        this.selectedSkills = new ArrayList<>();
    }

    public void addStatModifierEffect(StatModifierEffect newEffect) {
        statModifierEffects.add(newEffect); // 중복 검사 제거
        newEffect.apply(this);              // 내부에서 처리
    }

    public void applyStatModifierEffects() {
        for (StatModifierEffect effect : statModifierEffects) {
            effect.onTurnEnd(this);
        }
    }

    public void removeExpiredStatModifierEffects() {
        List<StatModifierEffect> expired = new ArrayList<>();
        for (StatModifierEffect effect : statModifierEffects) {
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
        List<StatusEffect> expired = new ArrayList<>();
        for (StatusEffect effect : statusEffects) {
            effect.onTurnEnd(this);
            if (effect.isExpired()) {
                expired.add(effect);
            }
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

    public void loadSkills(AllySkills skill) {
        int index = -1;
        for (int i = 0; i < skillList.size(); i++) {
            if (skillList.get(i).getName().equalsIgnoreCase(skill.getName())) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            skillList.remove(index);
            skillList.add(index, skill);
        } else {
            skillList.add(skill);
        }
    }

    public List<AllySkills> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<AllySkills> skillList) {
        this.skillList = skillList;
    }

    public List<StatusEffect> getStatusEffects() {
        return statusEffects;
    }

    public List<StatModifierEffect> getStatModifierEffects() {
        return statModifierEffects;
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

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
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

    @Override
    public String toString() {
        return "위치: " + position + " - " + baseStats.getName();
    }
}
