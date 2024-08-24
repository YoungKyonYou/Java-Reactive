package org.youyk.sec09;

import java.time.Duration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
    "then" could be helpful when we are not interested in the result of a publisher /
    we need to have sequential execution of asynchronous tasks.
 */
public class Lec15Then {

    private static final Logger log = LoggerFactory.getLogger(Lec15Then.class);

    public static void main(String[] args) {

        var records = List.of("a", "b", "c");

        /**
         * then 연산자의 주요 특징은 원본 스트림의 결과를 무시하고, 대신 인자로 전달된 Publisher의 결과를 반환한다는 점입니다.
         * 즉, 원본 스트림의 처리가 완료되면 then 연산자 뒤의 Publisher의 처리를 시작하고, 그 결과를 반환합니다.
         * 이 연산자는 주로 비동기 작업을 순차적으로 실행할 때 사용됩니다.
         * 예를 들어, 데이터를 데이터베이스에 저장한 후에 알림을 보내야 하는 경우에 then 연산자를 사용할 수 있습니다.
         */
        saveRecords(records)
                //then은 complete 신호만을 기다린다. error면 에러 신호
                .then(sendNotification(records)) // only in case of success
                .subscribe(Util.subscriber());


        Util.sleepSeconds(5);

    }

    private static Flux<String> saveRecords(List<String> records) {
        return Flux.fromIterable(records)
                .map(r -> "saved " + r)
                .delayElements(Duration.ofMillis(500));
    }

    private static Mono<Void> sendNotification(List<String> records) {
        return Mono.fromRunnable(() -> log.info("all these {} records saved successfully", records));
    }

}
