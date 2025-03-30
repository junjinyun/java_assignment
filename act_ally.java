package 팀플;


import java.util.Scanner;

public class act_ally {
	private static Scanner scan = new Scanner(System.in);
	public static void allyattack(int attacker) {

		System.out.print("공격할 적의 번호를 입력하세요 (0부터 " + (select_enemy.enemy.length - 1) + "까지): ");
		int target =0;
        while (true) {
            try {
                target = scan.nextInt();  // 정수 입력 받기
                if (target < 0 || target >= select_enemy.enemy.length) {
                    System.out.println("잘못된 입력입니다. 0부터 " + (select_enemy.enemy.length - 1) + "까지의 번호를 입력하세요.");
                } else {
                    break;  // 유효한 입력이면 반복문 종료
                }
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 숫자만 입력해주세요.");
                scan.nextLine();  // 잘못된 입력으로 발생한 버퍼를 처리하기 위해 nextLine() 호출
            }
        }
		System.out.println(select_ally.ally[attacker].name+"가 "+select_enemy.enemy[target].name+"를 공격합니다");
		select_enemy.enemy[target].health-=select_enemy.enemy[attacker].attack;
		System.out.println(select_enemy.enemy[target].name+"의 남은 체력 : "+select_enemy.enemy[target].health);
	} 
}
