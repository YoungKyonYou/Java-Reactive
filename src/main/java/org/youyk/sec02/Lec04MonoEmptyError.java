package org.youyk.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Mono;

public class Lec04MonoEmptyError {
    private static final Logger log = LoggerFactory.getLogger(Lec04MonoEmptyError.class);

    public static void main(String[] args) {
/*        getUsername(3)
                .subscribe(Util.subscriber());*/

        //아래와 같이 err 도 구현을 해줘야지 error가 발생하지 않는다
        getUsername(3)
                .subscribe(
                        s-> System.out.println(s),
                        err ->{}
                );
    }

    private static Mono<String> getUsername(int userId){
        return switch(userId){
            case 1 -> Mono.just("sam");
            case 2 -> Mono.empty(); // null
            default -> Mono.error(new RuntimeException("invalid input"));
        };
    }
}
