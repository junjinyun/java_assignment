package gameplay.Party;

import loaddata.Enemy;
import loaddata.EnemySkills;
import loaddata.SkillManager;
import gameplay.AdditionalEffects.StatusEffect;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
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
        loadSkillsForEnemy(enemy.getCategory(), enemy.getId()); // 적의 카테고리와 mobId로 스킬 로드
    }

    // 적의 카테고리와 mobId로 스킬을 로드하는 메소드
    private void loadSkillsForEnemy(String category, int mobId) {
        List<EnemySkills> skills = SkillManager.loadUsableEnemySkillsByType(category, mobId); // 스킬 매니저에서 스킬 로드

        if (skills != null && !skills.isEmpty()) {
            for (EnemySkills skill : skills) {
                addSkill(skill); // 로드한 스킬을 추가
            }
        } else {
            // 스킬이 없으면 캐릭터 이름을 출력
            System.out.println("No skills available for enemy: " + getName());  // 변경된 부분
        }
    }

    // 스킬을 추가하는 메소드
    public void addSkill(EnemySkills newSkill) {
        for (EnemySkills existingSkill : skillList) {
            if (existingSkill.getName().equalsIgnoreCase(newSkill.getName())) {
                return; // 중복된 스킬은 추가하지 않음
            }
        }
        skillList.add(newSkill); // 새로운 스킬 추가
    }

    // Getter 및 Setter들

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

        return sb.toString();
    }
}
