package org.youyk.sec03;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

public class Lec04FluxFromStream {
    public static void main(String[] args) {
        List<Integer> list = List.of(1,2,3,4);
        Stream<Integer> stream = list.stream();

        stream.forEach(System.out::println);
/*        //자바 stream은 한번 사용하면 닫힌다. 그래서 두번째 호출은 안된다.
        stream.forEach(System.out::println); //stream has already been operated upon or closed 이런 에러가 뜰것이다 두번째 호출은 안된다.*/

        //Flux.fromStream(list::stream)는 Java의 Stream을 Flux로 변환하는 코드
        /**
         * Java의 Stream은 한 번 사용하면 닫히게 되어 있습니다.
         * 즉, 한 번 소비한 후에는 다시 사용할 수 없습니다.
         * 그러나 Flux.fromStream(list::stream)에서는
         * list::stream이라는 Supplier(공급자)를 인자로 받습니다.
         * 이 Supplier는 Flux가 데이터를 필요로 할 때마다 새로운 Stream을 생성합니다.
         * 따라서 Flux는 여러 번 구독할 수 있습니다.
         */
        Flux<Integer> flux = Flux.fromStream(list::stream);
        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));
    }

}
