
package gameplay.Event;

import loaddata.Stage;
import loaddata.StageManager;
import java.util.List;
import java.util.Random;

public class StageInfo {
    private List<Stage> stages;

    // StageManager로부터 스테이지 리스트를 로드
    public void loadStages() {
        stages = StageManager.loadStages();
        if (stages == null || stages.isEmpty()) {
            System.out.println("스테이지 데이터를 로드할 수 없습니다.");
        }
    }

    // 특정 스테이지 ID에 해당하는 스테이지 정보를 반환
    public Stage getStageById(int id) {
        if (stages == null || stages.isEmpty()) {
            System.out.println("스테이지 데이터가 비어 있습니다.");
            return null;
        }
        
        for (Stage stage : stages) {
            if (stage.getId() == id) {
                return stage;  // 해당 ID에 맞는 스테이지 반환
            }
        }
        System.out.println("해당 ID에 해당하는 스테이지를 찾을 수 없습니다.");
        return null;  // ID에 해당하는 스테이지가 없으면 null 반환
    }

    // 특정 스테이지 ID에 대한 정보를 변수에 저장하여 출력
    public void printStageInfo(int id) {
        Stage stage = getStageById(id);
        if (stage != null) {
            System.out.println("스테이지 ID: " + stage.getId());
            System.out.println("스테이지 이름: " + stage.getStageName());
            System.out.println("적 유형: " + stage.getEnemyType());
            System.out.println("배경 이미지 소스: " + stage.getBgImagePath());
            System.out.println("파워 성장: " + stage.getPowerGrowth());
            System.out.println("보스 유형: " + stage.getBosstype());
        }
    }

    // 유효한 스테이지 중 하나를 무작위로 선택하여 반환 (마지막 스테이지 제외)
    public Stage getRandomValidStage() {
        loadStages();

        if (stages == null || stages.size() < 2) {
            return null; // 유효한 스테이지가 없거나 부족
        }

        Random random = new Random();
        int randomIndex = random.nextInt(stages.size() - 1); // 마지막 스테이지 제외
        return stages.get(randomIndex);
    }
    public static void main(String[] args) {
    	StageInfo stageInfo = new StageInfo();
    	Stage randomStage = stageInfo.getRandomValidStage();

        // 특정 스테이지 ID 정보 출력 (예: ID가 1인 스테이지)
        stageInfo.printStageInfo(randomStage.getId());
    }
}
