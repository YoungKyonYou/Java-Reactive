package org.youyk.sec10;


import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.youyk.common.Util;
import org.youyk.sec10.assigment.window.FileWriter;
import reactor.core.publisher.Flux;

public class Lec04WindowAssignment {

    public static void main(String[] args) {

        AtomicInteger counter = new AtomicInteger(0);
        String fileNameFormat = "src/main/resources/sec10/file%d.txt";

        eventStream()
                .window(Duration.ofMillis(1800)) // just for demo
                .flatMap(flux -> FileWriter.create(flux, Path.of(fileNameFormat.formatted(counter.incrementAndGet()))))
                .subscribe();


        Util.sleepSeconds(60);

    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "event-" + (i + 1));
    }

}
