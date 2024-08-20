package org.youyk.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
    How to handle error in a reactive pipeline
    Flux.(...)
        ...
        ...
        ...
        ...
 */
public class Lec06ErrorHandling {
    private static final Logger log = LoggerFactory.getLogger(Lec06ErrorHandling.class);

    public static void main(String[] args) {
        Flux.range(1,10)
                .map(i ->i == 5 ? 5/0 : i) // intention
                //exception이 발생하면 onErrorReturn을 통해서 -1을 리턴하게 된다. received : -1 그리고 received completed 가 됨
                //.onErrorReturn(-1)
                //onErrorReturn을 통해서 exception이 발생하면 -1을 리턴하게 된다. received : -1 그리고 received completed 가 됨
                .onErrorReturn(IllegalArgumentException.class, -1)
                .onErrorReturn(ArithmeticException.class, -2)
                //default error 시 -3을 리턴하게 된다. received : -3 그리고 received completed 가 됨
                .onErrorReturn(-3)
                .subscribe(Util.subscriber());

        //onErrorResume 은 fallback을 호출해서 다른 값을 리턴하게 된다.
        Mono.just(5)
                .map(i -> i == 5 ? 5/0 : i)
                .onErrorResume(ArithmeticException.class, ex -> fallback())
                .onErrorResume(ex -> fallback2())
                .onErrorReturn(-5)
                //.onErrorResume(ex -> fallback())
                .subscribe(Util.subscriber());


        Flux.range(1,10)
                .map(i ->i == 5 ? 5/0 : i) // intention
                .onErrorContinue((err, obj) -> {
                    log.error("Error: {}", err);
                    log.info("Object: {}", obj);
                })
                .subscribe(Util.subscriber());
    }

    private static void onErrorComplete(){
        //onErrorContinue는 에러가 발생하면 그 에러를 무시하고 received completed를 리턴한다.
        Mono.error(new RuntimeException("oops"))
                .onErrorComplete()
                .subscribe(Util.subscriber());
    }

    private static void onErrorReturn(){
        Mono.just(5)
                .map(i ->i == 5 ? 5/0 : i) // intention
                //exception이 발생하면 onErrorReturn을 통해서 -1을 리턴하게 된다. received : -1 그리고 received completed 가 됨
                //.onErrorReturn(-1)
                //onErrorReturn을 통해서 exception이 발생하면 -1을 리턴하게 된다. received : -1 그리고 received completed 가 됨
                .onErrorReturn(IllegalArgumentException.class, -1)
                .onErrorReturn(ArithmeticException.class, -2)
                //default error 시 -3을 리턴하게 된다. received : -3 그리고 received completed 가 됨
                .onErrorReturn(-3)
                .subscribe(Util.subscriber());
    }

    private static Mono<Integer> fallback(){
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(10, 100));
    }
    private static Mono<Integer> fallback2(){
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 1000));
    }
}
