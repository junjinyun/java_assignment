package gameplay.AdditionalEffects;

import gameplay.Party.AllyStatusManager;

public interface Effect {
    String getName();           // 효과 이름
    int getPower();             // 위력 (예: 30, 40, 50)
    int getDuration();          // 지속 시간 (턴)
    String getType();           // 효과 종류 (상태이상, 스텟증감, 특수효과)
    boolean isExpired();        // 만료 여부 체크
	void apply(Object target);
	void onTurnEnd(Object target);
}
