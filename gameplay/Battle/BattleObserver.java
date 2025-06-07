package gameplay.Battle;

import gameplay.GamePlayer;

public interface BattleObserver {
	void onActionComplete(GamePlayer player);
}