package org.youyk.sec12;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

/*
    We can emit multiple messages, but there will be only one subscriber
 */

/**
 * Sink를 사용하면, 외부에서 실시간으로 발생하는 데이터를 제어할 수 있고,
 * 이를 Flux로 변환하여 구독자에게 발행할 수 있습니다.
 * 반면, Flux만을 사용하면 미리 정의된 데이터 시퀀스를 발행하는 데 유용하지만,
 * 실시간 데이터를 처리하는 유연성은 부족합니다.
 * 이러한 차이점을 이해하는 것이 중요합니다.
 */
public class Lec02SinkUnicast {
    public static void main(String[] args) {
        demo2();
    }

    /**
     * onBackpressureBuffer() 메소드는 backpressure 전략을 설정합니다.
     * Backpressure는 데이터 소스가 데이터를 빠르게 생성할 때, 데이터를 소비하는 측이 처리할 수 없는 상황을 말합니다.
     * onBackpressureBuffer()는 이런 상황에서 데이터를 버퍼에 저장하도록 설정합니다.
     * 이 버퍼는 제한이 없으므로 (즉, 크기가 무한대입니다),
     * 데이터 소스가 매우 빠르게 데이터를 생성하더라도 데이터 소비자가 처리할 수 있을 때까지 데이터를 저장할 수 있습니다.
     */
    private static void demo1(){
        //handle through which we would push items
        // onBackPressureBuffer - unbounded queue(크기 제한이 없는 큐라는 뜻)
        // unicast() 메소드는 이 Sink가 단일 구독자를 가질 수 있음을 나타냅니다.
        // 즉, 한 번에 하나의 구독자만이 이 Sink에 연결될 수 있습니다.
        Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();


        // handle through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();

        sink.tryEmitNext("hi");
        sink.tryEmitNext("houw are you");
        sink.tryEmitNext("?");

        flux.subscribe(Util.subscriber("sam"));

    }

    private static void demo2(){
        //handle through which we would push items
        // onBackPressureBuffer - unbounded queue(크기 제한이 없는 큐라는 뜻)
        // unicast() 메소드는 이 Sink가 단일 구독자를 가질 수 있음을 나타냅니다.
        // 즉, 한 번에 하나의 구독자만이 이 Sink에 연결될 수 있습니다.
        Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();


        // handle through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();

        sink.tryEmitNext("hi");
        sink.tryEmitNext("houw are you");
        sink.tryEmitNext("?");

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));

    }
}
