package org.youyk.sec07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    publish on for downstream!
    subscribeOn for upstream!
 */
public class Lec05PublishOn {
    private static final Logger log = LoggerFactory.getLogger(Lec05PublishOn.class);

    public static void main(String[] args) {
        Flux<Object> flux = Flux.create(sink -> {
                    for (int i = 1; i < 3; i++) {
                        log.info("generating: {}", i);
                        sink.next(i);
                    }
                    sink.complete();
                })
                .publishOn(Schedulers.parallel())
                .doOnNext(v -> log.info("value: {}", v))
                .doFirst(() -> log.info("first1"))
                /**
                 * boundedElastic() 스케줄러는 필요에 따라 새로운 쓰레드를 생성하며,
                 * 사용하지 않는 쓰레드는 일정 시간이 지나면 자동으로 제거됩니다.
                 * 이 스케줄러는 쓰레드 풀을 사용하지 않으며, 대신에 필요한 만큼의 쓰레드를 생성합니다.
                 * 그러나 동시에 실행할 수 있는 쓰레드의 수는 제한되어 있습니다.
                 * 이 제한은 기본적으로 CPU 코어 수의 10배로 설정되어 있습니다.
                 */
                //.subscribeOn(Schedulers.boundedElastic())
                //immediate는 현재 쓰레드를 의미한다
                //Schdulers.parallel()은 CPU 코어 수 만큼 쓰레드를 생성한다. (cpu task에 좋다)
                .publishOn(Schedulers.boundedElastic())
                //여기 first2까지는 Main Thread로 찍힌다. 그 위로는 boundedElastic Thread로 찍힌다.
                .doFirst(() -> log.info("first2"));

        Runnable runnable1 = () -> flux
/*                .doFirst(() -> log.info("first1"))
                .subscribeOn(Schedulers.boundedElastic())
                //여기 first2까지는 Main Thread로 찍힌다. 그 위로는 boundedElastic Thread로 찍힌다.
                .doFirst(() -> log.info("first2"))*/
                .subscribe(Util.subscriber("sub1"));

        new Thread(runnable1).start(); //first2 까지만 별도 쓰레드로 돌고 그 위로는 다시 boundedElastic Thread로 돌아간다.

        Util.sleepSeconds(2);
    }
}
