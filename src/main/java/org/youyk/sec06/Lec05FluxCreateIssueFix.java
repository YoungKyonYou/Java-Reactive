package org.youyk.sec06;

import org.youyk.common.Util;
import org.youyk.sec04.helper.NameGenerator;
import reactor.core.publisher.Flux;

public class Lec05FluxCreateIssueFix {

    //hot publisher
    public static void main(String[] args) {
        NameGenerator generator = new NameGenerator();
        Flux<String> flux = Flux.create(generator).share();
        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

        for (int i = 0; i < 10; i++) {
            generator.generate();
        }
    }
}
