package baseball;

import camp.nextstep.edu.missionutils.Console;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static camp.nextstep.edu.missionutils.Randoms.pickNumberInRange;
import static constant.Constant.*;

public class NumberBaseballGame {

    private int[] answer = new int[LENGTH];

    public void run() {
        do {
            play();
        } while (restart());
    }

    private void play() {
        int[] guess;
        String input;

        makeAnswer();

        do {
            System.out.print(PROMPT_INPUT_NUMBER);
            input = Console.readLine();

            // 입력값 유효성 검사
            if (isValidInput(input)) {
                guess = Stream.of(input.split("")).mapToInt(Integer::parseInt).toArray();
            } else {
                throw new IllegalArgumentException(INPUT_ERROR);
            }
        } while (!checkAnswer(guess, answer));
    }

    // 정답 만들기
    private void makeAnswer() {
        Set<Integer> numbers = new HashSet<>();

        // Set으로 정답 만들기
        while (numbers.size() < answer.length) {
            int number = pickNumberInRange(1, 9);
            numbers.add(number); // 중복 자동 제거
        }

        // Set에서 배열로 변환
        answer = numbers.stream().mapToInt(Integer::intValue).toArray();
    }

    // 입력값 유효성 검사 (3자리 숫자, 중복 불가)
    private boolean isValidInput(String input) {
        return input.matches("\\d{3}") && input.chars().distinct().count() == LENGTH;
    }

    // 정답 확인하기
    private boolean checkAnswer(int[] guess, int[] answer) {
        int ball = 0;
        int strike = 0;

        // 볼, 스트라이크 계산
        ball = calculateBall(guess, answer);
        strike = calculateStrike(guess, answer);

        if (ball != 0 && strike == 0) {
            System.out.println(ball + BALL);
        }
        if (ball == 0 && strike != 0) {
            System.out.println(strike + STRIKE);
        }
        if (ball != 0 && strike != 0) {
            System.out.println(ball + BALL + " " + strike + STRIKE);
        }
        if (ball == 0 && strike == 0) {
            System.out.println(NOTHING);
        }

        return strike == LENGTH;
    }

    // 볼 계산하기
    private static int calculateBall(int[] guess, int[] answer) {
        int ball = 0;

        // 볼 확인
        for (int i = 0; i < LENGTH; i++) {
            final int currentGuess = guess[i];
            if (guess[i] != answer[i] && Arrays.stream(answer).anyMatch(x -> x == currentGuess)) {
                ball++;
            }
        }

        return ball;
    }

    // 스트라이크 계산하기
    private static int calculateStrike(int[] guess, int[] answer) {
        int strike = 0;

        // 스트라이크 확인
        for (int i = 0; i < LENGTH; i++) {
            if (guess[i] == answer[i]) {
                strike++;
            }
        }

        return strike;
    }

    // 게임 재시작
    private boolean restart() throws IllegalArgumentException {
        System.out.println(PROMPT_PLAY_AGAIN);
        String restart = Console.readLine();

        if (restart.equals(RESTART)) {
            return true;
        } else if (restart.equals(EXIT)) {
            return false;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
