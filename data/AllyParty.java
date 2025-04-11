package data;

import dungeon.Ally;
import dungeon.GetAllyJson;
import gameplay.AllyStatusManager;

import java.util.ArrayList;
import java.util.List;

public class AllyParty {
	private List<AllyStatusManager> party;

	public AllyParty() {
		this.party = new ArrayList<>();
		List<Ally> loadedAllies = GetAllyJson.loadAlly();

		// 예시: ally.json에서 전사, 기사, 도적, 마법사 선택 (index 0,1,2,5)
		int[] allyIndices = {0, 1, 2, 5};

		for (int i = 0; i < 4; i++) {
			Ally ally = loadedAllies.get(allyIndices[i]);
			AllyStatusManager manager = new AllyStatusManager(ally, i + 1); // 위치 1~4
			party.add(manager);
		}
	}

	public List<AllyStatusManager> getParty() {
		return party;
	}

	public AllyStatusManager getAllyByPosition(int position) {
		return party.stream()
			.filter(ally -> ally.getPosition() == position)
			.findFirst()
			.orElse(null);
	}

	public void printPartyStatus() {
		for (AllyStatusManager ally : party) {
			System.out.println(ally);
		}
	}
}