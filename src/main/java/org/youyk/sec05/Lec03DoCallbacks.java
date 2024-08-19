package org.youyk.sec05;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
/*
    do hooks/callbacks
 */
public class Lec03DoCallbacks {

    private static final Logger log = LoggerFactory.getLogger(Lec03DoCallbacks.class);

    public static void main(String[] args) {

        //순서가 매우 중요하다
        //doFirst-1, doFirst-2를보면 2가 먼저 출력되는데 이유는 subscriber()가 호출된 시점부터 위로 올라가면서 실행되기 때문이다 아래에서 위로 실행된다.
        //subscription object는 위에서 아래로 간다. 그래서 doOnSubscribe-1이 먼저 출력된다.
        //그다음 doOnRequest-2 부터 출력되는데 이유는 아래에서 위로 가기 때문
        //그다음 doOnComplete가 위에서 아래로 가면서 terminate를 같이 출력함
        //그다음 doFinally가 아래에서 위로 출력됨
        Flux.<Integer>create(fluxSink -> {
                    log.info("producer begins");
                    for (int i = 0; i < 4; i++) {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                //     fluxSink.error(new RuntimeException("oops"));
                    log.info("producer ends");
                })
                .doOnComplete(() -> log.info("doOnComplete-1"))
                .doFirst(() -> log.info("doFirst-1"))
                .doOnNext(item -> log.info("doOnNext-1: {}", item))
                .doOnSubscribe(subscription -> log.info("doOnSubscribe-1: {}", subscription))
                .doOnRequest(request -> log.info("doOnRequest-1: {}", request))
                .doOnError(error -> log.info("doOnError-1: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("doOnTerminate-1")) // complete or error case
                .doOnCancel(() -> log.info("doOnCancel-1"))
                .doOnDiscard(Object.class, o -> log.info("doOnDiscard-1: {}", o))//subscriber가 producer 보낸 item을 못받았을 때
                .doFinally(signal -> log.info("doFinally-1: {}", signal)) // finally irrespective of the reason
                 .take(2)
                .doOnComplete(() -> log.info("doOnComplete-2"))
                .doFirst(() -> log.info("doFirst-2"))
                .doOnNext(item -> log.info("doOnNext-2: {}", item))
                .doOnSubscribe(subscription -> log.info("doOnSubscribe-2: {}", subscription))
                .doOnRequest(request -> log.info("doOnRequest-2: {}", request))
                .doOnError(error -> log.info("doOnError-2: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("doOnTerminate-2")) // complete or error case
                .doOnCancel(() -> log.info("doOnCancel-2"))
                .doOnDiscard(Object.class, o -> log.info("doOnDiscard-2: {}", o))
                .doFinally(signal -> log.info("doFinally-2: {}", signal)) // finally irrespective of the reason
                //.take(4)
                .subscribe(Util.subscriber("subscriber"));


    }

}
