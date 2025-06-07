package gameplay.UI.Bottom.Left;
	
	import javax.swing.*;
	import java.awt.*;
	import loaddata.AllySkills;
	import java.util.List;
	
	public class SkillInfoPanel extends JPanel {
	    private JTextArea skillInfoText;
	    private List<AllySkills> skillsList; // 아군 스킬 리스트
	
	    public SkillInfoPanel(List<AllySkills> skillsList) {
	        this.skillsList = skillsList;
	        
	        setLayout(new BorderLayout());
	        setBorder(BorderFactory.createTitledBorder("스킬 정보"));
	
	        skillInfoText = new JTextArea();
	        skillInfoText.setEditable(false);
	        skillInfoText.setLineWrap(true);
	        skillInfoText.setWrapStyleWord(true);
	        add(new JScrollPane(skillInfoText), BorderLayout.CENTER);
	
	        showDefaultSkillInfo();
	    }
	
	    // 스킬 정보를 갱신하는 메서드
	    public void updateSkillDataDisplay(AllySkills skill) {
	        if (skill == null) {
	            skillInfoText.setText("스킬 정보를 불러오지 못했습니다.");
	            return;
	        }
	
	        StringBuilder info = new StringBuilder();
	        // 첫째 줄: 스킬 이름, 피해량 배율
	        info.append("스킬 이름: ").append(skill.getName())
	            .append("    피해량 배율: ").append(skill.getDamageMultiplier()).append("\n");
	
	        // 둘째 줄: 설명
	        info.append("설명: ").append(skill.getInformation()).append("\n");
	
	        // 셋째 줄: 부가 효과, 광역 여부
	        info.append("부가 효과: ").append(skill.getAEffect())
	            .append("    광역 공격: ").append(skill.isAoE() ? "O" : "X").append("\n");
	
	        // 넷째 줄: 대상 위치, 발동 위치
	        info.append("대상 위치: ").append(String.join(", ", skill.getTargetLocation()))
	            .append("    발동 위치: ").append(String.join(", ", skill.getSkillActivationZone()))
	            .append("\n");
	
	        skillInfoText.setText(info.toString());
	    }
	
	    // 기본 스킬 정보 표시
	    public void showDefaultSkillInfo() {
	        skillInfoText.setText("스킬을 선택하면 정보가 표시됩니다.");
	    }
	
	    // 버튼 번호에 해당하는 스킬 데이터를 표시하는 메서드
	    public void setSkillData(int buttonNumber) {
	        // buttonNumber에 해당하는 스킬 인덱스 구하기 (1~6 -> 0~5)
	        int skillIndex = buttonNumber - 1;
	
	        // 해당 인덱스의 스킬을 로드하여 표시
	        if (skillIndex >= 0 && skillIndex < skillsList.size()) {
	            AllySkills selectedSkill = skillsList.get(skillIndex);
	            updateSkillDataDisplay(selectedSkill);
	        } else {
	            skillInfoText.setText("잘못된 스킬 번호입니다.");
	        }
	    }
	}
