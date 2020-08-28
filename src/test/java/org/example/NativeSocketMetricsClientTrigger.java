package org.example;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.micrometer.MetricsDomain;
import io.vertx.micrometer.MicrometerMetricsOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;

@ExtendWith(VertxExtension.class)
public class NativeSocketMetricsClientTrigger {

    @Test
    @Timeout(value = 2, timeUnit = TimeUnit.SECONDS)
    void triggerNpe(Vertx v, VertxTestContext testContext) {

        VertxOptions vertxOptions = new VertxOptions();
        vertxOptions.setPreferNativeTransport(true); // Enable UNIX domain socket on UNIX

        // Turn on Metrics
        vertxOptions.setMetricsOptions(new MicrometerMetricsOptions() // Uncomment this filter to make the unit test
                // work
                //.addDisabledMetricsCategory(MetricsDomain.NET_CLIENT)
                .setEnabled(true)

        );
        Vertx vertx = Vertx.vertx(vertxOptions);

        vertx.deployVerticle(new DomainSocketServer(), h -> {
            // Deploy client
            vertx.deployVerticle(new DomainSocketClientTriggerVertical(), ch -> {
                if (ch.succeeded()) {
                    testContext.completeNow();
                }
                else {
                    ch.cause().printStackTrace();
                    testContext.failNow(ch.cause());
                }
            });

        });
    }

}
