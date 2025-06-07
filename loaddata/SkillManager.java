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
	private static final String ALLY_SKILLS_JSON_PATH = "src/data/allyskills.json"; // 아군 스킬 JSON 경로

	/**
	 * 적 타입에 해당하는 스킬 목록을 가져온다.
	 *
	 * @param key   적 타입 키 (예: "Humanoid", "Beast")
	 * @param mobId 적 인덱스 ID (예: 1, 2, 3 등)
	 * @return 해당 타입의 usable 스킬 목록, 없으면 빈 리스트
	 */
	public static List<EnemySkills> loadUsableEnemySkillsByType(String key, int mobId) {
		List<EnemySkills> usableSkills = new ArrayList<>();

		try (FileReader reader = new FileReader(ENEMY_SKILLS_JSON_PATH)) {
			Gson gson = new Gson();

			Map<String, List<EnemySkills>> skillMap = gson.fromJson(reader,
					new TypeToken<Map<String, List<EnemySkills>>>() {
					}.getType());

			if (!skillMap.containsKey(key)) {
				System.out.println("Invalid enemy key: " + key);
				return usableSkills;
			}

			List<EnemySkills> skills = skillMap.get(key);
			String mobIdStr = String.valueOf(mobId);

			for (EnemySkills skill : skills) {
				String[] ownerTypes = skill.getOwnerType().split("\\s+");

				for (String ownerType : ownerTypes) {
					if (ownerType.trim().equals(mobIdStr)) {
						usableSkills.add(skill);
						break;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return usableSkills;
	}

	/**
	 * 아군 키(캐릭터) 및 세부 전직에 해당하는 스킬 목록을 가져온다.
	 *
	 * @param key 기본 캐릭터 키
	 * @return 해당 키 및 전직의 스킬 목록
	 */
	public static List<AllySkills> loadAlltSkillsByKeyName(String key) {
		return loadAlltSkillsByKeyName(key, "default");
	}

	public static List<AllySkills> loadAlltSkillsByKeyName(String key, String name) {
		List<AllySkills> result = new ArrayList<>();

		try (FileReader reader = new FileReader(ALLY_SKILLS_JSON_PATH)) {
			Gson gson = new Gson();

			Map<String, List<AllySkills>> skillMap = gson.fromJson(reader,
					new TypeToken<Map<String, List<AllySkills>>>() {
					}.getType());

			List<AllySkills> simplifyMap = skillMap.get(key);
			if (simplifyMap == null) {
				System.out.println("No ally skills found for key: " + key);
			} else {
				for (AllySkills skill : simplifyMap) {
					if ("default".equalsIgnoreCase(name)) {
						if (skill.getOwner().equalsIgnoreCase(key)) {
							result.add(skill);
						}
					} else {
						if (skill.getOwner().equalsIgnoreCase(key) || skill.getOwner().equalsIgnoreCase(name)) {
							result.add(skill);
						}
					}
				}
			}

			// 'default' 키에 해당하는 스킬 리스트를 마지막에 추가
			List<AllySkills> defaultSkills = skillMap.get("default");
			if (defaultSkills != null) {
				result.addAll(defaultSkills);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

}
