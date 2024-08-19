package org.youyk.sec04;

import org.youyk.common.Util;
import org.youyk.sec04.helper.NameGenerator;
import reactor.core.publisher.Flux;

public class Lec02FluxCreateRefactor {

    public static void main(String[] args) {
        NameGenerator generator = new NameGenerator();
        Flux<String> flux = Flux.create(generator);
        flux.subscribe(Util.subscriber());

        for (int i = 0; i < 10; i++) {
            generator.generate();
        }
    }
}
