package gameplay.Party;

import loaddata.Enemy;
import loaddata.EnemySkills;
import loaddata.SkillManager;
import gameplay.AdditionalEffects.StatusEffect;
import java.util.ArrayList;
import java.util.List;

public class EnemyStatusManager {
    private Enemy baseStats;
    private List<StatusEffect> statusEffects;
    private int position;
    private int currentSpeed;
    private int actionOrder;
    private String mappingId;
    private List<EnemySkills> skillList;

    public EnemyStatusManager(Enemy enemy, String mappingId) {
        this.baseStats = enemy;
        this.statusEffects = new ArrayList<>();
        this.position = Integer.parseInt(mappingId.replaceAll("[^0-9]", ""));
        this.mappingId = mappingId;
        this.skillList = new ArrayList<>();

        // 적의 카테고리와 mobId로 스킬을 로드
        loadSkillsForEnemy();
    }

    // 적의 카테고리와 mobId로 스킬을 로드하는 메서드
    private void loadSkillsForEnemy() {
        List<EnemySkills> skills = SkillManager.loadUsableEnemySkillsByType(baseStats.getCategory(), baseStats.getId());
        
        for (EnemySkills skill : skills) {
            addSkill(skill); // 로드된 스킬 추가
        }
    }

    // 스킬 추가 메서드
    public void addSkill(EnemySkills newSkill) {
        for (EnemySkills existingSkill : skillList) {
            if (existingSkill.getName().equalsIgnoreCase(newSkill.getName())) {
                return; // 중복된 스킬은 추가하지 않음
            }
        }
        skillList.add(newSkill); // 새로운 스킬 추가
    }

    // Getter 및 Setter들...

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
