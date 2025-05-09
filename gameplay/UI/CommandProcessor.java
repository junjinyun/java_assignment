package gameplay.UI;

import gameplay.GamePlayer;

public class CommandProcessor {
    private final GamePlayer gamePlayer;

    public CommandProcessor(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public String processCommand(String command) {
        return switch (command.toLowerCase()) {
            case "spawn" -> {
                gamePlayer.generateParties();
                yield "파티를 생성합니다.";
            }
            case "stage" -> {
                gamePlayer.generateStage();
                yield "스테이지를 랜덤으로 선정합니다.";
            }
            case "allyskill" -> {
                gamePlayer.generateStage();
                yield "아군 스킬을 최신화합니다.";
            }
            case "enemyskill" -> {
                gamePlayer.generateStage();
                yield "적군 스킬을 최신화합니다.";
            }
            case "exit" -> {
                System.exit(0); // 프로그램 종료
                yield "프로그램을 종료합니다."; // 실제로는 이 줄은 실행되지 않음
            }
            default -> "알 수 없는 명령어입니다: " + command;
        };
    }
}
