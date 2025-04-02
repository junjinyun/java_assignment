package 팀플;

import java.util.Random;

public class EntryPointSelector {
	public static void EntryPoint() {
		int w, e = 0;
		Random random = new Random();// Random 객체로 난수 시드 생성
		int type = random.nextInt(4);// 아군배열 크기를 최대 값으로 하는 난수 생성
		find: for (int q = 0; q < 14; q++) {
			w = 2;
			e += 1;
			w = w + e;

			if (type < 2)
				for (int i = 0; i < 8; i++) {
					w -= 1;
					if (w < 8) {
						for (int j = 0; j < w; j++) {
							switch (type) {
							case 0:
								if (Mk_Use_Map.standardmap[i][j] == 1) {
									Mk_Use_Map.standardmap[i][j] = 2;
									break find;
								}

							}
						}
					} else {
						// w가 8보다 크거나 같으면 모든 칸을 체크
						for (int j = 0; j < 8; j++) {
							switch (type) {
							case 0:
								if (Mk_Use_Map.standardmap[i][j] == 1) {
									Mk_Use_Map.standardmap[i][j] = 2;
									break find;
								}
							case 1:
								if (Mk_Use_Map.standardmap[i][7 - j] == 1) {
									Mk_Use_Map.standardmap[i][7 - j] = 2;
									break find;
								}
							}
						}
					}
				}
			else
				for (int i = 7; i >= 0; i--) {
					w -= 1;
					if (w < 8) {
						for (int j = 0; j < w; j++) {
							switch (type) {
							case 2:
								if (Mk_Use_Map.standardmap[i][j] == 1) {
									Mk_Use_Map.standardmap[i][j] = 2;
									break find;
								}
							case 3:
								if (Mk_Use_Map.standardmap[i][7 - j] == 1) {
									Mk_Use_Map.standardmap[i][7 - j] = 2;
									break find;
								}
							}
						}
					} else {
						// w가 8보다 크거나 같으면 모든 칸을 체크
						for (int j = 0; j < 8; j++) {
							switch (type) {
							case 2:
								if (Mk_Use_Map.standardmap[i][j] == 1) {
									Mk_Use_Map.standardmap[i][j] = 2;
									break find;
								}
							case 3:
								if (Mk_Use_Map.standardmap[i][7 - j] == 1) {
									Mk_Use_Map.standardmap[i][7 - j] = 2;
									break find;
								}
							}
						}
					}
				}
		}
	}
}