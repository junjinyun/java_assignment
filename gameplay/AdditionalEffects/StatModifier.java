public class StatModifierEffect implements Effect {
    private String name;
    private int power;
    private int duration;
    private boolean applied = false;
    private Map<String, Integer> originalStatValues = new HashMap<>();

    public StatModifierEffect(String name, int power, int duration) {
        this.name = name;
        this.power = power;
        this.duration = duration;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String getType() {
        return "StatModifier";
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private List<String> extractStatNames() {
        List<String> stats = new ArrayList<>();
        if (name.contains("공")) stats.add("damage");
        if (name.contains("방")) stats.add("defense");
        if (name.contains("회피")) stats.add("evasion");
        if (name.contains("속도")) stats.add("speed");
        return stats;
    }

    @Override
    public void apply(AllyStatusManager target) {
        if (applied) return;

        List<String> statNames = extractStatNames();
        List<Effect> toRemove = new ArrayList<>();

        for (Effect effect : target.getEffects()) {
            if (!(effect instanceof StatModifierEffect other)) continue;

            List<String> otherStats = other.extractStatNames();

            for (String stat : statNames) {
                if (otherStats.contains(stat)) {
                    // 상반 효과 처리: 양수/음수 차이가 나면 서로 제거
                    if ((this.power > 0 && other.getPower() < 0) || (this.power < 0 && other.getPower() > 0)) {
                        toRemove.add(other);
                        target.removeEffect(this); // 본인도 제거 요청
                        return; // 더 이상 적용 안 함
                    }

                    // 동일 방향 효과 처리: 같은 방향이면 더 큰 값으로 갱신
                    if ((this.power > 0 && other.getPower() > 0) || (this.power < 0 && other.getPower() < 0)) {
                        // 더 큰 power 값으로 갱신
                        if (this.power > other.getPower()) {
                            other.setPower(this.power);  // 더 큰 power 값으로 갱신
                        }

                        // 더 긴 duration 값으로 갱신
                        if (this.duration > other.getDuration()) {
                            other.setDuration(this.duration);  // 더 긴 duration으로 갱신
                        }

                        // 더 작은 값은 삭제 처리
                        toRemove.add(this);
                        applied = true;
                        return;
                    }
                }
            }
        }

        for (Effect e : toRemove) {
            target.removeEffect(e);  // 삭제된 효과 제거
        }

        // 본 효과 적용
        for (String stat : statNames) {
            applyToStat(target, stat, true);
        }

        applied = true;
    }

    @Override
    public void onTurnEnd(AllyStatusManager target) {
        duration--;
        if (isExpired()) {
            for (String stat : extractStatNames()) {
                applyToStat(target, stat, false);
            }
        }
    }

    private void applyToStat(AllyStatusManager target, String stat, boolean apply) {
        if (apply) {
            switch (stat) {
                case "damage" -> {
                    int original = target.getBaseStats().getDamage();
                    originalStatValues.put("damage", original);
                    target.getBaseStats().setDamage((int)(original * (1 + power / 100.0)));
                }
                case "defense" -> {
                    int original = target.getBaseStats().getDefense();
                    originalStatValues.put("defense", original);
                    target.getBaseStats().setDefense((int)(original * (1 + power / 100.0)));
                }
                case "evasion" -> {
                    int original = target.getBaseStats().getEvasion();
                    originalStatValues.put("evasion", original);
                    target.getBaseStats().setEvasion((int)(original * (1 + power / 100.0)));
                }
                case "speed" -> {
                    int original = target.getCurrentSpeed();
                    originalStatValues.put("speed", original);
                    target.setCurrentSpeed((int)(original * (1 + power / 100.0)));
                }
            }
        } else {
            Integer original = originalStatValues.get(stat);
            if (original != null) {
                switch (stat) {
                    case "damage" -> target.getBaseStats().setDamage(original);
                    case "defense" -> target.getBaseStats().setDefense(original);
                    case "evasion" -> target.getBaseStats().setEvasion(original);
                    case "speed" -> target.setCurrentSpeed(original);
                }
            }
        }
    }

    @Override
    public boolean isExpired() {
        return duration <= 0;
    }
}
