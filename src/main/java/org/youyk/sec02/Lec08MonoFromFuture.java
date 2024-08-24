package org.youyk.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class Lec08MonoFromFuture {
    private static final Logger log = LoggerFactory.getLogger(Lec08MonoFromFuture.class);

    public static void main(String[] args) {

        //이것만 있으면 결과를 볼 수 없다 왜냐면 비동기로 ForkJoinPool에서 Thread를 돌리기 때문에 메인 쓰레드를 잠시 막아야 함.
        //ava의 메인 스레드는 프로그램의 모든 비동기 작업이 완료되지 않았더라도 프로그램의
        // 나머지 부분이 완료되면 종료됩니다. 따라서 getName() 메소드의 비동기 작업이 완료되기 전에
        // 메인 스레드가 종료되어,
        // Mono의 구독 결과를 볼 수 없는 것
        //subscribe 부분을 주석처리하고 돌려보면 "generating username"이라는 문구만 찍히는데 그러면 구독하진 않았으나 실제 일은 하는 것이다.
        // 이것은 문제가 될 수 있다. CompletableFuture는 Supplier를 받지만 Lazy하지 않다!(default가 그렇다)

        Mono.fromFuture(getName())
                .subscribe(Util.subscriber());

        //단 이렇게 만들어주면 Lazy하게 만들어줄 수 있다. 여기서 .subscribe를 하면 바로 실행하게 된다.
        Mono.fromFuture(Lec08MonoFromFuture::getName);

        //이렇게 딜레이를 줘야 결과를 볼 수 있음
        Util.sleepSeconds(1);
    }

    private static CompletableFuture<String> getName(){
        return CompletableFuture.supplyAsync(() -> {
            log.info("generating username");
            return Util.faker().name().firstName();
        });
    }
}
