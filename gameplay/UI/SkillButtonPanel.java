package gameplay.UI;

import gameplay.GamePlayer;
import gameplay.Party.AllyStatusManager;
import loaddata.AllySkills;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SkillButtonPanel extends JPanel implements UIObserver {
    private GamePlayer gamePlayer;
    private ButtonGroup skillGroup;
    private final ImageIcon activeIcon = new ImageIcon("src/image/Interface/green.png");
    private final ImageIcon inactiveIcon = new ImageIcon("src/image/Interface/red.png");
    private SkillInfoPanel skillInfoPanel; // SkillInfoPanel 인스턴스 추가

    public SkillButtonPanel(GamePlayer gamePlayer, SkillInfoPanel skillInfoPanel) {
        this.gamePlayer = gamePlayer;
        this.skillInfoPanel = skillInfoPanel;  // SkillInfoPanel을 생성자에서 받음
        skillGroup = new ButtonGroup();  // ButtonGroup을 클래스 레벨에서 초기화

        // FlowLayout으로 설정하고 좌우 간격 40px, 위 아래 간격 0px 설정
        setLayout(new FlowLayout(FlowLayout.LEFT, 40, 0));  
        setBorder(BorderFactory.createEtchedBorder());

        // 옵저버로 등록
        gamePlayer.addObserver(this);

        updateSkillButtons();  // 초기화 시에도 버튼 업데이트
    }

    @Override
    public void update(GamePlayer gamePlayer) {
        updateSkillButtons();  // 상태 변경 시 스킬 버튼 업데이트
    }

    public void updateSkillButtons() {
        if (gamePlayer == null || gamePlayer.getAllyParty() == null)
            return;

        ArrayList<AllySkills> skills = new ArrayList<>();
        for (int i = 1; i <= gamePlayer.getAllyParty().getParty().size(); i++) {
            AllyStatusManager statusManager = gamePlayer.getAllyParty().getAllyByMappingID(gamePlayer.getMappingId());
            if (statusManager != null) {
                statusManager.getSkillList().forEach(skill -> skills.add(skill)); // 스킬 객체 추가
            }
        }

        removeAll();
        for (int i = 0; i < 6; i++) {
            if (i < skills.size()) {
                final AllySkills skill = skills.get(i); // 스킬 객체 저장
                JRadioButton skillButton = new JRadioButton();

                // 아이콘만 설정하고 텍스트 제거
                skillButton.setIcon(inactiveIcon);  // 기본 비활성화 아이콘 설정
                skillButton.setSelectedIcon(activeIcon);  // 선택된 상태에서의 아이콘 설정

                // 버튼의 크기 설정
                skillButton.setPreferredSize(new Dimension(100, 100));

                // 버튼 선택 시 해당 스킬 정보를 SkillInfoPanel에 표시
                skillButton.addActionListener(e -> handleSkillButtonSelection(skillButton, skill));

                skillGroup.add(skillButton);  // ButtonGroup에 추가
                add(skillButton);  // 버튼을 패널에 추가
            }
        }
        revalidate();
        repaint();
    }

    private void handleSkillButtonSelection(JRadioButton selectedButton, AllySkills skill) {
        // 모든 버튼을 비활성화 아이콘으로 설정
        for (Component component : getComponents()) {
            if (component instanceof JRadioButton) {
                JRadioButton button = (JRadioButton) component;
                // 선택된 버튼만 활성화 상태로, 나머지는 비활성화 상태로
                if (button.equals(selectedButton)) {
                    button.setIcon(activeIcon);  // 선택된 버튼은 green
                } else {
                    button.setIcon(inactiveIcon);  // 다른 버튼들은 red
                }
            }
        }

        // 선택된 스킬 정보를 SkillInfoPanel에 전달하여 표시
        skillInfoPanel.updateSkillDataDisplay(skill);
    }
}
