package com.learn.thread.chap7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 在开发中经常会遇到hook线程，比如为了防止某个程序被重复启动，在进程启动时候会创建一个lock文件
 * 进程收到中断信号的时候会删除这个lock文件，
 * hook线程只有在退出信号的时候会被执行，尽量不要在hook线程中执行一些耗时非常长的操作，会导致其他线程不能退出
 * 以下代码为书上利用hook线程特点，模拟一个防止重复启动的程序
 *
 * */
public class PreventDuplicated {
    private final static String LOCK_PATH = "";

    private final static String LOCK_FILE = ".lock";

    private final static String PERMISSION = "rw-----";

    public static void main(String[] args) throws IOException {
        //检查是否存在.lock文件
        checkRunning();

        //注入hook线程，在程序退出删除lock文件
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("the program received kill signal");
                getLockFile().toFile().delete();
            }
        }));

        //简单模拟当前程序正在运行
        for (;;){
            try{
                TimeUnit.MILLISECONDS.sleep(1);
                System.out.println("program is running");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private static void checkRunning() throws IOException{
        Path path = getLockFile();
        if(path.toFile().exists()) throw new RuntimeException("the program already running");
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString(PERMISSION);
        Files.createFile(path,PosixFilePermissions.asFileAttribute(perms));
    }

    private static Path getLockFile(){
        return Paths.get(LOCK_PATH,LOCK_FILE);
    }

}
