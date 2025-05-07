package gameplay.Party;

import java.util.HashMap;
import java.util.Map;

public class NameMapper {
	 private static final Map<String, String> nameMap = new HashMap<>();

	    static {
	        // 사용자 표시 이름 → 시스템 이름
	        nameMap.put("기사", "knight");
	        nameMap.put("전사", "warrior");
	        nameMap.put("도적", "rogue");
	        nameMap.put("사수", "marksman");
	        nameMap.put("사제", "priest");
	        nameMap.put("마법사", "sorcerer");
	        // 추가 매핑 필요 시 여기에 계속 추가
	        nameMap.put("고블린 졸병", "humanoid");
	        nameMap.put("고블린 사수", "humanoid");
	        nameMap.put("고블린 창병", "humanoid");
	        nameMap.put("고블린 도축업자", "humanoid");
	        nameMap.put("오우거", "humanoid");

	        // thive
	        nameMap.put("도적단 전사", "thive");
	        nameMap.put("도적단 사수", "thive");
	        nameMap.put("도적단 도적", "thive");
	        nameMap.put("도적단 보급병", "thive");
	        nameMap.put("도적단 지휘관", "thive");

	        // beast
	        nameMap.put("변이된 늑대", "beast");
	        nameMap.put("변이된 까마귀", "beast");
	        nameMap.put("변이된 뱀", "beast");
	        nameMap.put("감염된 쥐 군체", "beast");
	        nameMap.put("감염된 곰", "beast");

	        // monster
	        nameMap.put("좀비", "monster");
	        nameMap.put("망령", "monster");
	        nameMap.put("슬라임", "monster");
	        nameMap.put("저주받은 무덤", "monster");
	        nameMap.put("리치", "monster");
	        
	    }

	    public static String toSystemName(String displayName) {
	        return nameMap.getOrDefault(displayName, displayName); // 기본적으로 그대로 반환
	    }
}
