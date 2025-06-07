package gameplay;

import gameplay.Party.AllyParty;

public class TestEvent {

    public static void main(String[] args) {
        // AllyParty 객체 생성
        AllyParty allyParty = new AllyParty();
        
        // RandomEventGenerator에서 해당 파티 객체를 사용하도록 수정
        //RandomEventGenerator.EventGenerator(allyParty);
    }
}