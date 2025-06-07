package gameplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import gameplay.Battle.AllyActionExecutor;
import gameplay.Battle.BattleExecutor;
import gameplay.Battle.BattleObserver;
import gameplay.Battle.MovementManager;
import gameplay.Event.EventLauncher;
import gameplay.Event.LogEventManager;
import gameplay.Event.StageInfo;
import gameplay.Event.YesNoObserver;
import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;
import gameplay.Party.SetSpeedAct;
import gameplay.UI.UIObserver;
import loaddata.SkillManager;
import loaddata.Stage;
import gameplay.Party.NameMapper;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;
import gameplay.Map.EntryPointSelector;
import gameplay.Map.MakeUseMap;
import gameplay.Model.Player;

public class GamePlayer {
	private AllyParty allyParty;
	private EnemyParty enemyParty;
	private Stage randomStage;
	private String mappingId = "A1";
	private String eMappingId = "E1";
	private String targetMappingId;
	private String yesOrNo = "";
	private String input = "";
	private List<UIObserver> observers = new ArrayList<>();
	private List<BattleObserver> battleObservers = new ArrayList<>();
	private List<YesNoObserver> yesnoObserver = new ArrayList<>();
	private final List<MemoryObserver> memoryObservers = new ArrayList<>();
	private int[][] mapData;
	private boolean battleState = false;
	private boolean allyActionLocked = false; // 아군 선택 잠금 상태
	private boolean enemyActionLocked = false; // 아군 선택 잠금 상태
	private int selectedSkillNum = -1;
	private int hasAct = 1; // 1: 행동 가능 상태, 0: 행동 완료됨
	private int turn = 1;
	private int actcount = 1;
	private int moveCount = 0;
	private boolean noneMode = false;
	private boolean onEvent = false;
	private List<String> fieldInfo = new ArrayList<>(Collections.nCopies(8, "slot"));
	private final long memoryThreshold = (long) (Runtime.getRuntime().maxMemory() * 0.8); // 예: 80%
	private final MovementManager movementManager = new MovementManager(this);
	private final AllyActionExecutor AAE = new AllyActionExecutor(this);
	private BattleExecutor battleExecutor;
	private LogEventManager logEventManager;
	private Player player;
	private EventLauncher eventLauncher;
	
	public void testMoveCharacter(String mappingId, int moveAmount, boolean isForward) {
		movementManager.moveCharacter(mappingId, moveAmount, isForward);
		notifyObservers();
	}
    public void addYesNoObserver(YesNoObserver observer) {
    	yesnoObserver.add(observer);
    }
    private void notifyYesNoObservers() {
        for (YesNoObserver observer : yesnoObserver) {
            observer.onYesNoInput(yesOrNo);
        }
    }
    public void removeYesNoObserver(YesNoObserver observer) {
    	yesnoObserver.remove(observer);
    }
	// 옵저버 등록
	public void addObserver(UIObserver observer) {
		observers.add(observer);
	}

	// 옵저버 알림
	public void notifyObservers() {
		for (UIObserver observer : observers) {
			observer.update(this);
		}
	}

	public void addBattleObserver(BattleObserver observer) {
		battleObservers.add(observer);
	}

	public void notifyBattleObservers() {
		for (BattleObserver observer : battleObservers) {
			observer.onActionComplete(this); // GamePlayer 객체 전달
		}
	}

	public void addMemoryObserver(MemoryObserver observer) {
		memoryObservers.add(observer);
	}

	public void checkMemoryUsage() {
		long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (usedMemory >= memoryThreshold) {
			notifyMemoryObservers(usedMemory);
		}
	}

	private void notifyMemoryObservers(long usedMemory) {
		long maxMemory = Runtime.getRuntime().maxMemory();
		for (MemoryObserver observer : memoryObservers) {
			observer.onMemoryThresholdExceeded(usedMemory, maxMemory);
		}
	}

	// 맵 생성
	public void generateMap() {
		MakeUseMap mapMaker = new MakeUseMap();
		mapMaker.mkMap();

		mapData = mapMaker.getMapdata();
		for (int i = 0; i < mapData.length; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < mapData[i].length; j++) {
				sb.append(mapData[i][j]).append(" ");
			}
			System.out.println(sb.toString().trim());
		}

		EntryPointSelector entrySelector = new EntryPointSelector();
		mapData = entrySelector.selectEntryPoint(mapData);

