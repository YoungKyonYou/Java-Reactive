package org.youyk.sec09;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;

public class Lec05Merge {

    private static final Logger log = LoggerFactory.getLogger(Lec05Merge.class);

    public static void main(String[] args) {

        demo2();
        Util.sleepSeconds(3);
    }

    private static void demo1() {
        Flux.merge(producer1(), producer2(), producer3()) // 순서 상관없이 실행됨
                .take(2)
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        producer2().mergeWith(producer1()) //demo1과 똑같음
                .mergeWith(producer3())
                .take(2)
                .subscribe(Util.subscriber());
    }

    /**
     * transform 메서드는 리액티브 스트림에서 사용되며, 스트림에 연산을 적용하는 데 사용됩니다.
     * 이 메서드는 Flux 또는 Mono 타입의 스트림에 대해 동작하며, Function<Publisher<T>, Publisher<V>>
     *     타입의 함수를 인자로 받습니다.  이 함수는 원본 스트림을 받아 새로운 스트림을 반환합니다.
     *     이 과정에서 원본 스트림에 대한 연산을 적용할 수 있습니다.
     * 이 연산은 원본 스트림을 변경하지 않으며, 새로운 스트림을 생성합니다.
     */
    private static Flux<Integer> producer1() {
        return Flux.just(1, 2, 3)
                .transform(Util.fluxLogger("producer1"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2() {
        return Flux.just(51, 52, 53)
                .transform(Util.fluxLogger("producer2"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer3() {
        return Flux.just(11, 12, 13)
                .transform(Util.fluxLogger("producer3"))
                .delayElements(Duration.ofMillis(10));
    }
}
