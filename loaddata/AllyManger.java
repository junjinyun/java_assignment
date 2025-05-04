package loaddata;

/* 적 JSON 정보 관리하는 클래스
*
* 1. 경로에 오류가 뜰 경우?
*   => 불러올 데이터를 우클릭 하여 경로/참조 복사 */

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetAllyJson {
    private static final String JSON_PATH = "src/data/ally.json"; // JSON 파일 경로

    public static List<Ally> loadAlly() {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();

            // JSON을 Map 형태로 읽기
            Map<String, List<Ally>> list = gson.fromJson(reader, new TypeToken<Map<String, List<Ally>>>() {}.getType());

            return list.get("ally"); // "ally" 키에 해당하는 리스트 가져오기
        } catch (FileNotFoundException e) {
            System.out.println("[오류] 파일을 찾을 수 없습니다: " + JSON_PATH);
            e.printStackTrace();  // 로그를 출력하거나 디버깅에 유용한 스택 추적 출력
        } catch (JsonSyntaxException e) {
            System.out.println("[오류] JSON 구문 오류가 발생했습니다. 파일이 올바른 JSON 형식인지 확인해 주세요.");
            e.printStackTrace();  // 구문 오류가 발생한 경우
        } catch (IOException e) {
            System.out.println("[오류] 파일 읽기 중 오류가 발생했습니다.");
            e.printStackTrace();  // 파일을 읽을 때 발생한 일반적인 IO 오류
        } catch (Exception e) {
            System.out.println("[오류] 예기치 않은 오류가 발생했습니다.");
            e.printStackTrace();  // 예기치 않은 모든 오류를 처리
        }
        return null;  // 오류 발생 시 null 반환
    }
}