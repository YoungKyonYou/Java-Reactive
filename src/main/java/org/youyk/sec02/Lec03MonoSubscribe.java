package org.youyk.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec03MonoSubscribe {
    private static final Logger log = LoggerFactory.getLogger(Lec03MonoSubscribe.class);

    public static void main(String[] args) {
        Mono<String> mono = Mono.just(1)
                .map(i -> i + "a");

        // 구독만 하고 request를 하지 않았는데 왜 콘솔에 "received: 1"가 찍힐까?
        // 그리고 왜 SubscriberImpl.java의 onComplete 메서드의 completed message가 안 찍힐까?
        // -> mono.subscribe(i -> log.info("received: {}", i);

        //이렇게 함으로써 completed message가 찍힌다.
        //그런데 request를 안 했는데 왜 데이터가 찍힐까?
        //답은 request를 대신해주고 있기 때문이다. 이건 내부적으로 가령 SubscriberImpl.java의 onSubscribe 메서드에서처럼
        //구독할 때 구독object가 자동으로 처음에 subscription.request(MAX_VALUE)을 해주고 있기 때문이다.
        mono.subscribe(i -> log.info("received: {}", i),
                err -> log.error("error", err),
                () -> log.info("completed!")); // onComplete

        //만약 자동으로 request해주는 거 말고 수동으로 해주고 싶으면 4번째 파라미터를 받는 함수를 사용하면 됨
        mono.subscribe(i -> log.info("received: {}", i),
                err -> log.error("error", err),
                () -> log.info("completed!"),
                subscription -> subscription.request(1));

    }
}
