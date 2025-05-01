package gameplay.Party;

import java.util.HashMap;
import java.util.Map;

public class NameMapper {
	 private static final Map<String, String> nameMap = new HashMap<>();

	    static {
	        // 사용자 표시 이름 → 시스템 이름
	        nameMap.put("기사", "Knight");
	        nameMap.put("전사", "Warrior");
	        nameMap.put("도적", "Rogue");
	        nameMap.put("사수", "Marksman");
	        nameMap.put("사제", "priest");
	        nameMap.put("마법사", "Sorcerer");
	        // 추가 매핑 필요 시 여기에 계속 추가
	    }

	    public static String toSystemName(String displayName) {
	        return nameMap.getOrDefault(displayName, displayName); // 기본적으로 그대로 반환
	    }
}
