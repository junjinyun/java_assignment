package dungeon;

public class UsableItem(){
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
} 
public UsableItem() {}

    public UsableItem(String name, int information, int strength, int applyChance, int price, int max_number, int mindrop_amount, int maxdrop_amount, int type, int a_effect) {
        
        String[] a_effect_part = a_effect.split(", ");
        int a_e_s = Integer.parseInt(a_effect_part[1]);
        int a_e_d = Integer.parseInt(a_effect_part[2]);
        this.name = name;
        this.information = information;
        this.strength = strength;
        this.applyChance = applyChance;
        this.price = price;
        this.max_number = max_number;
        this.mindrop_amount = mindrop_amount;
        this.maxdrop_amount = maxdrop_amount;
        this.type = type;
        this.a_effect = a_effect_part[0];
        this.a_effect_strength = a_e_s;
        this.a_effect_duration = a_e_d;
    }

        public int get_name() {
        return name;
    }
        public int get_information() {
        return information;
    }
        public int get_strength() {
        return strength;
    }
        public int get_applyChance() {
        return applyChance;
    }
        public int get_price() {
        return price;
    }
        public int get_max_number() {
        return max_number;
    }
        public int get_mindrop_amount() {
        return mindrop_amount;
    }
        public int get_maxdrop_amount() {
        return maxdrop_amount;
    }
        public int get_type() {
        return type;
    }
        public int get_a_effect() {
        return a_effect;
    }
        public int get_a_effect_strength() {
        return a_effect_strength;
    }
        public int get_a_effect_duration() {
        return a_effect_duration;
    }