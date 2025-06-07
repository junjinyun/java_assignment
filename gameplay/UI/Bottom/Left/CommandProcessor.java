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
                skillButtonPanel.updateSkillButtons();
                yield "";
            }
            case "spawnally()" -> {
                gamePlayer.generateAllyParty();
                skillButtonPanel.updateSkillButtons();
                yield "";
            }
            case String cmd when cmd.matches("spawnally\\(([^)]*)\\)") -> {
                try {
                    String input = cmd.substring(cmd.indexOf('(') + 1, cmd.lastIndexOf(')')).trim();
                    gamePlayer.generateAllyParty(input);
                    skillButtonPanel.updateSkillButtons();
                    yield "입력값으로 아군 생성: " + input;
                } catch (Exception e) {
                    yield "spawnally 명령 처리 중 오류 발생: " + e.getMessage();
                }
            }
            case "stage" -> {
                gamePlayer.generateStage();
                yield "스테이지를 랜덤으로 선정합니다.";
            }
            case "allyskill" -> {
                gamePlayer.loadAllySkills();
                skillButtonPanel.updateSkillButtons();
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
            case "map" -> {
                gamePlayer.generateMap();
                yield "맵 생성 완료.";
            }
            case "changebattlestate" -> {
            	gamePlayer.changeBattleState();
            	yield "전투 <---> 비전투 상태 전환.";
            }
            case "yes", "no" -> {
                gamePlayer.setYesOrNo(command.toLowerCase());
                yield "선택 입력 처리됨: " + command.toLowerCase();
            }
            case String noneModeCmd when noneModeCmd.matches("setnonemode\\((0|1)\\)") -> {
                try {
                    int mode = Integer.parseInt(noneModeCmd.replaceAll("[^01]", ""));
                    gamePlayer.setNoneMode(mode == 1);
                    yield "NoneMode가 " + (mode == 1 ? "활성화" : "비활성화") + "되었습니다.";
                } catch (Exception e) {
                    yield "형식 오류: setnonemode(0) 또는 setnonemode(1)";
                }
            }
            case String moveCharCmd when moveCharCmd.matches("movechar\\(([aAeE]\\d+),(\\d+),(0|1)\\)") -> {
                try {
                    // movechar(A1,2,1) 같은 명령어 파싱
                    String[] parts = moveCharCmd.substring(moveCharCmd.indexOf("(") + 1, moveCharCmd.indexOf(")")).split(",");
                    String mappingId = parts[0].trim().toUpperCase();  // A1, E2 등
                    int moveAmount = Integer.parseInt(parts[1].trim());
                    boolean isForward = parts[2].trim().equals("1");

                    gamePlayer.testMoveCharacter(mappingId, moveAmount, isForward);
                    yield String.format("캐릭터 %s를 %d칸 %s 이동했습니다.", mappingId, moveAmount, isForward ? "앞으로" : "뒤로");
                } catch (Exception e) {
                    yield "명령어 형식 오류: movechar(<mappingId>,<moveAmount>,<direction>)";
                }
            }
            
            case String selectAlly when selectAlly.matches("selectally\\((\\d+)\\)") -> {
                int allyNumber = Integer.parseInt(selectAlly.replaceAll("[^0-9]", ""));
                if (allyNumber >= 1 && allyNumber <= 4) {
                    gamePlayer.selectAllyByMid(allyNumber);
                    yield allyNumber + "번 아군 선택.";
                } else
                    yield "인자는 1 ~ 4 사이의 숫자로 입력하시오.";
            }
            case String selectEnemy when selectEnemy.matches("selectenemy\\((\\d+)\\)") -> {
                int enemyNumber = Integer.parseInt(selectEnemy.replaceAll("[^0-9]", ""));
                if (enemyNumber >= 1 && enemyNumber <= 4) {
                    gamePlayer.selectEnemyByMid(enemyNumber);
                    yield enemyNumber + "번 적군 선택.";
                }
                yield "인자는 1 ~ 4 사이의 숫자로 입력하시오.";
            }
            case String testAE when testAE.matches("testae\\(([^,]+),([^,]+),(\\d+),(\\d+)\\)") -> {
                try {
                    String[] parts = testAE.substring(testAE.indexOf("(") + 1, testAE.indexOf(")")).split(",");
                    String target = parts[0].trim();
                    String effectName = parts[1].trim();
                    int power = Integer.parseInt(parts[2].trim());
                    int duration = Integer.parseInt(parts[3].trim());
                    gamePlayer.applyEffect(target, effectName, power, duration);
                    yield "효과 적용: 대상=" + target + ", 효과=" + effectName + ", 수치=" + power + ", 지속시간=" + duration;
                } catch (Exception e) {
                    yield "형식 오류 또는 처리 중 예외 발생: " + e.getMessage();
                }
            }
            case "exit" -> {
                System.exit(0);
                yield "프로그램을 종료합니다.";
            }
            default -> {
                gamePlayer.setInput(command);  // default 명령 저장
                yield command +"를 입력값 으로 저장합니다.";
            }
        };
    }
}
