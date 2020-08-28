package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.SocketAddress;

public class DomainSocketClientTriggerVertical extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        NetClient netClient = vertx.createNetClient();
        SocketAddress addr = SocketAddress.domainSocketAddress("/var/tmp/myservice.sock");
        netClient.connect(addr, ar -> {
            if (ar.succeeded()) {
                // Connected

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
