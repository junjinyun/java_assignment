package loaddata;

public class Stage {
    private int id;
    private String stageName;
    private String enemyType;
    private String bgImagesource;
    private String powerGrowth;
    private String bosstype;

    public Stage() {}

    public Stage(int id, String stageName, String enemyType, String bgImagesource, String powerGrowth, String bosstype) {
        this.id = id;
        this.stageName = stageName;
        this.enemyType = enemyType;
        this.bgImagesource = bgImagesource;
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

    public String getBgImagesource() {
        return bgImagesource;
    }

    public String getPowerGrowth() {
        return powerGrowth;
    }

    public String getBosstype() {
        return bosstype;
    }
}