		for (int i = 0; i < mapData.length; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < mapData[i].length; j++) {
				sb.append(mapData[i][j]).append(" ");
			}
			System.out.println(sb.toString().trim());
		}

		notifyObservers();
	}

	// 스테이지 생성
	public void generateStage() {
		StageInfo stageInfo = new StageInfo();
		randomStage = stageInfo.getRandomValidStage();
		stageInfo.printStageInfo(randomStage.getId());
	}

	// 아군과 적군 파티 생성
	public void generateParties() {
	    if (allyParty == null) {
	        System.out.println("적 소환은 게임 시작 시 사용 가능");
	        return;
	    }

	    if (randomStage == null) {
	        generateStage();
	    }

	    enemyParty = new EnemyParty(randomStage);
	    loadEnemySkills();

	    for (int i = 4; i >= 1; i--) {
	        selectEnemyByMid(i);
	    }

	    for (EnemyStatusManager enemy : enemyParty.getEnemyParty()) {
	        int originalAttack = enemy.getBaseStats().getAttack();
	        int halvedAttack = (int) Math.ceil(originalAttack * 0.5);
	        enemy.getBaseStats().setAttack(halvedAttack);
	    }

	    SetSpeedAct.setSpeed(allyParty.getParty(), enemyParty.getEnemyParty());
	    SetSpeedAct.setActionOrder(allyParty.getParty(), enemyParty.getEnemyParty());

	    PartyInfoPrinter infoPrinter = new PartyInfoPrinter();
	    infoPrinter.printEnemyInfo(enemyParty);
	}
	public void generateAllyParty() {
    allyParty = new AllyParty();
    System.out.println("아군목록");
    System.out.println("============");
    allyParty.printAllAllyNames();
    System.out.println("아군을 4명 중복되지 않게 공백으로 구분하여 \n spawnally(String)메서드로 입력하십시오.");
	}
	public void generateAllyParty(String nums) {
	    actuallyGenerateAllyParty( nums);  // 최초 생성
	}
	private void actuallyGenerateAllyParty(String nums) {
	    allyParty = new AllyParty();
	    allyParty.selectPartyFromInput(nums);

	    for (int i = 1; i <= 4; i++) {
	        AllyStatusManager statusManager = allyParty.getAllyByMappingID("A" + i);
	        if (statusManager != null) {
	            int originalAttack = statusManager.getBaseStats().getAttack();
	            int doubledAttack = (int) Math.ceil(originalAttack * 2.0);
	            statusManager.getBaseStats().setAttack(doubledAttack);

	            String name = NameMapper.toSystemName(statusManager.getName());
	            System.out.println("Ally " + name + " added to the party with attack " + doubledAttack + ".");
	        }
	    }

	    loadAllySkills();

	    for (int i = 4; i >= 1; i--) {
	        selectAllyByMid(i);
	    }

	    PartyInfoPrinter infoPrinter = new PartyInfoPrinter();
	    infoPrinter.printAllyInfo(allyParty);
	}




	public void loadAllySkills() {
		if (allyParty == null)
			return;

		for (int i = 1; i <= allyParty.getParty().size(); i++) {
			AllyStatusManager statusManager = allyParty.getAllyByMappingID("A" + i);
			if (statusManager != null) {
				String name = NameMapper.toSystemName(statusManager.getName());
				System.out.println("Loading skills for: " + name);
				SkillManager.loadAlltSkillsByKeyName(name).forEach(skill -> statusManager.loadSkills(skill));
			}
		}
	}

	public void loadEnemySkills() {
		if (enemyParty == null) {
			System.out.println("Enemy party is not initialized.");
			return;
		}

		for (int i = 1; i <= enemyParty.getEnemyParty().size(); i++) {
			EnemyStatusManager statusManager = enemyParty.getEnemyByMappingID("E" + i);
			if (statusManager != null) {
				String name = NameMapper.toSystemName(statusManager.getName());
				System.out.println("Loading skills for (ID: E" + i + "): " + name);
				SkillManager.loadUsableEnemySkillsByType(name, enemyParty.getEnemyParty().get(i - 1).getId())
						.forEach(skill -> statusManager.addSkill(skill));
			}
		}
	}

	public void printAllySkills() {
		if (allyParty == null)
			return;

		for (int i = 1; i <= allyParty.getParty().size(); i++) {
			AllyStatusManager statusManager = allyParty.getAllyByMappingID("A" + i);
			if (statusManager != null) {
				System.out.println("Ally: " + statusManager.getName());
				statusManager.getSkillList().forEach(skill -> {
					System.out.println("    Skill Name: " + skill.getName());
				});
			}
		}
	}

	public void printEnemySkills() {
		if (enemyParty == null)
			return;

		for (int i = 1; i <= enemyParty.getEnemyParty().size(); i++) {
			EnemyStatusManager statusManager = enemyParty.getEnemyByMappingID("E" + i);
			if (statusManager != null) {
				System.out.println("Enemy (ID: " + "E" + i + "): " + statusManager.getName());
				statusManager.getSkillList().forEach(skill -> {
					System.out.println("    Skill Name: " + skill.getName());
				});
			}
		}
	}

	// 아군을 번호(MappingId 기반)로 선택
	public void selectAllyByMid(int num) {
		if (battleState && allyActionLocked) {
			System.out.println("전투 중에는 아군 선택이 잠금되어 변경할 수 없습니다.");
			return;
		}
		mappingId = "A" + num;
		System.out.println(mappingId + " 의 아군을 로드합니다");
		notifyObservers();
	}

	// 적군을 번호(MappingId 기반)로 선택
	public void selectEnemyByMid(int num) {
		eMappingId = "E" + num;
		System.out.println(eMappingId + " 의 적군을 로드합니다");
		notifyObservers();
	}

	// 아군을 전투 위치(Position) 기반으로 선택
	public void selectAllyByPosition(int num) {
		if (battleState && allyActionLocked) {
			System.out.println("전투 중에는 아군 선택이 잠금되어 변경할 수 없습니다.");
			return;
		}

		boolean found = false;
		for (int i = 1; i <= 4; i++) {
			AllyStatusManager a = this.getAllyParty().getAllyByMappingID("A" + i);
			if (a != null && a.getPosition() == num) {
				mappingId = "A" + i;
				found = true;
				break;
			}
		}

		if (found) {
			System.out.println(mappingId + " 의 아군을 로드합니다");
			notifyObservers();
		} else {
			System.out.println(num + " 위치에 아군이 존재하지 않습니다.");
		}
	}

	// 적군을 전투 위치(Position) 기반으로 선택
	public void selectEnemyByPosition(int num) {
		boolean found = false;
		for (int i = 1; i <= 4; i++) {
			EnemyStatusManager e = this.getEnemyParty().getEnemyByMappingID("E" + i);
			if (e != null && e.getPosition() == num) {
				eMappingId = "E" + i;
				found = true;
				break;
			}
		}

		if (found) {
			System.out.println(eMappingId + " 의 적군을 로드합니다");
			notifyObservers();
		} else {
			System.out.println(num + " 위치에 적군이 존재하지 않습니다.");
		}
	}

	public void applyEffect(String target, String effectName, int power, int duration) {
		if (target == null || target.length() < 2) {
			System.out.println("잘못된 대상 식별자: " + target);
			return;
		}

		char type = Character.toUpperCase(target.charAt(0)); // 'A' 또는 'E'

		if (type == 'A') {
			AllyStatusManager ally = allyParty.getAllyByMappingID(target);
			if (ally != null) {
				addEffectToManager(ally, effectName, power, duration);
			} else {
				System.out.println("대상을 찾을 수 없습니다: " + target);
			}
		} else if (type == 'E') {
			EnemyStatusManager enemy = enemyParty.getEnemyByMappingID(target);
			if (enemy != null) {
				addEffectToManager(enemy, effectName, power, duration);
			} else {
				System.out.println("대상을 찾을 수 없습니다: " + target);
			}
		} else {
			System.out.println("대상 구분 불가: " + target);
		}
	}

	private void addEffectToManager(Object manager, String effectName, int power, int duration) {
		String data = effectName + " " + power + " " + duration;

		if (manager instanceof AllyStatusManager ally) {
			ally.addEffects(data);
		} else if (manager instanceof EnemyStatusManager enemy) {
			enemy.addEffects(data);
		} else {
			System.out.println("알 수 없는 매니저 타입");
		}
	}

	// 전투 상태 전환 메서드
	public void changeBattleState() {
		if (battleState) {
			battleState = false;
			allyActionLocked = false;
			System.out.println("비전투 상태로 전환 합니다.");
		} else {
			battleState = true;
			allyActionLocked = false; // 초기에는 잠금 해제 상태, 전투 진행시 잠금 설정
			System.out.println("전투 상태로 전환 합니다.");
		}
	}

	public void allyAct() {
		new gameplay.Battle.AllyActionExecutor(this).allyAct();
	}

	public AllyParty getAllyParty() {
		return allyParty;
	}

	public void setAllyParty(AllyParty allyParty) {
		this.allyParty = allyParty;
	}

	public EnemyParty getEnemyParty() {
		return enemyParty;
	}

	public void setEnemyParty(EnemyParty enemyParty) {
		this.enemyParty = enemyParty;
	}

	public Stage getRandomStage() {
		return randomStage;
	}

	public void setRandomStage(Stage randomStage) {
		this.randomStage = randomStage;
	}

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	public String getEMappingId() {
		return eMappingId;
	}

	public void setEMappingId(String eMappingId) {
		this.eMappingId = eMappingId;
	}

	public String getTargetMappingId() {
		return targetMappingId;
	}

	public void setTargetMappingId(String targetMappingId, boolean isAllyAction) {
		this.targetMappingId = targetMappingId;

		if (isAllyAction) {
			if (selectedSkillNum != -1 && hasAct == 1 && battleState) {
				boolean success = AAE.allyAct();
				if (success) {
					selectedSkillNum = -1;
					this.targetMappingId = "";
					notifyObservers();
					battleExecutor.onActionComplete(this);
				} else {
					System.out.println("스킬 사용 실패, 행동 유지");
					// 실패 시에는 상태 초기화하지 않음 -> 행동 유지
				}
			}
		} else {
			System.out.println("적 행동을 위한 타겟 mappingId 설정됨: " + targetMappingId);
		}
	}

	public List<UIObserver> getObservers() {
		return observers;
	}

	public void setObservers(List<UIObserver> observers) {
		this.observers = observers;
	}

	public int[][] getMapData() {
		return mapData;
	}

	public void setMapData(int[][] mapData,String type) {
		this.mapData = mapData;
	    if ("미방문 방".equals(type)) {
	        this.incrementMoveCount();
			//n회 이동 시 마다 전투
			if(this.moveCount % 5 == 0 && this.isNoneMode()==false) {
				battleExecutor = new BattleExecutor(this);
				battleExecutor.startBattle();
			}
	        // x회 이동 시마다 랜덤 이벤트 발생
	        if (this.moveCount % 4 == 0 && this.isNoneMode()==false) {
	        	logEventManager = new LogEventManager(this);
	        	this.setOnEvent(true);
	            logEventManager.startRandomEvent();
	        }
	        if(this.getMoveCount()==20) {
	        	this.setMoveCount(0);
	        }
	    }

	}

	public boolean isBattleState() {
		return battleState;
	}

	public void setBattleState(boolean battleState) {
		this.battleState = battleState;
	}

	public boolean isAllyActionLocked() {
		return allyActionLocked;
	}

	public void setAllyActionLocked(boolean allyActionLocked) {
		this.allyActionLocked = allyActionLocked;
	}

	public int getSelectedSkillNum() {
		return selectedSkillNum;
	}

	public void setSelectedSkillNum(int selectedSkillNum) {
		this.selectedSkillNum = selectedSkillNum;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getActcount() {
		return actcount;
	}

	public void setActcount(int actcount) {
		this.actcount = actcount;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}
    public void incrementMoveCount() {
        moveCount++;
    }

	public boolean isEnemyActionLocked() {
		return enemyActionLocked;
	}

	public void setEnemyActionLocked(boolean enemyActionLocked) {
		this.enemyActionLocked = enemyActionLocked;
	}

	public void setFieldInfoAt(int index, String value) {
		// 1 ~ 8 범위 체크
		if (index < 1 || index > 8) {
			System.out.println("범위오류: index는 1부터 8 사이여야 합니다.");
			return;
		}
		fieldInfo.set(index - 1, value); // 내부 리스트는 0-based 인덱스
	}

	// 특정 인덱스의 값을 가져오는 게터
	public String getFieldInfoAt(int index) {
		// 1 ~ 8 범위 체크
		if (index < 1 || index > 8) {
			System.out.println("범위오류: index는 1부터 8 사이여야 합니다.");
			return null;
		}
		if (fieldInfo == null) {
			System.out.println("값이 없습니다.");
			return null;
		}
		return fieldInfo.get(index - 1);
	}

	public void setHasAct(int value) {
		if (this.hasAct == 1 && value == 0) {
			// 행동 완료 시점
			System.out.println("행동 완료됨. 다음 캐릭터로 진행할 준비.");

			// UI 옵저버 알림
			notifyObservers();

			// 전투 옵저버 알림 추가
			notifyBattleObservers();
		}
		this.hasAct = value;
	}
    public String getYesOrNo() {
        return yesOrNo;
    }
    public void setYesOrNo(String input) {
        yesOrNo = input.equalsIgnoreCase("yes") ? "yes" : "no";
        //System.out.println("선택: " + yesOrNo);
        notifyYesNoObservers();
    }
    public EventLauncher getEventLauncher() {
        return eventLauncher;
    }
    public void setEventLauncher(EventLauncher eventLauncher) {
        this.eventLauncher = eventLauncher;
    }
    public boolean isOnEvent() {
        return onEvent;
    }

    public void setOnEvent(boolean onEvent) {
        this.onEvent = onEvent;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public boolean isNoneMode() {
        return noneMode;
    }

    public void setNoneMode(boolean noneMode) {
        this.noneMode = noneMode;
    }
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void initializePlayerData() {
        // 1. 초기 데이터 불러오기
        Player initialPlayer = Player.loadPlayer("src/data/initial_player_data.json");
        
        if (initialPlayer != null) {
            // 2. 불러온 초기 데이터를 저장 경로에 덮어쓰기
            initialPlayer.savePlayer("src/data/player_data.json");
            player = Player.loadPlayer("src/data/player_data.json");
        } else {
            System.err.println("초기 플레이어 데이터를 불러오는 데 실패했습니다.");
        }
    }

}
