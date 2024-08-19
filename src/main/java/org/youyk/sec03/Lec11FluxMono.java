package org.youyk.sec03;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec11FluxMono {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.range(1, 10);
        //flux to mono
        flux.next()
                .subscribe(Util.subscriber());

        //위와 같은 코드
        Mono.from(flux)
                .subscribe(Util.subscriber());
    }

    private static void monoToFlux(){
        //mono to flux
        Mono<String> mono = getUsername(1);
        save(Flux.from(mono));
    }

    private static Mono<String> getUsername(int userId){
        return switch (userId){
            case 1 -> Mono.just("sam");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("invalid input"));
        };
    }

    private static void save(Flux<String> flux){
        flux.subscribe(Util.subscriber());
    }
}
