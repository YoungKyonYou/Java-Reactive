package org.youyk.sec12;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

public class Lec04Multicast {

    public static void main(String[] args) {
        demo2();
    }

    private static void demo1(){
        //handle through which we would push items
        // onBackPressureBuffer - bounded queue(크기 제한이 있는 큐라는 뜻)
        Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();


        // handle through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));

        sink.tryEmitNext("hi");
        sink.tryEmitNext("houw are you");
        sink.tryEmitNext("?");

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("jake"));
        sink.tryEmitNext("new message");

    }

    // warmup
    private static void demo2(){
        //handle through which we would push items
        // onBackPressureBuffer - bounded queue(크기 제한이 있는 큐라는 뜻)
        Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();


        // handle through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();


        //아래 메시지들은 위에 subscriber가 없다면 queueu에 축적되는데 subscriber가 생기면 그때부터 메시지를 받는다.
        //그런데 첫 subscriber가 생기기 전에 메시지가 축적되어 있으면 그 메시지들은 subscriber가 생기면 바로 전달된다.
        //sam만 3개의 메시지를 받는다. 나머지는 1개의 메시지만 받는다.(new message)이거 말이다
        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));
        flux.subscribe(Util.subscriber("jake"));

        sink.tryEmitNext("new message");

    }
}
