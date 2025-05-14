package gameplay.UI.Bottom.Left;

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
			skillButtonPanel.updateSkillButtons(); // 스킬 버튼 업데이트
			yield "파티를 생성합니다.";
		}
		case "stage" -> {
			gamePlayer.generateStage();
			yield "스테이지를 랜덤으로 선정합니다.";
		}
		case "allyskill" -> {
			gamePlayer.loadAllySkills();
			skillButtonPanel.updateSkillButtons(); // 스킬 버튼 업데이트
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
		case String selectAlly when selectAlly.matches("selectally\\((\\d+)\\)") -> {
			// selectally(1), selectally(2) 등에서 숫자를 추출
			int allyNumber = Integer.parseInt(selectAlly.replaceAll("[^0-9]", ""));
			if (allyNumber >= 1 && allyNumber <= 4) {
				gamePlayer.selectAllyByMid(allyNumber);
				yield allyNumber + "번 아군 선택.";
			} else
			yield  "인자는 1 ~ 4 사이의 숫자로 입력하시오.";
		}
		case String selectEnemy when selectEnemy.matches("selectenemy\\((\\d+)\\)") -> {
			// selectenemy(1), selectenemy(2) 등에서 숫자를 추출
			int enemyNumber = Integer.parseInt(selectEnemy.replaceAll("[^0-9]", ""));
			if (enemyNumber >= 1 && enemyNumber <= 4) {
			gamePlayer.selectEnemyByMid(enemyNumber);
			yield enemyNumber + "번 적군 선택.";
			}
			yield  "인자는 1 ~ 4 사이의 숫자로 입력하시오.";
		}
		case "exit" -> {
			System.exit(0); // 프로그램 종료
			yield "프로그램을 종료합니다."; // 실제로는 이 줄은 실행되지 않음
		}
		default -> "알 수 없는 명령어입니다: " + command;
		};
	}
}
