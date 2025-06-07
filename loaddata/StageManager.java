package loaddata;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StageManager {
    private static final String JSON_PATH = "src/data/stage.json"; // JSON 파일 경로

    public static List<Stage> loadStages() {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();

            // JSON 파일에서 Map<String, List<Stage>> 타입으로 파싱
            Map<String, List<Stage>> map = gson.fromJson(reader, new TypeToken<Map<String, List<Stage>>>() {}.getType());

            return map.get("stage"); // "stage" 키에 해당하는 리스트 반환
        } catch (FileNotFoundException e) {
            System.out.println("[오류] 파일을 찾을 수 없습니다: " + JSON_PATH);
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            System.out.println("[오류] JSON 구문 오류가 발생했습니다.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("[오류] 파일 읽기 중 문제가 발생했습니다.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("[오류] 예기치 못한 오류가 발생했습니다.");
            e.printStackTrace();
        }

        return null;
    }
}
