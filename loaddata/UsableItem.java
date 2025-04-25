package loaddata;

public class UsableItem {
    private String Name;
    private String Information;
    private int Strength;
    private int ApplyChance;
    private int Price;
    private int MaxNumber;
    private int MinDropAmount;
    private int MaxDropAmount;
    private String Type;
    private String AEffect;
    private int AEffectStrength;
    private int AEffectDuration;

    // 기본 생성자
    public UsableItem() {}

    // 매개변수가 있는 생성자
    public UsableItem(String name, String information, int strength, int applyChance, int price, int maxNumber, 
                      int minDropAmount, int maxDropAmount, String type, String aEffect) {
        this.Name = name;
        this.Information = information;
        this.Strength = strength;
        this.ApplyChance = applyChance;
        this.Price = price;
        this.MaxNumber = maxNumber;
        this.MinDropAmount = minDropAmount;
        this.MaxDropAmount = maxDropAmount;
        this.Type = type;

        // 'AEffect'를 쉼표로 나누어 각각의 요소를 처리합니다. (형식: "effect, strength, duration")
        String[] aEffectPart = aEffect.split(", ");
        if (aEffectPart.length == 3) {
            this.AEffect = aEffectPart[0];
            try {
                this.AEffectStrength = Integer.parseInt(aEffectPart[1]);
                this.AEffectDuration = Integer.parseInt(aEffectPart[2]);
            } catch (NumberFormatException e) {
                // strength나 duration이 정수로 변환되지 않는 경우 처리
                this.AEffectStrength = 0;
                this.AEffectDuration = 0;
            }
        } else {
            // 잘못된 형식일 경우 처리
            this.AEffect = "";
            this.AEffectStrength = 0;
            this.AEffectDuration = 0;
        }
    }

    // Getter 메서드들
    public String getName() {
        return Name;
    }

    public String getInformation() {
        return Information;
    }

    public int getStrength() {
        return Strength;
    }

    public int getApplyChance() {
        return ApplyChance;
    }

    public int getPrice() {
        return Price;
    }

    public int getMaxNumber() {
        return MaxNumber;
    }

    public int getMinDropAmount() {
        return MinDropAmount;
    }

    public int getMaxDropAmount() {
        return MaxDropAmount;
    }

    public String getType() {
        return Type;
    }

    public String getAEffect() {
        return AEffect;
    }

    public int getAEffectStrength() {
        return AEffectStrength;
    }

    public int getAEffectDuration() {
        return AEffectDuration;
    }
}