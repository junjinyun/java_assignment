package loaddata;

/* 적 스킬 정보 클래스 */

public class EnemySkill {
    private int Id; // 스킬 ID
    private String Name; // 스킬 이름
    private int damageMultiplier; // 피해 배율 (%)
    private String AEffect; // 추가 효과 (ex. 출혈 3 2, 후퇴 2)
    private String skillActivationZone; // 스킬 사용 가능 위치 (ex. "1 2")
    private String targetLocation; // 타겟 위치 (ex. "1 2 3")
    private String OwnerType; // 스킬 소유자 타입 (ex. 인간형, 짐승형 등)

    public EnemySkill() {}

    public EnemySkill(int id, String name, int damageMultiplier, String aEffect,
                      String skillActivationZone, String targetLocation, String ownerType) {
        this.Id = id;
        this.Name = name;
        this.damageMultiplier = damageMultiplier;
        this.AEffect = aEffect;
        this.skillActivationZone = skillActivationZone;
        this.targetLocation = targetLocation;
        this.OwnerType = ownerType;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public int getDamageMultiplier() {
        return damageMultiplier;
    }

    public String getAEffect() {
        return AEffect;
    }

    public String getSkillActivationZone() {
        return skillActivationZone;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public String getOwnerType() {
        return OwnerType;
    }
}