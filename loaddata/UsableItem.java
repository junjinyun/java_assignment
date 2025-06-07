package loaddata;

public class UsableItem {
    private int id;
    private String name;
    private String information;
    private int strength;
    private int applyChance;
    private int price;
    private int maxNumber;
    private int minDropAmount;
    private int maxDropAmount;
    private String type;
    private String aEffect;
    private int aEffectStrength;
    private int aEffectDuration;

    // 기본 생성자
    public UsableItem() {}

    // 매개변수가 있는 생성자
    public UsableItem(int id, String name, String information, int strength, int applyChance, int price, int maxNumber, 
                      int minDropAmount, int maxDropAmount, String type, String aEffect) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.strength = strength;
        this.applyChance = applyChance;
        this.price = price;
        this.maxNumber = maxNumber;
        this.minDropAmount = minDropAmount;
        this.maxDropAmount = maxDropAmount;
        this.type = type;

        // 'aEffect'를 쉼표로 나누어 각각의 요소를 처리합니다. (형식: "effect, strength, duration")
        String[] aEffectPart = aEffect.split(", ");
        if (aEffectPart.length == 3) {
            this.aEffect = aEffectPart[0];
            try {
                this.aEffectStrength = Integer.parseInt(aEffectPart[1]);
                this.aEffectDuration = Integer.parseInt(aEffectPart[2]);
            } catch (NumberFormatException e) {
                // strength 나 duration 이 정수로 변환되지 않는 경우 처리
                this.aEffectStrength = 0;
                this.aEffectDuration = 0;
            }
        } else {
            // 잘못된 형식일 경우 처리
            this.aEffect = "";
            this.aEffectStrength = 0;
            this.aEffectDuration = 0;
        }
    }

    // Getter 메서드들
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public int getStrength() {
        return strength;
    }

    public int getApplyChance() {
        return applyChance;
    }

    public int getPrice() {
        return price;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int getMinDropAmount() {
        return minDropAmount;
    }

    public int getMaxDropAmount() {
        return maxDropAmount;
    }

    public String getType() {
        return type;
    }

    public String getAEffect() {
        return aEffect;
    }

    public int getAEffectStrength() {
        return aEffectStrength;
    }

    public int getAEffectDuration() {
        return aEffectDuration;
    }
}
