package loaddata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkillManager {
    private static final String ENEMY_SKILLS_JSON_PATH = "src/data/enemyskills.json"; // 적 스킬 JSON 경로
    private static final String ALLY_SKILLS_JSON_PATH = "src/data/allyskills.json";   // 아군 스킬 JSON 경로

    /**
     * 적 타입에 해당하는 스킬 목록을 가져온다.
     * @param type 적 타입 키 (예: "Humanoid", "Beast")
     * @return 해당 타입의 스킬 목록, 없으면 null
     */
    public static List<EnemySkills> loadEnemySkillsByType(String type) {
        try (FileReader reader = new FileReader(ENEMY_SKILLS_JSON_PATH)) {
            Gson gson = new Gson();

            Map<String, List<EnemySkills>> skillMap = gson.fromJson(reader, new TypeToken<Map<String, List<EnemySkills>>>(){}.getType());

            return skillMap.get(type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

     //아군 key(캐릭터)Owner(세부전직)에 해당하는 스킬 목록을 가져온다
        public static List<AllySkills> loadAlltSkillsByKeyName(String key){
            return loadAlltSkillsByKeyName(key, "default");
        }// 자바에서는 매개변수의 디폴트 값을 지정 하는것이 불가능 하기에 위와 같이 사용

        public static List<AllySkills> loadAlltSkillsByKeyName(String key, String Name) {
            try (FileReader reader = new FileReader(ALLY_SKILLS_JSON_PATH)) {
                Gson gson = new Gson();

                Map<String, List<AllySkills>> skillMap = gson.fromJson(reader, new TypeToken<Map<String, List<AllySkills>>>(){}.getType());

                List<AllySkills> Simplifymap = skillMap.get(key);
                List<AllySkills> result = new ArrayList<>();

                for (AllySkills skill : Simplifymap) {
                    // Name이 "default"인 경우, key와 일치하는 스킬을 반환
                    if ("default".equalsIgnoreCase(Name)) {
                        if (skill.getOwner().equalsIgnoreCase(key)) {
                            result.add(skill);
                        }
                    } else {
                        // "default"가 아닌 경우 기존 방식대로 처리
                        if (skill.getOwner().equalsIgnoreCase(key) || skill.getOwner().equalsIgnoreCase(Name)) {
                            result.add(skill);
                        }
                    }
                }
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    //1. 캐릭터 분류를 위해 key로 스킬을 분류하여 가져옴
    //2. 캐릭터의 세부전직 에 따른 스킬을 가져오기 위하여 owner 속성에 따라 가져옴(세부전직[ex: 전사는 "현상금사냥꾼"과 "광전사" 로 나뉨]의 이름을 넣음)
    // 캐릭터가 세부 전직을 하지 않았을 경우 owner는 입력하지 않음(키 이름과 동일한 owner를 가진 스킬만 가져옴)
    // 세부 전직 이 존재 하면 기본 스킬과 전직 스킬을 가져옴.
}