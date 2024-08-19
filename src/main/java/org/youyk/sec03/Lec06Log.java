package org.youyk.sec03;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

public class Lec06Log {
    public static void main(String[] args) {
        /**
         * Reactor에서는 각 연산자가 이전 연산자의 구독자(subscriber) 역할을 합니다.
         * 이는 각 연산자가 이전 연산자로부터 데이터를 받아 처리하고,
         * 그 결과를 다음 연산자에게 전달하기 때문입니다.
         * 이 코드에서는 log() 연산자와 map() 연산자가 있습니다.
         * log() : 이 연산자는 Flux.range(1, 5)로부터 데이터를 받아 로깅하고, 그대로 다음 연산자에게 전달합니다. 따라서 log()는 Flux.range(1, 5)의 구독자입니다.
         * map(i->Util.faker().name().firstName()) : 이 연산자는 log()로부터 데이터를 받아 처리합니다. 여기서 처리란, 각 숫자를 Util.faker().name().firstName()를 사용하여 이름으로 변환하는 것을 의미합니다. 따라서 map()은 log()의 구독자입니다.
         * 마지막으로, subscribe(Util.subscriber())는 map() 연산자의 구독자입니다. 이 구독자는 map() 연산자가 처리한 결과를 받아 최종적으로 처리합니다.
         */

        //log는 producer와 subscriber 사이에 위치하는 operator이다.
        //이 메소드는 Flux의 모든 신호를 로깅합니다. 이는 onNext, onComplete, onError 등의 신호를 포함합니다.
        // 이 예제에서는 각 숫자가 방출될 때마다 로그 메시지가 출력됩니다.
        Flux.range(1,  5)
                .log()
                //map operation이 subscriber이며 log의 subscriber이다.
                .map(i->Util.faker().name().firstName())
                .subscribe(Util.subscriber());
    }
}
