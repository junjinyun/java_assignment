package loaddata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkillManager {
    private static final String ENEMY_SKILLS_JSON_PATH = "src/data/enemy_skills.json"; // 적 스킬 JSON 경로
    private static final String ALLY_SKILLS_JSON_PATH = "src/data/ally_skills.json";   // 아군 스킬 JSON 경로

    /**
     * 적 타입에 해당하는 스킬 목록을 가져온다.
     * @param type 적 타입 키 (예: "Humanoid", "Beast")
     * @return 해당 타입의 스킬 목록, 없으면 null
     */
    public static List<Skill> loadEnemySkillsByType(String type) {
        try (FileReader reader = new FileReader(ENEMY_SKILLS_JSON_PATH)) {
            Gson gson = new Gson();

            Map<String, List<Skill>> skillMap = gson.fromJson(reader, new TypeToken<Map<String, List<Skill>>>(){}.getType());

            return skillMap.getOrDefault(type, null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 아군 Owner에 해당하는 스킬 목록을 가져온다.
     * @param owner 아군 소유자 (예: "Warrior", "Mage")
     * @return 해당 Owner의 스킬 목록, 없으면 빈 리스트
     */
    public static List<Skill> loadAllySkillsByOwner(String owner) {
        try (FileReader reader = new FileReader(ALLY_SKILLS_JSON_PATH)) {
            Gson gson = new Gson();

            List<Skill> allSkills = gson.fromJson(reader, new TypeToken<List<Skill>>(){}.getType());

            List<Skill> result = new ArrayList<>();
            for (Skill skill : allSkills) {
                if (skill.getOwner() != null && skill.getOwner().equalsIgnoreCase(owner)) {
                    result.add(skill);
                }
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 테스트용 메인 함수
    public static void main(String[] args) {
        // 적 스킬 테스트
        List<Skill> humanoidSkills = loadEnemySkillsByType("Humanoid");
        if (humanoidSkills != null) {
            System.out.println("[Humanoid 스킬 목록]");
            for (Skill skill : humanoidSkills) {
                System.out.println("기술 이름: " + skill.getName() + ", 피해 배율: " + skill.getDamageMultiplier());
            }
        } else {
            System.out.println("Humanoid 스킬 데이터를 불러오지 못했습니다.");
        }

        // 아군 스킬 테스트
        List<Skill> warriorSkills = loadAllySkillsByOwner("Warrior");
        if (warriorSkills != null && !warriorSkills.isEmpty()) {
            System.out.println("\n[Warrior 스킬 목록]");
            for (Skill skill : warriorSkills) {
                System.out.println("기술 이름: " + skill.getName() + ", 피해 배율: " + skill.getDamageMultiplier());
            }
        } else {
            System.out.println("Warrior 스킬을 찾을 수 없습니다.");
        }
    }
}