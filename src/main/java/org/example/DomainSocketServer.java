package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.SocketAddress;

public class DomainSocketServer extends AbstractVerticle {


    @Override
    public void start(Promise<Void> startPromise) {
        vertx.createHttpServer().requestHandler(req -> {
            // Handle application
        })
                .listen(SocketAddress.domainSocketAddress("/var/tmp/myservice.sock"), ar -> {
            if (ar.succeeded()) {
                // Bound to socket
                startPromise.complete();
            }
            else {
                startPromise.fail(ar.cause());
            }
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        stopPromise.complete();
    }
}
