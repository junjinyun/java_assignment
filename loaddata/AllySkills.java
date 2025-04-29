package loaddata;

/* 아군 스킬 정보 클래스 */

public class AllySkills {
    private String Name; // 스킬 이름
    private String damageMultiplier; // 기술의 피해량 배율(사용자 공격력 * damageMultiplier = 최종 피해량)
    private String AEffect; // 기술의 부가효과(출혈, 부상, 스텟감소 등)
    private String DamageGrowth; // 피해 성장치(추후에 레벨과 함께 사용하여 최종 )
    private String skillActivationZone; // 기술을 사용 가능한 위치
    private String targetLocation; // 기술이 공격 가능한 위치
    private String Owner; // 해당 기술의 사용자

    public AllySkill() {
    }

    public AllySkill(String name, String damageMultiplier, String aEffect,
                     String damageGrowth, String skillActivationZone, String targetLocation, String owner) {
        this.Name = name;
        this.damageMultiplier = damageMultiplier;
        this.AEffect = aEffect;
        this.DamageGrowth = damageGrowth;
        this.skillActivationZone = skillActivationZone;
        this.targetLocation = targetLocation;
        this.Owner = owner;
    }

    public String getName() {
        return Name;
    }

    public String getDamageMultiplier() {
        return damageMultiplier;
    }

    public String getAEffect() {
        return AEffect;
    }

    public String getDamageGrowth() {
        return DamageGrowth;
    }

    public String getSkillActivationZone() {
        return skillActivationZone;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public String getOwner() {
        return Owner;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setDamageMultiplier(String damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public void setAEffect(String aEffect) {
        this.AEffect = aEffect;
    }

    public void setDamageGrowth(String damageGrowth) {
        this.DamageGrowth = damageGrowth;
    }

    public void setSkillActivationZone(String skillActivationZone) {
        this.skillActivationZone = skillActivationZone;
    }

    public void setTargetLocation(String targetLocation) {
        this.targetLocation = targetLocation;
    }

    public void setOwner(String owner) {
        this.Owner = owner;
    }
}