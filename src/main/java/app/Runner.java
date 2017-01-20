package app;

import cases.to.play.instrumenting.Worker;

import java.util.Objects;

public class Runner {

    public static void main(String[] args) {
        System.out.println("Start Runner...");
        final Worker worker = new Worker();
        System.out.println("Validating instrumentations...");
        if (!Objects.equals(worker.toString(), "transformed")) {
            throw new RuntimeException("Worker has an unexpected toString value: " + worker.toString());
        }
        System.out.println("End Runner...");
    }
}
