package org.youyk.sec04;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxCreate {
    public static void main(String[] args) {
/*        Flux.create(fluxSink ->{
            fluxSink.next(1);
            fluxSink.next(2);
            fluxSink.complete();
        })
                .subscribe(Util.subscriber());*/


        //특정 조건을 줌으로서 멈추게 할 수 있음
/*        Flux.create(fluxSink->{
            for(int i=0; i<10; i++){
                fluxSink.next(Util.faker().country().username());
            }
            fluxSink.complete();
        }).subscribe(Util.subscriber());*/

        //canada가 나오면 멈추게 하기
        Flux.create(fluxSink->{
            for(int i=0; i<10; i++){
                String country;
                do{
                    country = Util.faker().country().name();
                    fluxSink.next(country);
                }while(!country.equalsIgnoreCase("canada"));
                fluxSink.complete();
            }
            fluxSink.complete();
        }).subscribe(Util.subscriber());
    }
}
