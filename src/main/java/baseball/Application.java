package baseball;

import camp.nextstep.edu.missionutils.Console;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static camp.nextstep.edu.missionutils.Randoms.pickNumberInRange;

public class Application {
    public static void main(String[] args) {
        //TODO: 숫자 야구 게임 구현
        NumberBaseballGame numberBaseballGame = new NumberBaseballGame();
        numberBaseballGame.run();
    }
}

