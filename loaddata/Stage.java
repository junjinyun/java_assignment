package loaddata;

public class Stage {
    private int id;
    private String stageName;
    private String enemyType;
    private String bgImagePath; // 수정된 변수명
    private String powerGrowth;
    private String bosstype;

    public Stage() {}

    public Stage(int id, String stageName, String enemyType, String bgImagePath, String powerGrowth, String bosstype) {
        this.id = id;
        this.stageName = stageName;
        this.enemyType = enemyType;
        this.bgImagePath = bgImagePath; // 수정된 변수명
        this.powerGrowth = powerGrowth;
        this.bosstype = bosstype;
    }

    public int getId() {
        return id;
    }

    public String getStageName() {
        return stageName;
    }

    public String getEnemyType() {
        return enemyType;
    }

    public String getBgImagePath() {
        return bgImagePath; // 수정된 변수명
    }

    public String getPowerGrowth() {
        return powerGrowth;
    }

    public String getBosstype() {
        return bosstype;
    }

    public void setBgImagePath(String bgImagePath) {
        this.bgImagePath = bgImagePath; // 수정된 변수명
    }
}
