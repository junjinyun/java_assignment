package gameplay;

import java.util.Random;

public class EntryPointSelector {
	public static void EntryPoint() {
	    int limit = 2, increment = 0;

	    Random random = new Random();
	    int type = random.nextInt(4);  // type을 랜덤하게 설정
	    find: for (int q = 0; q < 14; q++) {
	        limit = 2;
	        increment += 1;
	        limit += increment;

	        if (type < 2)
	            for (int i = 0; i < 8; i++) {
	                limit -= 1;
	                if (limit < 8) {
	                    for (int j = 0; j < limit; j++) {
	                        switch (type) {
	                        case 0:
	                            if (Mk_Use_Map.standardmap[i][j] == 1) {
	                                Mk_Use_Map.standardmap[i][j] = 3;
	                                break find;
	                            }
	                        case 1:
	                            if (Mk_Use_Map.standardmap[i][7 - j] == 1) {
	                                Mk_Use_Map.standardmap[i][7 - j] = 3;
	                                break find;
	                            }
	                        }
	                    }
	                } else {
	                    for (int j = 0; j < 8; j++) {
	                        switch (type) {
	                        case 0:
	                            if (Mk_Use_Map.standardmap[i][j] == 1) {
	                                Mk_Use_Map.standardmap[i][j] = 3;
	                                break find;
	                            }
	                        case 1:
	                            if (Mk_Use_Map.standardmap[i][7 - j] == 1) {
	                                Mk_Use_Map.standardmap[i][7 - j] = 3;
	                                break find;
	                            }
	                        }
	                    }
	                }
	            }
	        else
	            for (int i = 7; i >= 0; i--) {
	                limit -= 1;
	                if (limit < 8) {
	                    for (int j = 0; j < limit; j++) {
	                        switch (type) {
	                        case 2:
	                            if (Mk_Use_Map.standardmap[i][j] == 1) {
	                                Mk_Use_Map.standardmap[i][j] = 3;
	                                break find;
	                            }
	                        case 3:
	                            if (Mk_Use_Map.standardmap[i][7 - j] == 1) {
	                                Mk_Use_Map.standardmap[i][7 - j] = 3;
	                                break find;
	                            }
	                        }
	                    }
	                } else {
	                    for (int j = 0; j < 8; j++) {
	                        switch (type) {
	                        case 2:
	                            if (Mk_Use_Map.standardmap[i][j] == 1) {
	                                Mk_Use_Map.standardmap[i][j] = 3;
	                                break find;
	                            }
	                        case 3:
	                            if (Mk_Use_Map.standardmap[i][7 - j] == 1) {
	                                Mk_Use_Map.standardmap[i][7 - j] = 3;
	                                break find;
	                            }
	                        }
	                    }
	                }
	            }
	    }
	}
}