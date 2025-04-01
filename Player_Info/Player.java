package Player_Info;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;

public class Player {
    int money;

    private static final String SAVE_FILE = "player_data.json";

    public Player(int money) {
        this.money = money;
    }

    // 게임 데이터 저장 ( 지정된 폴더에 JSON 파일 생성 )
    public void saveGame() {
        try {
            Gson gson = new Gson();
            List<String> itemNames = new ArrayList<>();
            PlayerData data = new PlayerData(money);
            FileWriter writer = new FileWriter(SAVE_FILE);
            gson.toJson(data, writer);
            writer.close();
            System.out.println("💾 게임 데이터가 저장되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 게임 데이터 불러오기
    public static Player loadGame() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            try {
                Gson gson = new Gson();
                FileReader reader = new FileReader(SAVE_FILE);
                PlayerData data = gson.fromJson(reader, new TypeToken<PlayerData>() {
                }.getType());
                reader.close();

                Player player = new Player(data.money);
                System.out.println("📂 저장된 데이터를 불러왔습니다! (" + SAVE_FILE + ")");
                return player;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("🔄 새 게임을 시작합니다.");
        return new Player(500);
    }
}