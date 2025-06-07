package gameplay.UI.Bottom.Right;

import gameplay.GamePlayer;
import gameplay.UI.UIObserver;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel implements UIObserver {
	private final GamePlayer gamePlayer;
	private int[][] mapData;
	// mapData 각 셀의 의미:
	// 0 - 이동 불가 영역 또는 미정의 공간
	// 1 - 이동 가능 방 (미방문)
	// 2 - 이동 가능 방 (방문함)
	// 3 - 현재 플레이어 위치
	private final RoomButton[][] buttons = new RoomButton[8][8];

	private int currentX = -1;
	private int currentY = -1;

	private final JLabel noDataLabel = new JLabel("지도 데이터가 없습니다.", SwingConstants.CENTER);
	private final JPanel gridPanel = new JPanel(new GridLayout(8, 8, 10, 10));
	private final JPanel cards = new JPanel(new CardLayout()); // 카드 레이아웃 패널

	private static final String CARD_GRID = "GRID";
	private static final String CARD_NODATA = "NODATA";

	public MapPanel(GamePlayer gamePlayer) {
		this.gamePlayer = gamePlayer;
		setLayout(new BorderLayout());

		gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		gridPanel.setPreferredSize(new Dimension(620, 620));
		initButtons(gridPanel);

		noDataLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		noDataLabel.setForeground(Color.RED);

		cards.add(gridPanel, CARD_GRID);
		cards.add(noDataLabel, CARD_NODATA);

		add(cards, BorderLayout.CENTER);

		update(gamePlayer);
		gamePlayer.addObserver(this);
	}

	private void initButtons(JPanel gridPanel) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				final int x = i, y = j;
				RoomButton btn = new RoomButton();
				btn.addActionListener(e -> {
					if (canMoveTo(x, y)) {
						moveTo(x, y);
					}
				});
				buttons[i][j] = btn;
				gridPanel.add(btn);
			}
		}
	}

	private boolean canMoveTo(int x, int y) {
		if (mapData == null)
			return false;

	    // 이벤트 중이거나 배틀 중이면 이동 불가
	    if (gamePlayer.isOnEvent() || gamePlayer.isBattleState()) {
	        return false;
	    }
		

		return isAdjacent(currentX, currentY, x, y) && (mapData[x][y] == 1 || mapData[x][y] == 2);
	}

	private boolean isAdjacent(int x1, int y1, int x2, int y2) {
		return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) == 1;
	}

	private void moveTo(int x, int y) {
	    if (mapData == null) {
	        showNoDataMessage(true);
	        return;
	    }
	    showNoDataMessage(false);

	    if (currentX != -1 && currentY != -1) {
	        mapData[currentX][currentY] = 2;
	    }

	    // 이동하려는 위치의 원래 상태 저장
	    int originalRoomType = mapData[x][y];
	    String originalRoomTypeStr = switch (originalRoomType) {
	        case 1 -> "미방문 방";
	        case 2 -> "방문한 방";
	        case 3 -> "현재 위치";
	        case 0 -> "이동 불가";
	        default -> "알 수 없음";
	    };

	    // 마지막 남은 1인지 확인
	    if (originalRoomType == 1) {
	        boolean isLastUnvisited = true;
	        for (int i = 0; i < mapData.length; i++) {
	            for (int j = 0; j < mapData[i].length; j++) {
	                if (!(i == x && j == y) && mapData[i][j] == 1) {
	                    isLastUnvisited = false;
	                    break;
	                }
	            }
	        }
	        if (isLastUnvisited) {
	            originalRoomTypeStr = "완료";  // 이동하려는 위치가 마지막 1인 경우
	        }
	    }

	    // 위치 이동
	    currentX = x;
	    currentY = y;
	    mapData[currentX][currentY] = 3;

	    gamePlayer.setMapData(mapData, originalRoomTypeStr);

	    updateButtonStates();
	    repaint();
	}



	private void updateButtonStates() {
		if (mapData == null)
			return;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				RoomButton btn = buttons[i][j];
				btn.setRoomType(mapData[i][j]); // 꼭 호출해서 타입도 갱신

				if (i == currentX && j == currentY) {
					btn.setSelected(true);
					btn.setSelectable(false);
				} else if (isAdjacent(currentX, currentY, i, j) && (mapData[i][j] == 1 || mapData[i][j] == 2)) {
					btn.setSelected(false);
					btn.setSelectable(true);
				} else {
					btn.setSelected(false);
					btn.setSelectable(false);
				}
			}
		}
	}

	private void showNoDataMessage(boolean show) {
		CardLayout cl = (CardLayout) cards.getLayout();
		if (show) {
			cl.show(cards, CARD_NODATA);
		} else {
			cl.show(cards, CARD_GRID);
		}
		repaint();
	}

	@Override
	public void update(GamePlayer player) {
		this.mapData = player.getMapData();
		if (mapData == null) {
			showNoDataMessage(true);
			currentX = -1;
			currentY = -1;
			return;
		} else {
			showNoDataMessage(false);
		}

		// 현재 위치(3)를 찾아 currentX, currentY에 저장
		currentX = -1;
		currentY = -1;
		outer: for (int i = 0; i < mapData.length; i++) {
			for (int j = 0; j < mapData[i].length; j++) {
				if (mapData[i][j] == 3) {
					currentX = i;
					currentY = j;
					break outer;
				}
			}
		}

		// 버튼에 roomType 세팅 및 선택 상태 갱신
		for (int i = 0; i < mapData.length; i++) {
			for (int j = 0; j < mapData[i].length; j++) {
				buttons[i][j].setRoomType(mapData[i][j]);
				buttons[i][j].setSelected(i == currentX && j == currentY);
				buttons[i][j].setSelectable(
						isAdjacent(currentX, currentY, i, j) && (mapData[i][j] == 1 || mapData[i][j] == 2));
			}
		}

		repaint();
	}
}
