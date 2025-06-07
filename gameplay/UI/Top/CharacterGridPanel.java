package gameplay.UI.Top;

import gameplay.GamePlayer;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyParty;
import gameplay.Party.EnemyStatusManager;
import gameplay.UI.UIObserver;
import gameplay.AdditionalEffects.Effect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterGridPanel extends JPanel {

	private GamePlayer gamePlayer;
	private CharacterPanelMaker panelMaker;

	private JPanel characterArea;

	public CharacterGridPanel(GamePlayer gamePlayer) {
		this.gamePlayer = gamePlayer;
		this.panelMaker = new CharacterPanelMaker();

		gamePlayer.addObserver(panelMaker);
		gamePlayer.addObserver(gp -> updateIcons());

		setLayout(null);
		setOpaque(false);

		characterArea = new JPanel(null);
		characterArea.setOpaque(false);
		add(characterArea);

		// 8칸(4 아군 + 4 적군) 미리 자리 만들어두기 (빈 슬롯 포함)
		for (int i = 1; i <= 8; i++) {
			// 임시 mappingId, faction, index는 임시로 설정. 이후 updateIcons에서 업데이트
			String mappingId = "slot" + i;
			CharacterUnitPanel unitPanel = new CharacterUnitPanel(mappingId, "빈칸", i, gamePlayer);
			characterArea.add(unitPanel);
		}

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				layoutCharacterArea();
			}
		});
	}

	private void layoutCharacterArea() {
		int width = getWidth();
		int height = getHeight();

		characterArea.setBounds(0, 0, width, height);

		// 아군 4칸 (좌측부터 오른쪽으로 배치), 적군 4칸 (우측에서 왼쪽으로 배치)
		int totalSlots = 8;
		int slotWidth = width / totalSlots;
		int slotHeight = height;

		Component[] units = characterArea.getComponents();

		for (int i = 0; i < units.length; i++) {
			Component comp = units[i];
			int x;

			// i: 0~7
			// 아군(4): 위치는 4 3 2 1 순으로 좌측 4칸 차지
			// 적군(4): 위치는 1 2 3 4 순으로 우측 4칸 차지

			if (i < 4) {
				// 아군 슬롯 (index 0~3) → mapping to position: 4,3,2,1 (left to right)
				x = i * slotWidth;
			} else {
				// 적군 슬롯 (index 4~7) → position 1~4 (right to left)
				// 적군은 우측 4칸이므로 인덱스 4~7
				x = (i) * slotWidth;
			}

			comp.setBounds(x, 0, slotWidth, slotHeight);

			if (comp instanceof CharacterUnitPanel cup) {
				cup.resizeInnerComponents(slotWidth, slotHeight);
			}
		}
	}

	private void updateIcons() {
		Component[] units = characterArea.getComponents();

		// 아군 위치 4,3,2,1 에 맞게 배열: index 0~3
		// 적군 위치 1,2,3,4 에 맞게 배열: index 4~7

		// 아군 위치별 매핑
		// position -> status manager (아군)
		List<AllyStatusManager> allyList = new ArrayList<>();
		if (gamePlayer.getAllyParty() != null) {
			allyList = gamePlayer.getAllyParty().getParty();
		}

		// 적군 위치별 매핑
		List<EnemyStatusManager> enemyList = new ArrayList<>();
		if (gamePlayer.getEnemyParty() != null) {
			enemyList = gamePlayer.getEnemyParty().getEnemyParty();
		}

		// 아군 위치 -> status
		AllyStatusManager[] allyByPos = new AllyStatusManager[4];
		for (AllyStatusManager ally : allyList) {
			int pos = ally.getPosition();
			if (pos >= 1 && pos <= 4) {
				allyByPos[pos - 1] = ally;
			}
		}

		// 적군 위치 -> status
		EnemyStatusManager[] enemyByPos = new EnemyStatusManager[4];
		for (EnemyStatusManager enemy : enemyList) {
			int pos = enemy.getPosition();
			if (pos >= 1 && pos <= 4) {
				enemyByPos[pos - 1] = enemy;
			}
		}

		for (int i = 0; i < units.length; i++) {
			Component comp = units[i];
			if (!(comp instanceof CharacterUnitPanel))
				continue;

			CharacterUnitPanel cup = (CharacterUnitPanel) comp;

			if (i < 4) {
				// 아군 슬롯 (index 0~3)
				// 아군은 위치 4,3,2,1 → index 0=pos4, 1=pos3, 2=pos2, 3=pos1
				int pos = 4 - i;
				AllyStatusManager allyStatus = (pos >= 1 && pos <= 4) ? allyByPos[pos - 1] : null;

				if (allyStatus != null) {
					cup.setFaction("아군");
					cup.setMappingId(allyStatus.getMappingId());
					cup.updateStatusTooltips(allyStatus.getEffects());
					setButtonIcon(cup.getButton(), allyStatus.getBaseStats().getImagePath());
					gamePlayer.setFieldInfoAt(i+1,allyStatus.getMappingId());
				} else {
					cup.setFaction("빈칸");
					cup.setMappingId("");
					cup.getButton().setIcon(null);
					cup.clearStatusTooltips();
					gamePlayer.setFieldInfoAt(i+1,"none");
				}

				updateAllyButton(cup);

			} else {
				// 적군 슬롯 (index 4~7)
				// 적군은 위치 1,2,3,4 → index4=pos1, index5=pos2, index6=pos3, index7=pos4
				int pos = i - 3;
				EnemyStatusManager enemyStatus = (pos >= 1 && pos <= 4) ? enemyByPos[pos - 1] : null;

				if (enemyStatus != null) {
					cup.setFaction("적군");
					cup.setMappingId(enemyStatus.getMappingId());
					cup.updateStatusTooltips(enemyStatus.getEffects());
					setButtonIcon(cup.getButton(), enemyStatus.getBaseStats().getImagePath());
					gamePlayer.setFieldInfoAt(i+1,enemyStatus.getMappingId());
				} else {
					cup.setFaction("빈칸");
					cup.setMappingId("");
					cup.getButton().setIcon(null);
					cup.clearStatusTooltips();
					gamePlayer.setFieldInfoAt(i+1,"none");
				}

				updateEnemyButton(cup);
			}
		}
	}

	private void updateAllyButton(CharacterUnitPanel cup) {
		JButton button = cup.getButton();
		String mappingId = cup.getMappingId();
		String selectedId = gamePlayer.getMappingId();

		if (mappingId != null && !mappingId.isEmpty()) {
			if (mappingId.equals(selectedId)) {
				button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
			} else {
				button.setBorder(null);
			}

			// 클릭 리스너 재설정
			for (MouseListener listener : button.getMouseListeners()) {
				button.removeMouseListener(listener);
			}

			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					boolean isAllySelectionLocked = gamePlayer.isAllyActionLocked();

					if (SwingUtilities.isLeftMouseButton(e)) {
						if (isAllySelectionLocked) {
							System.out.println(mappingId + "는 현재 선택할 수 없습니다.");
							return;
						}
						//System.out.println(mappingId + "를 선택합니다.");
						String numberOnly = mappingId.replaceAll("\\D+", ""); // 숫자가 아닌 문자는 모두 제거
						int mid = Integer.parseInt(numberOnly);
						gamePlayer.selectAllyByMid(mid);
						updateIcons();
					} else if (SwingUtilities.isRightMouseButton(e)) {
						System.out.println(mappingId + "를 타깃으로 지정합니다.");
						gamePlayer.setTargetMappingId(mappingId, true);
						updateIcons();
					}
				}
			});

			button.setEnabled(true);
		} else {
			button.setBorder(null);
			button.setEnabled(false);

			// 클릭 리스너 제거
			for (MouseListener listener : button.getMouseListeners()) {
				button.removeMouseListener(listener);
			}
		}
	}

	private void updateEnemyButton(CharacterUnitPanel cup) {
		JButton button = cup.getButton();
		String mappingId = cup.getMappingId();

		button.setBorder(null);
		boolean enemyActionLocked = gamePlayer.isEnemyActionLocked();

		if (mappingId != null && !mappingId.isEmpty()) {
			if (enemyActionLocked) {
				button.setEnabled(false);
			} else {
				button.setEnabled(true);
			}

			// 클릭 리스너 재설정
			for (MouseListener listener : button.getMouseListeners()) {
				button.removeMouseListener(listener);
			}

			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (enemyActionLocked) {
						System.out.println("적 행동 중에는 조작이 제한됩니다.");
						return;
					}

					if (SwingUtilities.isLeftMouseButton(e)) {
						//System.out.println(mappingId + "를 선택합니다.");
						String numberOnly = mappingId.replaceAll("\\D+", ""); // 숫자가 아닌 문자는 모두 제거
						int mid = Integer.parseInt(numberOnly);
						gamePlayer.selectEnemyByMid(mid);
						updateIcons();
					} else if (SwingUtilities.isRightMouseButton(e)) {
						System.out.println(mappingId + "를 타깃으로 지정합니다.");
						gamePlayer.setTargetMappingId(mappingId, true);
						updateIcons();
					}
				}
			});

		} else {
			button.setEnabled(false);

			// 클릭 리스너 제거
			for (MouseListener listener : button.getMouseListeners()) {
				button.removeMouseListener(listener);
			}
		}
	}

	private void setButtonIcon(JButton button, String imgPath) {
		if (imgPath == null || imgPath.isEmpty()) {
			button.setIcon(null);
			return;
		}

		int btnWidth = button.getWidth() > 0 ? button.getWidth() : 100;
		int btnHeight = button.getHeight() > 0 ? button.getHeight() : 100;

		int iconWidth = (int) (btnWidth * 0.7);
		int iconHeight = (int) (btnHeight * 0.6);

		Icon currentIcon = button.getIcon();
		if (currentIcon instanceof ImageIcon) {
			ImageIcon currentImageIcon = (ImageIcon) currentIcon;
			if (currentImageIcon.getDescription() != null && currentImageIcon.getDescription().equals(imgPath)) {
				return; // 동일 이미지면 재설정 안 함
			}
		}

		ImageIcon icon = createScaledIcon(imgPath, iconWidth, iconHeight);
		icon.setDescription(imgPath);
		button.setIcon(icon);
	}

	private ImageIcon createScaledIcon(String path, int width, int height) {
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage();
		Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImg);
	}

	private class CharacterUnitPanel extends JPanel {
		private JButton button;
		private JPanel infoPanel;
		private JPanel statusPanel;
		private String mappingId;
		private String faction;

		// 라벨 변경: 상태, 버프, 디버프, 특수
		private final String[] statusLabels = { "상태", "버프", "디벞", "특수" };
		private JButton[] statusButtons = new JButton[4];

		public CharacterUnitPanel(String mappingId, String faction, int index, GamePlayer gamePlayer) {
			this.mappingId = mappingId;
			this.faction = faction;

			setLayout(null);
			setOpaque(false);

			button = new JButton(mappingId + " - " + faction);
			button.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			button.setOpaque(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(true);
			button.setHorizontalTextPosition(SwingConstants.CENTER);
			button.setVerticalTextPosition(SwingConstants.BOTTOM);
			add(button);

			infoPanel = panelMaker.create(mappingId, faction, gamePlayer);
			infoPanel.setOpaque(false);
			add(infoPanel);

			statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
			statusPanel.setOpaque(false);
			add(statusPanel);

			for (int i = 0; i < statusLabels.length; i++) {
				String label = statusLabels[i];
				JButton statusBtn = new JButton(label);
				statusBtn.setPreferredSize(new Dimension(32, 24));
				statusBtn.setMargin(new Insets(0, 0, 0, 0));
				statusBtn.setFocusable(false);
				statusBtn.setBackground(null);
				statusBtn.setOpaque(true);
				statusBtn.setToolTipText("");

				statusPanel.add(statusBtn);
				statusButtons[i] = statusBtn;
			}
		}

		public void setMappingId(String mappingId) {
			this.mappingId = mappingId;
			button.setText(mappingId + " - " + faction);
		}

		public void setFaction(String faction) {
			this.faction = faction;
			button.setForeground("아군".equals(faction) ? Color.WHITE : "적군".equals(faction) ? Color.RED : Color.GRAY);
			button.setText(mappingId.isEmpty() ? "빈칸" : mappingId + " - " + faction);
		}

		public String getMappingId() {
			return mappingId;
		}

		public JButton getButton() {
			return button;
		}

		public void resizeInnerComponents(int width, int height) {
			int statusHeight = 40;
			int buttonHeight = height - statusHeight;

			button.setBounds(0, 0, width, buttonHeight);
			infoPanel.setBounds(0, 0, width, buttonHeight);
			statusPanel.setBounds(0, buttonHeight, width, statusHeight);
		}

		public void updateStatusTooltips(List<Effect> effects) {
			if (effects == null || effects.isEmpty()) {
				clearStatusTooltips();
				return;
			}

			List<String> statusList = new ArrayList<>();
			List<String> buffList = new ArrayList<>();
			List<String> debuffList = new ArrayList<>();
			List<String> specialList = new ArrayList<>();

			for (Effect effect : effects) {
				String formatted = effect.getName() + " ( 위력 : " + effect.getPower() + ", " + effect.getDuration()
						+ "턴)";
				String name = effect.getName();

				if (name.contains("증가")) {
					buffList.add(formatted);
				} else if (name.contains("감소")) {
					debuffList.add(formatted);
				} else if (name.contains("출혈") || name.contains("중독") || name.contains("화상")) {
					statusList.add(formatted);
				} else {
					specialList.add(formatted); // 특수효과 (기능 없는 문자열 포함 등)
				}
			}

			String[] tooltipTexts = new String[4];
			tooltipTexts[0] = statusList.isEmpty() ? "" : String.join("\n", statusList);
			tooltipTexts[1] = buffList.isEmpty() ? "" : String.join("\n", buffList);
			tooltipTexts[2] = debuffList.isEmpty() ? "" : String.join("\n", debuffList);
			tooltipTexts[3] = specialList.isEmpty() ? "" : String.join("\n", specialList);

			for (int i = 0; i < 4; i++) {
				statusButtons[i].setToolTipText("<html>" + tooltipTexts[i].replace("\n", "<br>") + "</html>");
			}
		}

		public void clearStatusTooltips() {
			for (JButton btn : statusButtons) {
				btn.setToolTipText("");
			}
		}
	}
	public class CharacterPanelMaker implements UIObserver {

	    private final Map<String, JPanel> panelMap = new HashMap<>();

	    public JPanel create(String mappingId, String faction, GamePlayer gamePlayer) {
	        JPanel charPanel = new JPanel(new BorderLayout());
	        charPanel.setBorder(BorderFactory.createTitledBorder(mappingId));
	        charPanel.setOpaque(false);

	        // 체력 바
	        JProgressBar healthBar = new JProgressBar(0, 100);
	        healthBar.setStringPainted(true);
	        healthBar.setOpaque(false);
	        healthBar.setBackground(new Color(0, 0, 0, 0));
	        healthBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());
	        charPanel.add(healthBar, BorderLayout.NORTH);

	        JLabel factionLabel = new JLabel(faction, SwingConstants.CENTER);
	        factionLabel.setOpaque(false);
	        factionLabel.setForeground(Color.YELLOW);
	        charPanel.add(factionLabel, BorderLayout.CENTER);

	        panelMap.put(mappingId, charPanel);
	        updateCharacterPanel(gamePlayer); // 초기값 반영
	        return charPanel;
	    }

	    @Override
	    public void update(GamePlayer gamePlayer) {
	        updateCharacterPanel(gamePlayer);
	    }

	    private void updateCharacterPanel(GamePlayer gamePlayer) {
	        JPanel charPanel;
	        int healthPercent = 0;

	        for (int i = 1; i <= 8; i++) {
	            charPanel = panelMap.get("slot" + i);
	            if (charPanel == null) {
	                continue; // 해당 슬롯이 없으면 건너뜀
	            }

	            JProgressBar healthBar = (JProgressBar) charPanel.getComponent(0);
	            String mappingId = gamePlayer.getFieldInfoAt(i);

	            if (mappingId == null || mappingId.isEmpty() || mappingId.equals("slot")) {
	                healthPercent = 0;
	                healthBar.setValue(0);
	                healthBar.setString(""); // 빈 문자열 처리
	            } else if (mappingId.charAt(0) == 'A') {
	                AllyStatusManager ally = gamePlayer.getAllyParty().getAllyByMappingID(mappingId);
	                if (ally != null) {
	                    int currentHp = ally.getBaseStats().getHealth();
	                    int maxHp = ally.getBaseStats().getMaxHealth();
	                    healthPercent = getHealthPercent(currentHp, maxHp);
	                    if (!ally.getBaseStats().isAlive()) {
	                        healthBar.setString("사망");
	                        healthBar.setValue(0);
	                    } else {
	                        healthBar.setString(currentHp + " / " + maxHp);
	                        healthBar.setValue(healthPercent);
	                    }
	                } else {
	                    healthBar.setValue(0);
	                    healthBar.setString(""); // ally == null
	                }
	            } else {
	                EnemyParty enemyParty = gamePlayer.getEnemyParty(); // null 체크
	                if (enemyParty != null) {
	                    EnemyStatusManager enemy = enemyParty.getEnemyByMappingID(mappingId);
	                    if (enemy != null) {
	                        int currentHp = enemy.getBaseStats().getHealth();
	                        int maxHp = enemy.getBaseStats().getMaxHealth();
	                        healthPercent = getHealthPercent(currentHp, maxHp);
	                        if (!enemy.isAlive()) {
	                            healthBar.setString("사망");
	                            healthBar.setValue(0);
	                        } else {
	                            healthBar.setString(currentHp + " / " + maxHp);
	                            healthBar.setValue(healthPercent);
	                        }
	                    } else {
	                        healthBar.setValue(0);
	                        healthBar.setString(""); // enemy == null
	                    }
	                } else {
	                    healthBar.setValue(0);
	                    healthBar.setString(""); // enemyParty == null
	                }
	            }
	        }
	    }




	    private int getHealthPercent(int health, int maxHealth) {
	        return (maxHealth > 0) ? (int) ((double) health / maxHealth * 100) : 0;
	    }
	}


}
