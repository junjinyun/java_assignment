package gameplay.Party;

import loaddata.Enemy;
import loaddata.EnemySkills;

import java.util.ArrayList;
import java.util.List;

// 적 캐릭터의 상태 및 상태이상을 관리하는 클래스
public class EnemyStatusManager {
    private Enemy baseStats; // 기본 스탯 (EnemyFactory에서 불러온 원본 정보)
    private List<StatusEffect241> statusEffects; // 현재 적용 중인 상태이상 목록
    private int position; // 적 위치 (0~3 등)
    private int currentSpeed; // 전투 중 동적인 현재 속도
    private int actionOrder; // 행동 순서 (우선순위)
    private EnemySkills skillList; //캐릭터가 가지고 있는 스킬을 저장하는 리스트

    public EnemyStatusManager(Enemy enemy, int position) {
        this.baseStats = enemy;
        this.statusEffects = new ArrayList<>();
        this.position = position;
    }

    // 상태이상 추가: 이름이 같으면 위력은 합산하고, 지속시간은 더 긴 쪽을 사용
    public void addStatusEffect(StatusEffect241 newEffect) {
        // 동일한 상태이상이 있는지 체크
        for (StatusEffect241 effect : statusEffects) {
            if (effect.getName().equals(newEffect.getName())) {
                // 중복된 상태이상이 있을 경우, 위력 합산 및 지속시간 갱신
                effect.setPower(effect.getPower() + newEffect.getPower());
                effect.setDuration(Math.max(effect.getDuration(), newEffect.getDuration()));
                return;
            }
        }
        // 상태이상이 없으면 새 상태이상 추가
        statusEffects.add(newEffect);
    }

    // 상태이상 효과 적용: 체력 감소 및 지속시간 감소, 만료된 상태이상 제거
    public void applyStatusEffects() {
        List<StatusEffect241> expired = new ArrayList<>();
        for (StatusEffect241 effect : statusEffects) {
            // 상태이상의 위력만큼 체력 감소
            int currentHp = baseStats.getHealth();
            baseStats.setHealth(Math.max(0, currentHp - effect.getPower())); // 체력 0 이하 방지

            // 지속시간 감소
            effect.setDuration(effect.getDuration() - 1);

            // 만료 상태이상 제거 대상에 추가
            if (effect.getDuration() <= 0) {
                expired.add(effect);
            }
        }
        // 만료된 상태이상은 리스트에서 제거
        statusEffects.removeAll(expired);
    }

    // Getter 및 Setter
    public List<StatusEffect241> getStatusEffects() {
        return statusEffects;
    }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("위치 ").append(position).append(" - ").append(getName()).append("\n");
        sb.append("체력: ").append(baseStats.getHealth()).append("/").append(baseStats.getMaxHealth()).append("\n");
        sb.append("속도: ").append(currentSpeed).append(" / 행동순서: ").append(actionOrder).append("\n");
        sb.append("상태이상: ").append(statusEffects.isEmpty() ? "없음" : "\n");
        for (StatusEffect241 effect : statusEffects) {
            sb.append("  - ").append(effect.toString()).append("\n");
        }
        return sb.toString();
    }
}