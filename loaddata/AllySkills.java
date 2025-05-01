package loaddata;

/* 아군 스킬 정보 클래스 */

public class AllySkills {
    private String Name; // 스킬 이름
    private String Information; //해당 스킬의 로어(스킬 이미지에 대한 부가 설명)
    private String damageMultiplier; // 기술의 피해량 배율(사용자 공격력 * damageMultiplier = 최종 피해량)
    private String AEffect; // 기술의 부가효과(출혈, 부상, 스텟감소 등)
    private String DamageGrowth; // 피해 성장치(추후에 레벨과 함께 사용하여 최종 )
    private String skillActivationZone; // 기술을 사용 가능한 위치
    private String targetLocation; // 기술이 공격 가능한 위치
    private String Owner; // 해당 기술의 사용자
    private boolean IsAoE; // 해당 기술이 광역기술인지 단일 기술인지
    private String pictureloc; // 해당 기술의 이미지 파일의 주소

    public AllySkills() {
    }

    public AllySkills(String name, String Information, String damageMultiplier, String aEffect,
                     String damageGrowth, String skillActivationZone, String targetLocation, String owner, boolean IsAoE) {
        this.Name = name;
        this.Information = Information;
        this.damageMultiplier = damageMultiplier;
        this.AEffect = aEffect;
        this.DamageGrowth = damageGrowth;
        this.skillActivationZone = skillActivationZone;
        this.targetLocation = targetLocation;
        this.Owner = owner;
        this.IsAoE = IsAoE;
    }

    public String getName() {
        return Name;
    }

    public String getInformation(){
        return Information;
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
    
    public boolean getIsAoE() {
        return IsAoE;
    }

    public String pictureloc(){
        return pictureloc;
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

    public void setIsAoE(boolean IsAoE) {
        this.IsAoE = IsAoE;
    }
    
}