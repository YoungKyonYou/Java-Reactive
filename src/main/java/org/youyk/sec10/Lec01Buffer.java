package org.youyk.sec10;

import java.time.Duration;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;

/*
    Collect itmes based on given internal / size
 */
public class Lec01Buffer {

    public static void main(String[] args) {
        demo4();
        Util.sleepSeconds(60);
    }

    private static void demo1(){
        eventStream()
                .buffer()//int-max value or the source has to complete
                .subscribe(Util.subscriber());
    }

    private static void demo2(){
        eventStream()
                .buffer(3)//every 3 items
                .subscribe(Util.subscriber());
    }

    private static void demo3(){
        eventStream()
                .buffer(Duration.ofMillis(500))//every 500ms
                .subscribe(Util.subscriber());
    }

    private static void demo4(){
        eventStream()
                .bufferTimeout(3, Duration.ofSeconds(1))//every 3 items or max 1 second
                .subscribe(Util.subscriber());
    }

    private static Flux<String> eventStream(){
        return Flux.interval(Duration.ofMillis(200))
                .take(10)
                //concatWith 연산자는 원본 스트림의 처리가 완료된 후에 인자로 전달된 Publisher의 처리를 시작합니다.
                // 이 연산자는 원본 스트림의 결과를 무시하고, 대신 인자로 전달된 Publisher의 결과를 반환합니다.
                // Flux.never()는 아무런 데이터도 방출하지 않고 완료되지 않는 Flux를 생성합니다.
                // 따라서 이 연산자는 원본 스트림의 처리가 완료된 후에 아무런 데이터도 방출하지 않고 완료되지 않는 스트림을 반환합니다.
                .concatWith(Flux.never())
                .map(i-> "event-" + i);
    }

}
