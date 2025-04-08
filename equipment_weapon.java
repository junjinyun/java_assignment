package dungeon;

public class equipment_weapon(){
    private String name;
    private int attack;
    private int attack_growth;
    private int evadereduction_growth;
    private int level;
    private int max_level;
    private String owner;

} 
public UsableItem() {}
    public UsableItem(String name, int attack, int attack_growth, int evadereduction_growth, String owner) {
        
        this.name = name;
        this.attack;
        this.attack_growth;
        this.evadereduction_growth;
        this.level = level;
        this.max_level = max_level;
        this.owner = owner;
    }
        public int get_name() {
        return name;
        }
        public int get_attack() {
        return attack;
        }
        public int get_attack_growth() {
        return attack_growth;
        }
        public int get_evadereduction_growth() {
        return evadereduction_growth;
        }
        public int get_level() {
        return level;
    }
        public int get_max_level() {
        return max_level;
    }
        public int get_owner() {
        return owner;
    }