// GamePlayer.java
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
import gameplay.Map.EntryPointSelector;
import gameplay.Map.MakeUseMap;

public class GamePlayer {
    private AllyParty allyParty;
    private EnemyParty enemyParty;
    private Stage randomStage;
    private String mappingId = "A1";
    private String eMappingId = "E1";
    private List<UIObserver> observers = new ArrayList<>();
    private int[][] mapData;

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
            System.out.println(sb.toString().trim());  // 한 행씩 한 줄 출력
        }

        EntryPointSelector entrySelector = new EntryPointSelector();
        mapData = entrySelector.selectEntryPoint(mapData);

        for (int i = 0; i < mapData.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < mapData[i].length; j++) {
                sb.append(mapData[i][j]).append(" ");
            }
            System.out.println(sb.toString().trim());  // 한 행씩 한 줄 출력
        }
        // 맵 데이터 변경 즉시 UI 갱신 요청
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
            allyParty = new AllyParty();
            for (int i = 1; i <= 4; i++) {
                AllyStatusManager statusManager = allyParty.getAllyByMappingID("A" + i);
                if (statusManager != null) {
                    String name = NameMapper.toSystemName(statusManager.getName());
                    System.out.println("Ally " + name + " added to the party.");
                }
            }
            loadAllySkills();
        }

        if (randomStage == null) {
            generateStage();
        }

        enemyParty = new EnemyParty(randomStage);
        loadEnemySkills();

        SetSpeedAct.setSpeed(allyParty.getParty(), enemyParty.getEnemyParty());
        SetSpeedAct.setActionOrder(allyParty.getParty(), enemyParty.getEnemyParty());

        PartyInfoPrinter infoPrinter = new PartyInfoPrinter();
        infoPrinter.printAllyInfo(allyParty);
        infoPrinter.printEnemyInfo(enemyParty);
    }

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
        mappingId = "A" + num;
        System.out.println(mappingId + " 의 아군을 로드합니다");
        notifyObservers();
    }

    public void selectEnemyByMid(int num) {
        eMappingId = "E" + num;
        System.out.println(eMappingId + " 의 적군을 로드합니다");
        notifyObservers();
    }

    public AllyParty getAllyParty() {
        return allyParty;
    }

    public EnemyParty getEnemyParty() {
        return enemyParty;
    }

    public String getMappingId() {
        return mappingId;
    }

    public String getEMappingId() {
        return eMappingId;
    }

    public int[][] getMapData() {
        return mapData;
    }
}
