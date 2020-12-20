package io.tatum.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class Promise {
    public static <T> CompletableFuture<List<T>> all(final List<CompletableFuture<T>> futures) {
        CompletableFuture<List<T>> completableFuture = new CompletableFuture<>();
        final int total = futures.size();
        final AtomicInteger counter = new AtomicInteger();
        final List<T> results = new ArrayList<>(futures.size());

        futures.stream()
            .forEach(f -> f.whenComplete((ok, error) -> {
                if (error == null) {
                    synchronized (results) {
                        results.add(ok);
                    }
                }

                int totalDone = counter.incrementAndGet();
                if (totalDone == total) {
                    completableFuture.complete(results);
                }
            }));

        return completableFuture;
    }
}
