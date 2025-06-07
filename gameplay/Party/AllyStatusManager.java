package gameplay.Party;

import loaddata.Ally;
import loaddata.AllySkills;

import java.util.ArrayList;
import java.util.List;
import gameplay.AdditionalEffects.*;

// 아군 캐릭터의 상태 및 상태이상을 관리하는 클래스
public class AllyStatusManager {
	private Ally baseStats; // 기본 스탯 (JSON에서 불러온 원본 정보)
	// private List<StatusEffect> statusEffects; // 현재 적용 중인 상태이상 목록
	private int position; // 파티 내 위치 (1~4)
	private int currentSpeed; // 전투 중 동적인 현재 속도
	private int actionOrder; // 행동 순서 (우선순위)
	private String mappingId;// 아군 객체와 인스턴스로 연결하기 위한 변수
	private List<AllySkills> skillList; // 캐릭터가 가지고 있는 스킬을 저장하는 리스트
	private List<AllySkills> selectedSkills; // 캐릭터가 소지한 스킬 중 전투에서 사용할 스킬
	private List<StatusEffect> statusEffects;
	private List<StatModifierEffect> statModifierEffects;
	private List<SpecialEffect> specialEffects;

	// 스킬을 실제로 저장만 하며 지정하는 기능은 별도의 클래스로 분리하여 생성할 예정

	public AllyStatusManager(Ally ally, String mappingId) {
		this.baseStats = ally;
		this.position = Integer.parseInt(mappingId.replaceAll("[^0-9]", ""));
		this.mappingId = mappingId;
		this.skillList = new ArrayList<>(); // 각 캐릭터는 개별적인 스킬 리스트를 가짐
		this.selectedSkills = new ArrayList<>();
		this.statusEffects = new ArrayList<>();
		this.statModifierEffects = new ArrayList<>();
		this.specialEffects = new ArrayList<>();
	}
	
	public void addEffects(String data) {
	    String[] parts = data.trim().split("\\s+");

	    if (parts.length == 2) {
	        // 형식: 이름 숫자 → SpecialEffect 처리
	        String name = parts[0];
	        int power;

	        try {
	            power = Integer.parseInt(parts[1]);
	        } catch (NumberFormatException e) {
	            System.out.println("숫자 변환 실패: " + data);
	            return;
	        }

	        SpecialEffect special = new SpecialEffect(name, power);
	        addSpecialEffect(special);
	        return;
	    }

	    if (parts.length == 3) {
	        // 형식: 이름 숫자 숫자 → 기존대로 처리
	        String name = parts[0];
	        int power, duration;

	        try {
	            power = Integer.parseInt(parts[1]);
	            duration = Integer.parseInt(parts[2]);
	        } catch (NumberFormatException e) {
	            System.out.println("숫자 변환 실패: " + data);
	            return;
	        }

	        if (name.contains("증가") || name.contains("감소")) {
	            StatModifierEffect buff = new StatModifierEffect(name, power, duration);
	            addStatModifierEffect(buff);
	        } else {
	            StatusEffect effect = new StatusEffect(name, power, duration);
	            addStatusEffect(effect);
	        }
	        return;
	    }

	    System.out.println("잘못된 형식의 데이터입니다: " + data);
	}

    // 특수효과 추가
    public void addSpecialEffect(SpecialEffect newEffect) {
        for (int i = 0; i < specialEffects.size(); i++) {
            SpecialEffect existingEffect = specialEffects.get(i);
            if (existingEffect.canMerge(newEffect)) {
                existingEffect.merge(newEffect, this);
                return;
            }
        }
        specialEffects.add(newEffect);
    }

    // 특수효과 종료 체크 및 제거 (턴 종료 시 호출)
    public void checkSpecialEffectsEnd() {
        List<SpecialEffect> expired = new ArrayList<>();
        for (SpecialEffect effect : specialEffects) {
            effect.onTurnEnd(this);
            if (effect.isExpired()) {
                expired.add(effect);
            }
        }
        specialEffects.removeAll(expired);
    }

    // 특수효과 출력 (위력 없음)
    public void printSpecialEffects() {
        if (specialEffects.isEmpty()) {
            System.out.println("특수효과: 없음");
        } else {
            for (SpecialEffect effect : specialEffects) {
                System.out.println("특수효과: " + effect.getName() + ", 지속시간: " + effect.getDuration());
            }
        }
    }

    // 특수효과 제거
    public void removeSpecialEffect(SpecialEffect effect) {
        specialEffects.remove(effect);
    }

