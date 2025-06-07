package loaddata;

/* 적 스킬 정보 클래스 */
public class EnemySkills {
    private int id; // 스킬 ID
    private String name; // 스킬 이름
    private int damageMultiplier; // 피해 배율 (%)
    private String aEffect; // 추가 효과 (ex. 출혈 3 2, 후퇴 2)
    private String skillActivationZone; // 스킬 사용 가능 위치 (ex. "1 2")
    private String targetLocation; // 타겟 위치 (ex. "1 2 3")
    private String ownerType; // 스킬 소유자 타입 (ex. 인간형, 짐승형 등)
    private String constraint; // 스킬 사용 제약 조건 (ex. 마킹, 약화 등)
    private boolean isAoE; // 범위 공격 여부
    private String target; // 대상 유형 (자신, 아군, 적군)
    private String type; // 스킬 유형 (공격, 버프, 디버프, 상태이상) ← 추가

    public EnemySkills() {}

    public EnemySkills(int id, String name, int damageMultiplier, String aEffect,
                       String skillActivationZone, String targetLocation, String ownerType,
                       String constraint, boolean isAoE, String target, String type) {
        this.id = id;
        this.name = name;
        this.damageMultiplier = damageMultiplier;
        this.aEffect = aEffect;
        this.skillActivationZone = skillActivationZone;
        this.targetLocation = targetLocation;
        this.ownerType = ownerType;
        this.constraint = constraint;
        this.isAoE = isAoE;
        this.target = target;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDamageMultiplier() {
        return damageMultiplier;
    }

    public String getAEffect() {
        return aEffect;
    }

    public String getSkillActivationZone() {
        return skillActivationZone;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public String getConstraint() {
        return constraint;
    }

    public boolean isAoE() {
        return isAoE;
    }

    public String getTarget() {
        return target;
    }

    public String getType() {
        return type;
    }
}
