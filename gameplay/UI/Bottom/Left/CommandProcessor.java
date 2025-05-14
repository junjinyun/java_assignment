package gameplay.UI;

import gameplay.GamePlayer;

public class CommandProcessor {
    private final GamePlayer gamePlayer;
    private final SkillButtonPanel skillButtonPanel;

    public CommandProcessor(GamePlayer gamePlayer, SkillButtonPanel skillButtonPanel) {
        this.gamePlayer = gamePlayer;
        this.skillButtonPanel = skillButtonPanel;
    }

    public String processCommand(String command) {
        return switch (command.toLowerCase()) {
            case "spawn" -> {
                gamePlayer.generateParties();
                skillButtonPanel.updateSkillButtons();  // 스킬 버튼 업데이트
                yield "파티를 생성합니다.";
            }
            case "stage" -> {
                gamePlayer.generateStage();
                yield "스테이지를 랜덤으로 선정합니다.";
            }
            case "allyskill" -> {
                gamePlayer.loadAllySkills();
                skillButtonPanel.updateSkillButtons();  // 스킬 버튼 업데이트
                yield "아군 스킬을 최신화합니다.";
            }
            case "enemyskill" -> {
                gamePlayer.loadEnemySkills();
                yield "적군 스킬을 최신화합니다.";
            }
            case "showask" -> {
                gamePlayer.printAllySkills();
                yield "아군 스킬을 출력합니다.";
            }
            case "showesk" -> {
                gamePlayer.printEnemySkills();
                yield "적군 스킬을 출력합니다.";
            }
            case "selectally(1)" ->{
            	gamePlayer.selectAllyByMid(1);
            	yield "1번 아군 선택.";
            }
            case "selectally(2)" ->{
            	gamePlayer.selectAllyByMid(2);
            	yield "2번 아군 선택.";
            }
            case "selectally(3)" ->{
            	gamePlayer.selectAllyByMid(3);
            	yield "3번 아군 선택.";
            }
            case "selectally(4)" ->{
            	gamePlayer.selectAllyByMid(4);
            	yield "4번 아군 선택.";
            }
            case "exit" -> {
                System.exit(0); // 프로그램 종료
                yield "프로그램을 종료합니다."; // 실제로는 이 줄은 실행되지 않음
            }
            default -> "알 수 없는 명령어입니다: " + command;
        };
    }
}
