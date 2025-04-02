package 팀플;

import java.util.ArrayList;
import java.util.Random;

public class Mk_Use_Map {
	public static int[][] standardmap = new int[8][8];

	public static void mkmap() {

		for (int i = 0; i < standardmap.length; i++)
			for (int x = 0; x < standardmap[i].length; x++)
				standardmap[i][x] = 0;
	}

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
				if (standardmap[i][x] == 1)
					System.out.print("o ");
				else if (standardmap[i][x] == 2)
					System.out.print("x ");
				else
					System.out.print("  ");
			}
			System.out.print("\n");
		}

	}

}
