package 팀플;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Mk_Use_Map {
	public static ArrayList<Integer> current_loc = new ArrayList<>();// 현제 위치 저장을 위한 어레이 리스트

	public static int[][] standardmap = new int[8][8];

	public static void mkmap() {

		for (int i = 0; i < standardmap.length; i++)
			for (int x = 0; x < standardmap[i].length; x++)
				standardmap[i][x] = 0;
	}// 0 : 빈 칸, 1 : 이벤트가 발생 하지 않은 방 2 : 빈방(이벤트 없음) 3 : 현제 위치

	public static void mkroom() {
		Integer selected_row_or_col, startlocation;
		ArrayList<Integer> created_row = new ArrayList<Integer>();
		ArrayList<Integer> created_col = new ArrayList<Integer>();
		for (int i = 1; i <= 6; i++)// i+1 값을 이용하여 홀수이면 행, 짝수이면 열을 선택
		{
			Random random = new Random();// Random 객체로 난수 시드 생성
			selected_row_or_col = random.nextInt(8);// 생성할 열 또는 행 지정
			startlocation = random.nextInt(4); // 선택된 행 또는 열에서 처음으로 방을 생성하기 시작할 위치를 선정(0~3)
			if (i % 2 == 1) { // 랜덤한 행에 3~8의 크기의 방 생성
				while (true)
					if (created_row.equals(startlocation)) {
						Random random1 = new Random();// Random 객체로 난수 시드 생성
						selected_row_or_col = random1.nextInt(8);// 생성할 열 또는 행 지정
					} else {
						created_row.add(selected_row_or_col);
						break;
					}
				while (startlocation < 8) {
					standardmap[selected_row_or_col][startlocation] = 1;
					startlocation++;
				}
			} else {// 랜덤한 열에 3~8의 크기의 방 생성
				while (true)
					if (created_col.equals(startlocation)) {
						Random random1 = new Random();// Random 객체로 난수 시드 생성
						selected_row_or_col = random1.nextInt(8);// 생성할 열 또는 행 지정
					} else {
						created_col.add(selected_row_or_col);
						break;
					}
				while (startlocation < 8) {
					standardmap[startlocation][selected_row_or_col] = 1;
					startlocation++;
				}
			}
		}
	}

	public static void loadmap() {
		for (int i = 0; i < standardmap.length; i++) {
			for (int x = 0; x < standardmap[i].length; x++) {
				if (standardmap[i][x] == 1 || standardmap[i][x] == 2)
					System.out.print("o ");
				else if (standardmap[i][x] == 3) {
					System.out.print("x ");
					if (current_loc.isEmpty() == false)
						current_loc.clear();// 현제 위치를 매번 초기화 하기 위해 이미 존재 하는 값을 제거
					current_loc.add(i);
					current_loc.add(x);
				} else
					System.out.print("  ");
			}
			System.out.print("\n");
		}
		System.out.println("현제 좌표 : " + "" + current_loc);
	}

	private static Scanner scan = new Scanner(System.in);

	public static void move_map() {
		int row = current_loc.get(0), col = current_loc.get(1); // 현제 행, 열을 current_loc 에서 가져옴
		boolean top = false, down = false, left = false, right = false; // 현제위치를 기반으로 상, 하, 좌, 우 중에서 방이 존재 하는지 판별하는 변수
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				if (row >= 1)
					if (standardmap[row - 1][col] == 1)
						top = true;
			case 1:
				if (row < 7)
					if (standardmap[row + 1][col] == 1)
						down = true;
			case 2:
				if (col >= 1)
					if (standardmap[row][col - 1] == 1)
						left = true;
			case 3:
				if (col < 7)
					if (standardmap[row][col + 1] == 1)
						right = true;
			}
		}
		// System.out.println("상 : " + top + " 하 : " + down + " 좌 : " + left + " 우 : "
		// +right);//디버깅용
		System.out.println("이동 할 방을 선택 하시오 -> 1.좌      2.상      3.하      4.우");
		search: while (true) {
			int ans = scan.nextInt();
			switch (ans) {
			case 1:
				if (left == true) {
					System.out.println("왼쪽 방으로 이동 합니다.");
					standardmap[row][col] = 1; // 현재 방을 일반 방으로 변경
					standardmap[row][col - 1] = 3; // 이동하는 방을 현제 방으로 변경
					break search; // 해당하는 장소로 이동 후 구문 종료
				} else {
					System.out.println("왼쪽에 방이 존재하지 않습니다.");
					break;// 해당 방향에 방이 없으면 재 입력 받도록 함.
				}
			case 2:
				if (top == true) {
					System.out.println("위쪽 방으로 이동 합니다.");
					standardmap[row][col] = 1;
					standardmap[row - 1][col] = 3;
					break search;
				} else {
					System.out.println("위쪽에 방이 존재하지 않습니다.");
					break;
				}
			case 3:
				if (down == true) {
					System.out.println("아래 방으로 이동 합니다.");
					standardmap[row][col] = 1;
					standardmap[row + 1][col] = 3;
					break search;
				} else {
					System.out.println("아래에 방이 존재하지 않습니다.");
					break;
				}
			case 4:
				if (right == true) {
					System.out.println("오른쪽 방으로 이동 합니다.");
					standardmap[row][col] = 1;
					standardmap[row][col + 1] = 3;
					break search;
				} else {
					System.out.println("오른쪽 방이 존재하지 않습니다.");
					break;
				}
			default:
				System.out.println("잘못된 값을 입력 하였습니다. 1 ~ 4 사이의 정수를 입력해 주십시오");
				break;
			}
		}
		loadmap();//맵을 이동 후 이동 후의 맵 상태를 재 출력
	}// 현재 위치 기반으로 인접한 1칸의 방 으로만 이동 가능

}
