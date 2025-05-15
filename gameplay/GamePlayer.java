package gameplay;

import java.util.ArrayList;
import java.util.List;

import gameplay.Event.StageInfo;
import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;
import gameplay.Party.SetSpeedAct;
import gameplay.UI.UIObserver;
import loaddata.SkillManager;
import loaddata.Stage;
import gameplay.Party.NameMapper;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;
import gameplay.Map.MakeUseMap;

public class GamePlayer {
    private AllyParty allyParty;
    private EnemyParty enemyParty;
    private Stage randomStage;
    private String MappingId = "A1";
    private String EMappingId = "E1";
    private List<UIObserver> observers = new ArrayList<>();  // 옵저버 리스트 추가
    private int[][] MapData;
    //각 옵저버 별로 트리거 조건을 다르게 함(현제는 해당 클래스의 값이 1개라도 수정 되면 연동된 모든 레이아웃을 돔트리 부터 다시 구성하게 되어있음 -> 성능저하 크게 유발 -> 트리거 조건을 부여하여 해당하는 클래스의 UI만 수정하게 변경)
    //(ex : 아군데이터 관련은 allyParty 와 MappingId)
    //(ex : 적군데이터 관련은 enemyParty 와 EMappingId)
    
    // 옵저버 등록
    public void addObserver(UIObserver observer) {
        observers.add(observer);
    }

    // 옵저버 알림
    public void notifyObservers() {
        for (UIObserver observer : observers) {
            observer.update(this);  // 상태를 전달하여 UI 갱신
        }
    }

    public void generateMap(){
        MakeUseMap mapmaker = new MakeUseMap();
        EntryPointSelector selecter = new EntryPointSelector();
        mapmaker.mkMap();
        mapmaker.mkRoom();
        selecter.EntryPoint();
        //mapmaker.loadMap(); 디버깅 용으로 현제 지도 데이터를 print문으로 출력
        MapData = mapmaker.getMapdata();
    }

    public void moveMap(MapData){
        MakeUseMap mapmaker = new MakeUseMap();
        mapmaker.moveMap();
        //mapmaker.loadMap(); 디버깅 용으로 현제 지도 데이터를 print문으로 출력
        MapData = mapmaker.getMapdata();
    }

    // 스테이지 생성
    public void generateStage() {
        StageInfo stageInfo = new StageInfo();
        randomStage = stageInfo.getRandomValidStage();
        stageInfo.printStageInfo(randomStage.getId());
    }

    // 아군과 적군 파티 생성
    public void generateParties() {
        // 아군 파티가 없으면 생성
        if (allyParty == null) {
            allyParty = new AllyParty();
            for (int i = 1; i <= 4; i++) {
                AllyStatusManager statusManager = allyParty.getAllyByMappingID("A" + i);
                if (statusManager != null) {
                    String name = NameMapper.toSystemName(statusManager.getName());
                    System.out.println("Ally " + name + " added to the party.");
                }
            }
            loadAllySkills(); // 아군 스킬 로드 (생성 시)
        }

        // 스테이지가 없으면 생성
        if (randomStage == null) {
            generateStage();
        }

        // 적 파티 생성
        enemyParty = new EnemyParty(randomStage);
        loadEnemySkills(); // 적군 스킬 로드 (적 생성 시마다)

        // 속도 및 행동 순서 설정
        SetSpeedAct.setSpeed(allyParty.getParty(), enemyParty.getEnemyParty());
        SetSpeedAct.setActionOrder(allyParty.getParty(), enemyParty.getEnemyParty());

        // 정보 출력
        PartyInfoPrinter infoPrinter = new PartyInfoPrinter();
        infoPrinter.printAllyInfo(allyParty);
        infoPrinter.printEnemyInfo(enemyParty);
    }

    // 아군 스킬 로딩 (생성 시, 강화 시에만 로드)
    public void loadAllySkills() {
        if (allyParty == null) return;

        for (int i = 1; i <= allyParty.getParty().size(); i++) {
            AllyStatusManager statusManager = allyParty.getAllyByMappingID("A" + i);
            if (statusManager != null) {
                String name = NameMapper.toSystemName(statusManager.getName());
                System.out.println("Loading skills for: " + name);
                SkillManager.loadAlltSkillsByKeyName(name).forEach(skill -> statusManager.loadSkills(skill));
            }
        }
    }

    // 적군 스킬 로딩 (적 생성 시마다 수시로 호출)
    public void loadEnemySkills() {
        if (enemyParty == null) {
            System.out.println("Enemy party is not initialized.");
            return;
        }

        // 적군 파티에서 각 캐릭터에 대해 스킬을 로드
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

    // 아군 스킬 출력 (소지자와 스킬 이름만)
    public void printAllySkills() {
        if (allyParty == null) return;

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

    // 적군 스킬 출력 (소지자와 스킬 이름만)
    public void printEnemySkills() {
        if (enemyParty == null) return;

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

    public void selectAllyByMid(int num) {
        MappingId = "A" + num;
        System.out.println(MappingId + " 의 아군을 로드합니다");
        notifyObservers();
    }

    public void selectEnemyByMid(int num) {
        EMappingId = "E" + num;
        System.out.println(EMappingId + " 의 적군을 로드합니다");
        notifyObservers();
    }

    public AllyParty getAllyParty() {
        return allyParty;
    }

    public EnemyParty getEnemyParty() {
        return enemyParty;
    }

    public String getMappingId() {
        return MappingId;
    }

    public String getEMappingId() {
        return EMappingId;
    }

    public int[][] getMapData() {
        return MapData;
    }
}
