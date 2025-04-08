package dungeon;

public class equipment_armor(){
    private String name;
    private int defense;
    private int defense_growth;
    private int health;
    private int health_growth;
    private int evade;
    private int evade_growth;
    private int level;
    private int max_level;
    private String owner;
} 
public UsableItem() {}

    public UsableItem(String name, int defense, int defense_growth, int health, int health_growth, int evade, int evade_growth, int level, int max_level, String owner) {
        
        this.name = name;
        this.defense = defense;
        this.defense_growth = defense_growth;
        this.health = health;
        this.health_growth = health_growth;
        this.evade = evade;
        this.evade_growth = evade_growth;
        this.level = level;
        this.max_level = max_level;
        this.owner = owner;
    }
        public int get_name() {
        return name;
    }
        public int get_defense() {
        return defense;
    }
        public int get_defense_growth() {
        return defense_growth;
    }
        public int get_health() {
        return health;
    }
        public int get_health_growth() {
        return health_growth;
    }
        public int get_evade() {
        return evade;
    }
        public int get_evade_growth() {
        return evade_growth;
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