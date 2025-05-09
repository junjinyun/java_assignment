package gameplay.UI;

import gameplay.GamePlayer;

public class CommandProcessor {
    private final GamePlayer gamePlayer;

    public CommandProcessor(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public String processCommand(String command) {
        return switch (command.toLowerCase()) {
            case "start" -> {
                gamePlayer.startGame();
                yield "게임을 시작합니다.";
            }
            case "spawn" -> {
                gamePlayer.generateParties();
                yield "파티를 생성합니다.";
            }
            case "stage" -> {
                gamePlayer.generateStage();
                yield "스테이지를 랜덤으로 선정합니다.";
            }
            default -> "알 수 없는 명령어입니다: " + command;
        };
    }
}
