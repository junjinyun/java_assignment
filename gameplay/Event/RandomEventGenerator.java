package gameplay.Event;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import loaddata.MapEvent;
import loaddata.EventManager;
import gameplay.Party.AllyParty;  // AllyParty 클래스를 추가로 import 해야 함

public class RandomEventGenerator {
    private static Scanner scan = new Scanner(System.in);
    public static List<MapEvent> SelectedMapEvent = EventManager.loadMapEvents();

    public static void EventGenerator(AllyParty allyParty) {  // AllyParty를 매개변수로 받음
        String SelectedItem = "";
        String YesOrNo = "";
        String EventType;
        String EventResult = "";

        Random random = new Random();
        int key = random.nextInt(SelectedMapEvent.size());
        MapEvent currentEvent = SelectedMapEvent.get(key);

        EventType = currentEvent.getEventType().trim();
        System.out.println(currentEvent.getName() + "\n" + currentEvent.getInformation());

        // 고정 크기: 요청 아이템, 성공 시, 실패 시
        String[] ReqestItemString = currentEvent.getRequestedItem().split(", ");
        String ReqestItemname = ReqestItemString[0]; // 요구 아이템

        if (EventType.equalsIgnoreCase("상호작용")) {
            System.out.println("대소문자 구분 없이 Yes 입력 시 상호작용, 그 외는 종료");
            YesOrNo = scan.nextLine();
            if ("yes".equalsIgnoreCase(YesOrNo.trim())) {
                System.out.println("상호작용함");
            } else {
                System.out.println("상호작용하지 않음");
            }

        } else if (EventType.equalsIgnoreCase("아이템제출")) {
            System.out.println("아이템 제출 창 출력");

            w:
            while (true) {
                System.out.print("제출할 아이템을 선택하세요(no 입력 시 미제출 상태로 진행): ");
                SelectedItem = scan.nextLine();

                // 아이템 제출 거부
                if (SelectedItem.equalsIgnoreCase("no")) {
                    YesOrNo = "no";
                    EventResult = ReqestItemString[2]; // 실패시 결과
                    System.out.println("아이템을 제출하지 않고 이벤트를 진행합니다.");
                    break w;
                }

                while (true) {
                    System.out.print("아이템을 제출하시겠습니까? (yes/no): ");
                    YesOrNo = scan.nextLine();

                    if (YesOrNo.equalsIgnoreCase("yes")) {
                        if (SelectedItem.equalsIgnoreCase(ReqestItemname)) {
                            EventResult = ReqestItemString[1]; // 성공시 결과
                            System.out.println("아이템 '" + SelectedItem + "'을(를) 제출하고 이벤트를 진행합니다.");
                            break w;
                        } else {
                            EventResult = ReqestItemString[2]; // 실패시 결과
                            System.out.println("잘못된 아이템을 제출하셨습니다. 다시 입력하세요.");
                            break; // 다시 아이템 입력
                        }

                    } else if (YesOrNo.equalsIgnoreCase("no")) {
                        EventResult = ReqestItemString[2]; // 실패시 결과
                        System.out.println("아이템을 제출하지 않고 이벤트를 진행합니다.");
                        break w;
                    } else {
                        System.out.println("잘못된 입력입니다. 'yes' 또는 'no'로 입력해주세요.");
                    }
                }
            }
        }

        // 이벤트 실행
        String internalId = currentEvent.getInternalId();
        EventLauncher launcher = new EventLauncher(allyParty);  // AllyParty 객체를 전달

        Object[] args;
        Class<?>[] paramTypes;

        if (EventType.equalsIgnoreCase("상호작용")) {
            args = new Object[]{YesOrNo};
            paramTypes = new Class[]{String.class};
        } else if (EventType.equalsIgnoreCase("아이템제출")) {
            args = new Object[]{SelectedItem, YesOrNo, EventResult};
            paramTypes = new Class[]{String.class, String.class, String.class};
        } else {
            args = new Object[]{};
            paramTypes = new Class[]{};
        }

        try {
            java.lang.reflect.Method method = EventLauncher.class.getMethod(internalId, paramTypes);
            method.invoke(launcher, args);
        } catch (NoSuchMethodException e) {
            System.out.println("[오류] 이벤트 처리 메서드를 찾을 수 없습니다: " + internalId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}