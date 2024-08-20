package org.youyk.sec05;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04Delay {
    public static void main(String[] args) {
        //각각의 데이터 항목을 지정된 시간만큼 지연시키는 역할을 합니다.
        // 이 연산자는 비동기적으로 작동하며, 별도의 스레드에서 실행됩니다.
        //interval과 비슷하다
        //delayElements는 모든 연산을 다하고 나서, 각각의 요소를 지정된 시간만큼 지연시키고 보내는 것이다.
        /**
         * 따라서 이 코드는 1부터 10까지의 숫자를 생성하고,
         * 각 숫자를 1초 간격으로 구독자에게 전달하는 역할을 합니다.
         * 이 과정에서 메인 스레드는 지연되지 않으며,
         * 각 숫자는 별도의 스레드에서 지연된 후 구독자에게 전달됩니다.
         */
        //이렇게 로그를 찍어보면 request가 1개씩 들어오는 것을 볼 수 있다.
        Flux.range(1,10)
                .log()
                .delayElements(Duration.ofSeconds(1))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(12);

    }
}