    // 특수효과 리스트 반환
    public List<SpecialEffect> getSpecialEffects() {
        return specialEffects;
    }
    public boolean hasSpecialEffect(String effectName) {
        for (SpecialEffect effect : specialEffects) {
            if (effect.getName().equals(effectName)) {
                return true;
            }
        }
        return false;
    }

	
	public void addStatModifierEffect(StatModifierEffect newEffect) {
		// 새 효과가 상반되는 기존 효과가 있는지 확인
		List<StatModifierEffect> toRemove = new ArrayList<>();

		String newStat = newEffect.getStatName(); // 예: "방어"
		String newType = newEffect.getEffectType(); // 예: "증가" or "감소"

		for (StatModifierEffect existing : statModifierEffects) {
			String existingStat = existing.getStatName();
			String existingType = existing.getEffectType();

			// 상반 조건: 같은 스탯 + 효과 타입 다름 (증가 vs 감소)
			if (newStat.equals(existingStat) && !newType.equals(existingType)) {
				toRemove.add(existing); // 상반 효과 제거 대상
			}
		}

		if (!toRemove.isEmpty()) {
			for (StatModifierEffect effect : toRemove) {
				effect.forceRemove(this); // 원본 스탯 복원
				statModifierEffects.remove(effect);
			}
			return;
		}

		// 병합 가능한 기존 효과가 있으면 병합
		for (Effect e : statModifierEffects) {
			if (e instanceof StatModifierEffect existing && existing.canMerge(newEffect)) {
				existing.merge(newEffect, this);
				return;
			}
		}

		// 아무 문제 없으면 적용
		newEffect.apply(this);
		statModifierEffects.add(newEffect);
	}

	public void checkStatModifierEffectsEnd() {
		List<StatModifierEffect> expired = new ArrayList<>();
		for (StatModifierEffect effect : statModifierEffects) {
			effect.onTurnEnd(this);
			if (effect.isExpired()) {
				expired.add(effect);
			}
		}
		statModifierEffects.removeAll(expired);
	}

	public void printStatModifierEffects() {
		if (statModifierEffects.isEmpty()) {
			System.out.println("스텟 증감 효과: 없음");
		} else {
			for (StatModifierEffect effect : statModifierEffects) {
				System.out.println("스텟 증감: " + effect.getName() + ", 위력: " + effect.getPower() + ", 지속시간: "
						+ effect.getDuration());
			}
		}
	}

	public void addStatusEffect(StatusEffect newEffect) {
		for (int i = 0; i < statusEffects.size(); i++) {
			StatusEffect existingEffect = statusEffects.get(i);
			StatusEffect mergedEffect = StatusEffect.mergeEffects(existingEffect, newEffect);
			if (mergedEffect != null) {
				statusEffects.set(i, mergedEffect);
				return;
			}
		}
		statusEffects.add(newEffect);
	}

	public void applyStatusEffects() {
	    int totalRecovery = 0;
	    int totalDamage = 0;
	    List<StatusEffect> expired = new ArrayList<>();

	    for (StatusEffect effect : statusEffects) {
	        if (effect.isRecoveryEffect()) {
	            totalRecovery += effect.getPower();
	        } else {
	            totalDamage += effect.getPower();
	        }
	        effect.setDuration(effect.getDuration() - 1);
	        if (effect.isExpired()) {
	            expired.add(effect);
	        }
	    }

	    int netEffect = totalRecovery - totalDamage;
	    int currentHp = baseStats.getHealth();
	    int maxHp = baseStats.getMaxHealth();

	    if (netEffect > 0) {
	        baseStats.setHealth(Math.min(maxHp, currentHp + netEffect));
	    } else if (netEffect < 0) {
	        baseStats.setHealth(Math.max(0, currentHp + netEffect)); // netEffect 음수이므로 더함
	    }

	    statusEffects.removeAll(expired);
	}

	public void printStatusEffects() {
		if (statusEffects.isEmpty()) {
			System.out.println("상태이상: 없음");
		} else {
			for (StatusEffect effect : statusEffects) {
				System.out.println(
						"상태이상: " + effect.getName() + ", 위력: " + effect.getPower() + ", 지속시간: " + effect.getDuration());
			}
		}
	}

	public void loadSkills(AllySkills skill) {
		int index = -1;

		// 이미 같은 이름의 스킬이 있는지 확인
		for (int i = 0; i < skillList.size(); i++) {
			if (skillList.get(i).getName().equalsIgnoreCase(skill.getName())) {
				index = i;
				break;
			}
		}

		if (index >= 0) {
			skillList.remove(index); // 같은 이름의 스킬이 있으면 제거
			skillList.add(index, skill); // 새로 업데이트된 스킬을 같은 위치에 삽입
		} else {
			skillList.add(skill); // 새 스킬을 추가
		}
	}

	public List<Effect> getEffects() {
		List<Effect> all = new ArrayList<>();
		all.addAll(statModifierEffects);
		all.addAll(statusEffects);
		all.addAll(specialEffects);
		return all;
	}

	public void removeEffect(Effect effect) {
		if (effect instanceof StatModifierEffect) {
			statModifierEffects.remove(effect);
		} else if (effect instanceof StatusEffect) {
			statusEffects.remove(effect);
		}
	}

	public List<StatModifierEffect> getStatModifierEffects() {
		return statModifierEffects;
	}

	public List<AllySkills> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<AllySkills> skillList) {
		this.skillList = skillList;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(int currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public int getActionOrder() {
		return actionOrder;
	}

	public void setActionOrder(int actionOrder) {
		this.actionOrder = actionOrder;
	}

	public Ally getBaseStats() {
		return baseStats;
	}

	public String getName() {
		return baseStats.getName();
	}

	public List<StatusEffect> getStatusEffects() {
		return statusEffects;
	}

	public String getMappingId() {
		return mappingId;
	}

}
