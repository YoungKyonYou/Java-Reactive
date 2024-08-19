package org.youyk.sec02;

import org.youyk.common.Util;
import org.youyk.sec02.assignment.FileServiceImpl;

public class Lec12Assignment {
    public static void main(String[] args) {
        FileServiceImpl fileService = new FileServiceImpl();

        fileService.write("file.txt", "This is a test file")
                .subscribe(Util.subscriber());

        fileService.read("file.txt")
                .subscribe(Util.subscriber());

        fileService.delete("file.txt").subscribe(Util.subscriber());
    }
}
