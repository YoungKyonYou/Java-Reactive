package org.youyk.sec12;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

@Slf4j
public class Lec05MulticastDirectBestEffort {

    public static void main(String[] args) {
        demo2();
        Util.sleepSeconds(10);
    }
    private static void demo1(){
      //  System.getProperty("reactor.bufferSize.small", "16");

        //handle through which we would push items
        // onBackPressureBuffer - bounded queue(크기 제한이 있는 큐라는 뜻)
        Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer(/*100*/); // 100개의 item을 queue에 담을 수 있다.


        // handle through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();

        /**
         * 이렇게 하면 mike의 performance가 sam에 영향을 미친다 실행하면 버퍼가 꽉차면 FAIL이 뜨는 걸 볼 수 있다.
         * 이 문제를 해결해보자. 위에처럼 onBackpressureBuffer() 안에 값을 주는 방법도 있다. demo2 참고
         *
         */
        flux.subscribe(Util.subscriber("sam"));
        //subscriber에 지연을 줬음
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 1; i <= 1000; i++) {
            EmitResult result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }



    }

    private static void demo2(){
        //  System.getProperty("reactor.bufferSize.small", "16");

        // directBestEffort는 버퍼가 꽉차면 버퍼에 담지 않고 바로 subscriber에게 전달한다.
        // 이렇게 하면 mike가 다 받지 못한다.
        // 아직도 문제가 존재한다.
        //그래서 아래서 mike에게 onBackpressureBuffer()를 주어서 해결해보자.
        Many<Object> sink = Sinks.many().multicast().directBestEffort(/*100*/);


        // handle through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        //subscriber에 지연을 줬음
        flux.onBackpressureBuffer().delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 1; i <= 1000; i++) {
            EmitResult result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }



    }
}
