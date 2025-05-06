public class StatusEffect implements Effect {
    private String name;
    private int power;       // 위력 (양수로 처리되도록 절대값 적용)
    private int duration;    // 지속 턴 수

    public StatusEffect(String name, int power, int duration) {
        this.name = name;
        this.power = Math.abs(power);  // power가 음수이면 절대값으로 변환
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
        return "StatusEffect";  // 상태이상 효과
    }

    // 상태이상 갱신 처리 (중복 상태이상의 위력 합산 및 지속시간 갱신)
    public static StatusEffect mergeEffects(StatusEffect existingEffect, StatusEffect newEffect) {
        if (existingEffect.getName().trim().equalsIgnoreCase(newEffect.getName().trim())) {
         // 중복 상태이상의 위력 합산
         int newPower = existingEffect.getPower() + newEffect.getPower();
         // 지속시간 갱신
         int newDuration = Math.max(existingEffect.getDuration(), newEffect.getDuration());
         // 새로운 상태이상 객체 반환
         return new StatusEffect(existingEffect.getName(), newPower, newDuration);
    }
        return null;
}


    @Override
    public void onTurnEnd(AllyStatusManager target) {
        if (isRecoveryEffect()) {
            // 회복 효과: 위력을 양수 그대로 사용하여 체력 증가
            int hp = target.getBaseStats().getHealth();
            target.getBaseStats().setHealth(Math.min(target.getBaseStats().getMaxHealth(), hp + power));
        } else {
            // 일반 상태이상: 체력 감소 (여기서는 power가 양수인 경우의 처리로 체력을 감소시킴)
            int hp = target.getBaseStats().getHealth();
            target.getBaseStats().setHealth(Math.max(0, hp - power)); // 체력 0 이하 방지
        }
        
        // 지속시간 감소
        duration--;
    }

    // 회복 효과 여부 체크 (이름에 "회복"이 포함되어 있는지 여부)
    private boolean isRecoveryEffect() {
        return name.contains("회복");
    }

    @Override
    public boolean isExpired() {
        return duration <= 0;
    }
}
