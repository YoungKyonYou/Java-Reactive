package org.youyk.sec04;

import org.youyk.common.Util;
import org.youyk.sec04.assignment.FileReaderServiceImpl;

import java.nio.file.Path;

public class Lec09Assignment {
    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/sec04/file.txt");
        FileReaderServiceImpl fileReaderService = new FileReaderServiceImpl();
        fileReaderService.read(path)
                .takeUntil(s -> s.equalsIgnoreCase("line17"))
                .subscribe(Util.subscriber());
    }
}
