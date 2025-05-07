package gameplay;
import loaddada.StageManager;
import loaddada.EnemyManager;
import loaddada.EnemyParty;


public class LoadAllySkills {

    public static void main(String[] args) {

        List<Stage> a = StageManager.loadStages().get(1);
        List<Enemy> SpwanedEnemy = EnemyParty.enemyParty(a);
        public void printEnemyInfo(EnemyParty enemyParty) {
            System.out.println("=== 적군 기초 정보 ===");
            for (enemy enemy : SpwanedEnemy.getEnemyParty()){
                System.out.println("이름: " + enemy.getName());
            }
        }
    }   
}