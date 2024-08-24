package org.youyk.sec09.helper;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import org.youyk.sec09.Lec01StartWith;
import reactor.core.publisher.Flux;

public class NameGenerator {
    private static final Logger log = LoggerFactory.getLogger(NameGenerator.class);
    private final List<String> redis = new ArrayList<>(); //for demo

    public Flux<String> generateNames(){
        return Flux.generate(sink -> {
            log.info("generating username");
            Util.sleepSeconds(1);
            String name = Util.faker().name().firstName();
            redis.add(name);
            sink.next(name);
        })
                //캐시에서 먼저 가져온다 없으면 add한다
                .startWith(redis)
                .cast(String.class);
    }
}
