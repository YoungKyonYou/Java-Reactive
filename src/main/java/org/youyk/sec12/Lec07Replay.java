package org.youyk.sec12;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

public class Lec07Replay {
    public static void main(String[] args) {
        demo1();
    }
    private static void demo1(){
        //handle through which we would push items
        // replay().all()은 모든 데이터를 저장하고 있다가 새로운 subscriber가 들어오면 모든 데이터를 전달한다.
        //jake가 모든 메시지를 받을 수 있게 한다.
//        Many<Object> sink = Sinks.many().replay().all();
        //이렇게 하면 jake는 "?"부터 받을 수 있다.
        Many<Object> sink = Sinks.many().replay().limit(1);



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
}
