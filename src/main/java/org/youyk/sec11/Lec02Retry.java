package org.youyk.sec11;

import java.awt.print.Pageable;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import org.youyk.sec09.Lec03ConcatWith;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

/**
 * retry operator simply resubscribes when it sees error signal
 */
public class Lec02Retry {
    private static final Logger log = LoggerFactory.getLogger(Lec02Retry.class);

    public static void main(String[] args) {
        demo2();
        Util.sleepSeconds(10);
    }

    private static void demo1(){
        getCountryName()
        //retry operator simply resubscribes when it sees error signal
                .retry(2) //product service에서 product info를 가져올 때 product service에서 여러 인스턴스가 실행된다고 가정했을 때
                //하나의 로드밸런서가 있을 때 500에러가 발생하게 된다(마이크로 서비스에서 이런 문제가 어쩌다가 발생함)
                //retry를 추가함으로써 이런 에러에 대해서 다시 시도하게 되는 것이다.
                .subscribe(Util.subscriber());
    }

    private static void demo2(){
        getCountryName()
                //retry operator simply resubscribes when it sees error signal
                .retryWhen(/*Retry.indefinitely()*/Retry.fixedDelay(2, Duration.ofSeconds(1))
                                .filter(ex -> RuntimeException.class.equals(ex.getClass())) //RuntimeException이 발생하면 retry하게 함
                                .onRetryExhaustedThrow((spec, signal) -> signal.failure()) //retry가 끝나면 에러를 던지게 함
                        /*.doBeforeRetry(rs -> log.info("retrying"))*/)
                .subscribe(Util.subscriber());//시간을 두고 retry를 하게 할 수 있다.
    }
    private static Mono<String> getCountryName(){
        AtomicInteger atomicInteger = new AtomicInteger(0);
        return Mono.fromSupplier(() -> {
            if(atomicInteger.incrementAndGet() <3){
                throw new RuntimeException("oops");
            }
            return Util.faker().country().name();
        })
                .doOnError(err -> log.info("ERROR : {}", err.getMessage()))
                .doOnSubscribe(s->log.info("subscribing")); //non-blocking IO

    }
}
