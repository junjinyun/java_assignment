package dungeon;

public class UsableItem {
    private String name;
    private String information;
    private int strength;
    private int applyChance;
    private int price;
    private int max_number;
    private int mindrop_amount;
    private int maxdrop_amount;
    private String type;
    private String a_effect;
    private int a_effect_strength;
    private int a_effect_duration;

    // 기본 생성자
    public UsableItem() {}

    // 매개변수가 있는 생성자
    public UsableItem(String name, String information, int strength, int applyChance, int price, int max_number, 
                      int mindrop_amount, int maxdrop_amount, String type, String a_effect) {
        this.name = name;
        this.information = information;
        this.strength = strength;
        this.applyChance = applyChance;
        this.price = price;
        this.max_number = max_number;
        this.mindrop_amount = mindrop_amount;
        this.maxdrop_amount = maxdrop_amount;
        this.type = type;

        // 'a_effect'를 쉼표로 나누어 각각의 요소를 처리합니다. (형식: "effect, strength, duration")
        String[] a_effect_part = a_effect.split(", ");
        if (a_effect_part.length == 3) {
            this.a_effect = a_effect_part[0];
            try {
                this.a_effect_strength = Integer.parseInt(a_effect_part[1]);
                this.a_effect_duration = Integer.parseInt(a_effect_part[2]);
            } catch (NumberFormatException e) {
                // strength나 duration이 정수로 변환되지 않는 경우 처리
                this.a_effect_strength = 0;
                this.a_effect_duration = 0;
            }
        } else {
            // 잘못된 형식일 경우 처리
            this.a_effect = "";
            this.a_effect_strength = 0;
            this.a_effect_duration = 0;
        }
    }

    // Getter 메서드들
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
        return max_number;
    }

    public int getMindropAmount() {
        return mindrop_amount;
    }

    public int getMaxdropAmount() {
        return maxdrop_amount;
    }

    public String getType() {
        return type;
    }

    public String getAEffect() {
        return a_effect;
    }

    public int getAEffectStrength() {
        return a_effect_strength;
    }

    public int getAEffectDuration() {
        return a_effect_duration;
    }
}