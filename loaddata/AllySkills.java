package loaddata;

/* 아군 스킬 정보 클래스 */

public class AllySkills {
    private String name; // 스킬 이름
    private String information; // 해당 스킬의 로어(스킬 이미지에 대한 부가 설명)
    private String damageMultiplier; // 기술의 피해량 배율(사용자 공격력 * damageMultiplier = 최종 피해량)
    private String aEffect; // 기술의 부가효과(출혈, 부상, 스텟감소 등)
    private String damageGrowth; // 피해 성장치(추후에 레벨과 함께 사용하여 최종)
    private String skillActivationZone; // 기술을 사용 가능한 위치
    private String targetLocation; // 기술이 공격 가능한 위치
    private String owner; // 해당 기술의 사용자
    private boolean isAoE; // 해당 기술이 광역기술인지 단일 기술인지
    private String pictureLoc; // 해당 기술의 이미지 파일의 주소
    private String target; // 대상 타입 (예: 적군, 아군 등)

    public AllySkills() {
    }

    public AllySkills(String name, String information, String damageMultiplier, String aEffect,
                      String damageGrowth, String skillActivationZone, String targetLocation,
                      String owner, boolean isAoE, String target) {
        this.name = name;
        this.information = information;
        this.damageMultiplier = damageMultiplier;
        this.aEffect = aEffect;
        this.damageGrowth = damageGrowth;
        this.skillActivationZone = skillActivationZone;
        this.targetLocation = targetLocation;
        this.owner = owner;
        this.isAoE = isAoE;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public String getDamageMultiplier() {
        return damageMultiplier;
    }

    public String getAEffect() {
        return aEffect;
    }

    public String getDamageGrowth() {
        return damageGrowth;
    }

    public String getSkillActivationZone() {
        return skillActivationZone;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isAoE() {
        return isAoE;
    }

    public String getPictureLoc() {
        return pictureLoc;
    }

    public String getTarget() {
        return target;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setDamageMultiplier(String damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public void setAEffect(String aEffect) {
        this.aEffect = aEffect;
    }

    public void setDamageGrowth(String damageGrowth) {
        this.damageGrowth = damageGrowth;
    }

    public void setSkillActivationZone(String skillActivationZone) {
        this.skillActivationZone = skillActivationZone;
    }

    public void setTargetLocation(String targetLocation) {
        this.targetLocation = targetLocation;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAoE(boolean isAoE) {
        this.isAoE = isAoE;
    }

    public void setPictureLoc(String pictureLoc) {
        this.pictureLoc = pictureLoc;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
