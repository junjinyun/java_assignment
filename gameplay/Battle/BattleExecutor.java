package gameplay.Battle;

import gameplay.GamePlayer;
import gameplay.Event.ItemDrop;
import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;
import gameplay.Party.SetSpeedAct;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BattleExecutor implements BattleObserver {

	private AllyParty allyParty;
	private EnemyParty enemyParty;
	private GamePlayer gamePlayer;
	private boolean battleEnd = false;

	private int currentTurn = 1;
	private int currentActionIndex = 1;
	private int totalUnits = 0;
	private EnemySkillExecutor executor;

	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	public BattleExecutor(GamePlayer gamePlayer) {
		this.gamePlayer = gamePlayer;
		this.allyParty = gamePlayer.getAllyParty();
		this.enemyParty = gamePlayer.getEnemyParty();

		this.gamePlayer.addBattleObserver(this);
	}

	public void startBattle() {
		if (enemyParty == null) {
			System.out.print("적 파티를 소환 합니다");
			gamePlayer.generateParties();
			this.enemyParty = gamePlayer.getEnemyParty();
		}
		System.out.println("전투 시작!");
		gamePlayer.changeBattleState();
		gamePlayer.setAllyActionLocked(false);

		currentTurn = 1;
		currentActionIndex = 1;
		gamePlayer.setTurn(currentTurn);
		gamePlayer.setActcount(currentActionIndex);

		SetSpeedAct.setSpeed(allyParty.getParty(), enemyParty.getEnemyParty());
		SetSpeedAct.setActionOrder(allyParty.getParty(), enemyParty.getEnemyParty());

		totalUnits = allyParty.getParty().size() + enemyParty.getEnemyParty().size();

		proceedNextAction();
	}

	private void proceedNextAction() {
		checkBattleEnd();
		if (battleEnd)
			return;

		gamePlayer.setTurn(currentTurn);
		gamePlayer.setActcount(currentActionIndex);

		boolean acted = false;

		// 아군 행동 처리
		for (AllyStatusManager ally : allyParty.getParty()) {
			if (ally.getActionOrder() == currentActionIndex) {
				if (ally.getBaseStats().getHealth() <= 0) {
					currentActionIndex++;
					postActionProcess();
					return;
				}
				System.out.println("[" + ally.getMappingId() + "] 아군 행동 시작");
				int index = Integer.parseInt(ally.getMappingId().replaceAll("[^0-9]", ""));
				gamePlayer.selectAllyByMid(index);
				gamePlayer.setAllyActionLocked(true);
				gamePlayer.setHasAct(1);
				return;
			}
		}

		// 적군 행동 처리
		EnemyStatusManager currentEnemy = null;
		EnemyStatusManager nextEnemy = null;

		for (EnemyStatusManager enemy : enemyParty.getEnemyParty()) {
			if (enemy.getActionOrder() == currentActionIndex) {
				if (enemy.getBaseStats().getHealth() <= 0) {
					currentActionIndex++;
					postActionProcess();
					return;
				}
				currentEnemy = enemy;
				break;
			}
		}

		int nextActionIndex = currentActionIndex + 1;
		if (nextActionIndex > totalUnits)
			nextActionIndex = 1;

		for (EnemyStatusManager enemy : enemyParty.getEnemyParty()) {
			if (enemy.getActionOrder() == nextActionIndex) {
				nextEnemy = enemy;
				break;
			}
		}

		if (currentEnemy != null) {
			System.out.println("[" + currentEnemy.getMappingId() + "] 적군 행동 시작");
			int index = Integer.parseInt(currentEnemy.getMappingId().replaceAll("[^0-9]", ""));
			gamePlayer.selectAllyByMid(index);
			executeEnemyAction(currentEnemy);

			currentEnemy.applyStatusEffects();
			currentEnemy.checkStatModifierEffectsEnd();

			acted = true;
		}

		if (acted) {
			boolean currentIsEnemy = currentEnemy != null;
			boolean nextIsEnemyAlive = nextEnemy != null && nextEnemy.isAlive();
			if (currentIsEnemy && nextIsEnemyAlive) {
				scheduler.schedule(this::postActionProcess, 3, TimeUnit.SECONDS);
			} else {
				postActionProcess();
			}
		}
	}

	public void postActionProcess() {
		checkBattleEnd();

		if (!battleEnd) {
			currentActionIndex++;
			if (currentActionIndex > totalUnits) {
				currentActionIndex = 1;
				currentTurn++;
				System.out.println("===== [턴 " + currentTurn + " 시작] =====");
				gamePlayer.notifyObservers();
			}

			gamePlayer.setTurn(currentTurn);
			gamePlayer.setActcount(currentActionIndex);
			proceedNextAction();
		}
	}

	public void executeEnemyAction(EnemyStatusManager enemy) {
		if (!enemy.getBaseStats().isAlive()) {
			System.out.println(enemy.getName() + " 는 사망 상태로 행동을 건너뜁니다.");
			postActionProcess();
			return;
		}

		System.out.println(enemy.getName() + " 가 자동 공격을 수행합니다.");

		gamePlayer.setEnemyActionLocked(true);

		gamePlayer.setEMappingId(enemy.getMappingId());
		executor = new EnemySkillExecutor(gamePlayer);
		executor.execute();

		enemy.applyStatusEffects();
		enemy.checkStatModifierEffectsEnd();

		// 부가효과(상태효과 등) 적용 후 사망 처리
		processDeath();

		gamePlayer.setEnemyActionLocked(false);
	}

	// 아군 행동 완료 후 호출되는 콜백 메서드
	@Override
	public void onActionComplete(GamePlayer player) {
		player.setAllyActionLocked(false);
		AllyStatusManager ally = allyParty.getAllyByMappingID(player.getMappingId());
		if (ally != null) {
			ally.applyStatusEffects();
			ally.checkStatModifierEffectsEnd();
			processDeath(); // 부가효과 적용 후 사망 처리
		}

		postActionProcess();
	}

	private void processDeath() {
		// 아군 사망 처리
		for (AllyStatusManager ally : allyParty.getParty()) {
			if (ally.getBaseStats().getHealth() <= 0 && ally.getBaseStats().isAlive()) {
				ally.getBaseStats().setAlive(false);
				ally.getBaseStats().setAttackable(false);
				System.out.println(ally.getName() + " 이(가) 사망하였습니다.");
			}
		}

		// 적군 사망 처리
		for (EnemyStatusManager enemy : enemyParty.getEnemyParty()) {
			if (enemy.getBaseStats().getHealth() <= 0 && enemy.getBaseStats().isAlive() == true) {
				enemy.getBaseStats().setAlive(false);
				enemy.getBaseStats().setAttackable(false);
				System.out.println(enemy.getName() + " 이(가) 사망하였습니다.");
			}
		}
	}

	private void checkBattleEnd() {
		if (checkAllAlliesDead() || checkAllEnemiesDead()) {
			if (checkAllAlliesDead()) {
				System.out.println("아군 전멸! 전투 종료");
			} else {
				System.out.println("적군 전멸! 전투 종료");

				for (int i = 0; i < 4; i++) {
					ItemDrop.runSingleDrop(gamePlayer);
				}

			}

			restoreAlliesHealth();
			gamePlayer.setEnemyParty(null);
			gamePlayer.selectAllyByMid(1);
			battleEnd = true;
			gamePlayer.changeBattleState();
			shutdownScheduler();
		}
	}

	private boolean checkAllAlliesDead() {
		return allyParty.getParty().stream().allMatch(ally -> ally.getBaseStats().getHealth() <= 0);
	}

	private boolean checkAllEnemiesDead() {
		return enemyParty.getEnemyParty().stream().allMatch(enemy -> enemy.getBaseStats().getHealth() <= 0);
	}

	private void shutdownScheduler() {
		System.out.println("스케줄러 종료 처리");
		scheduler.shutdown();
		try {
			if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
				scheduler.shutdownNow();
			}
		} catch (InterruptedException e) {
			scheduler.shutdownNow();
		}
	}

	private void restoreAlliesHealth() {
		for (AllyStatusManager ally : allyParty.getParty()) {
			if (!ally.getBaseStats().isAlive()) {
				ally.getBaseStats().setAlive(true);
				System.out.println(ally.getName() + " 부활 처리됨");
			}
			ally.getBaseStats().setHealth(ally.getBaseStats().getMaxHealth());
			System.out.println(ally.getName() + " 체력 회복 완료 (" + ally.getBaseStats().getHealth() + "/"
					+ ally.getBaseStats().getMaxHealth() + ")");
		}
	}
}
