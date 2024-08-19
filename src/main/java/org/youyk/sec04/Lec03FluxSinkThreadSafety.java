package org.youyk.sec04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import org.youyk.sec02.Lec01LazyStream;
import org.youyk.sec04.helper.NameGenerator;
import reactor.core.publisher.Flux;

import java.util.ArrayList;


/*
  Flux Sink is Thread safe
 */
public class Lec03FluxSinkThreadSafety {
    private static final Logger log = LoggerFactory.getLogger(Lec03FluxSinkThreadSafety.class);

    public static void main(String[] args) {
        demo2();

    }

    //not thread safe
    private static void demo1(){
        ArrayList<Integer> list = new ArrayList<>();
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        };
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
        Util.sleepSeconds(3);
        log.info("list size: {}", list.size());
    }

    //arraylist is not thread safe
    //flux sink is thread safe. we get all the 10000 items safely into array list
    private static void demo2(){
        ArrayList<String> list = new ArrayList<String>();
        NameGenerator generator = new NameGenerator();
        Flux<String> flux = Flux.create(generator);
        flux.subscribe(list::add);

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                generator.generate();
            }
        };
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
        Util.sleepSeconds(3);
        log.info("list size: {}", list.size());
    }
}
