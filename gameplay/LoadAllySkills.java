package gameplay;

import gameplay.Party.AllyParty;
import loaddata.SkillManager;
import gameplay.Party.NameMapper;
import gameplay.Party.AllyStatusManager;

public class LoadAllySkills {

    public static void main(String[] args) {
        // AllyParty 객체 생성
        AllyParty allyParty = new AllyParty();

        // 각 아군의 상태 관리 객체를 가져와 스킬 로드
        for (int i = 1; i <= allyParty.getParty().size(); i++) {
            AllyStatusManager allyStatusManager = allyParty.getAllyByMappingID("A"+i); // AllyStatusManager 객체 가져오기
            if (allyStatusManager != null) {
                String allyName = NameMapper.toSystemName(allyStatusManager.getName()); // 아군의 이름을 시스템 이름으로 변환
                System.out.println("Loading skills for: " + allyName);
                // 스킬을 로드하고 해당 캐릭터의 스킬 리스트에 추가
                SkillManager.loadAlltSkillsByKeyName(allyName).forEach(skill -> allyStatusManager.loadSkills(skill));
            }
        }

        // 스킬이 로드된 후, 각 캐릭터의 스킬 리스트 출력
        for (int i = 1; i <= allyParty.getParty().size(); i++) {
            AllyStatusManager allyStatusManager = allyParty.getAllyByMappingID("A"+i); //  AllyStatusManager 객체 가져오기
            if (allyStatusManager != null) {
                System.out.println("Character: " + allyStatusManager.getName());

                int size = allyStatusManager.getSkillList().size();
                System.out.println("Number of skills: " + size);

                if (size == 0) {
                    System.out.println("No skills loaded for this character.");
                } else {
                    for (int x = 0; x < size; x++) {
                        System.out.println("Skill ID: " + x + " Skill Name: " + allyStatusManager.getSkillList().get(x).getName());
                    }
                }
            }
        }
    }
}