package gameplay;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import dungeon.EventManager;
import dungeon.MapEvent;

public class RandomEventGenerator {
    private static Scanner scan = new Scanner(System.in);
    public static List<MapEvent> SelectedMapEvent = EventManager.loadMapEvents();

    // 각 이벤트의 ID와 난수를 비교하여 일치하는 이벤트를 불러와 실행
    public static void EventGenerator() {
        String SelectedItem = "", YesOrNo = "", EventType;

        Random random = new Random();
        int key = random.nextInt(SelectedMapEvent.size());
        MapEvent currentEvent = SelectedMapEvent.get(key);

        EventType = currentEvent.getEventType().trim();
        System.out.println(currentEvent.getName() + "\n" + currentEvent.getInformation());

        String[] ReqestItemString = currentEvent.getRequestedItem().split(", ");
        String ReqestItemname = ReqestItemString[0];

        // 상호작용 이벤트
        if (EventType.equalsIgnoreCase("상호작용")) {
            System.out.println("대소문자 구분 없이 Yes 입력 시 상호작용, 그 외에는 종료");
            YesOrNo = scan.nextLine().trim();
            if (YesOrNo.equalsIgnoreCase("yes")) {
                System.out.println("상호작용함");
            } else {
                System.out.println("상호작용하지 않음");
            }
        }
        // 아이템 제출 이벤트
        else if (EventType.equalsIgnoreCase("아이템제출")) {
            System.out.println("아이템 제출 창 출력");
            w:
            while (true) {
                System.out.print("제출할 아이템을 선택하세요(no 입력 시 미제출 상태로 진행): ");
                SelectedItem = scan.nextLine();

                if (SelectedItem.equalsIgnoreCase("no")) {
                    System.out.println("아이템을 제출하지 않고 이벤트를 진행합니다.");
                    YesOrNo = "no";
                    break;
                }

                while (true) {
                    System.out.print("아이템을 제출하시겠습니까? (yes/no): ");
                    YesOrNo = scan.nextLine();

                    if (YesOrNo.equalsIgnoreCase("yes")) {
                        if (SelectedItem.equalsIgnoreCase(ReqestItemname)) {
                            System.out.println("아이템 '" + SelectedItem + "'을(를) 제출하고 이벤트를 진행합니다.");
                            break w;
                        } else {
                            System.out.println("잘못된 아이템을 제출하셨습니다. 다시 입력하세요.");
                            break;
                        }
                    } else if (YesOrNo.equalsIgnoreCase("no")) {
                        System.out.println("아이템을 제출하지 않고 이벤트를 진행합니다.");
                        break w;
                    } else {
                        System.out.println("잘못된 입력입니다. 'yes' 또는 'no'로 입력해주세요.");
                    }
                }
            }
        }

        // ===============================
        // ▶ 동적으로 이벤트 메서드 호출
        // ===============================
     // 현재 발생한 이벤트의 내부 식별자(메서드 이름으로 사용)를 가져옵니다.
        String internalId = currentEvent.getInternalId();

        // EventLauncher 클래스의 인스턴스를 생성합니다 (메서드 호출 대상 객체).
        EventLauncher launcher = new EventLauncher();

        // 메서드 호출에 필요한 인자 및 해당 인자의 타입 배열을 선언합니다.
        Object[] args;
        Class<?>[] paramTypes;

        // 이벤트 타입이 '상호작용'일 경우, 인자: YesOrNo 문자열 1개
        if (EventType.equalsIgnoreCase("상호작용")) {
            args = new Object[]{YesOrNo};                      // 인자 배열 생성
            paramTypes = new Class[]{String.class};            // 매개변수 타입 정의

        // 이벤트 타입이 '아이템제출'일 경우, 인자: SelectedItem, YesOrNo 두 개
        } else if (EventType.equalsIgnoreCase("아이템제출")) {
            args = new Object[]{SelectedItem, YesOrNo};        // 인자 배열 생성
            paramTypes = new Class[]{String.class, String.class}; // 매개변수 타입 정의

        // 그 외 다른 이벤트 타입의 경우, 인자 없이 메서드를 호출합니다.
        } else {
            args = new Object[]{};                             // 인자 없음
            paramTypes = new Class[]{};                        // 타입도 없음
        }

        try {
            // internalId와 일치하는 이름 및 매개변수를 가진 메서드를 EventLauncher에서 찾습니다.
            java.lang.reflect.Method method = EventLauncher.class.getMethod(internalId, paramTypes);

            // 찾은 메서드를 launcher 인스턴스를 통해 호출하며, 인자는 args 배열을 넘깁니다.
            method.invoke(launcher, args);

        } catch (NoSuchMethodException e) {
            // 메서드를 찾을 수 없을 경우 오류 메시지를 출력합니다.
            System.out.println("[오류] 이벤트 처리 메서드를 찾을 수 없습니다: " + internalId);

        } catch (Exception e) {
            // 기타 예외가 발생한 경우 전체 예외 내용을 출력합니다.
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventGenerator();
    }
}