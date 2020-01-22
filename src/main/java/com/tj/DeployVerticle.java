package com.tj;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * Created by tianjian on 2020/1/8.
 */
public class DeployVerticle extends AbstractVerticle {
    public void start() {

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route(HttpMethod.POST,"/deploy").handler(routingContext -> {

            // 所有的请求都会调用这个处理器处理
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");

            // 写入响应并结束处理
            response.end("Hello World from Vert.x-Web!");
        });
        server.requestHandler(router::accept).listen(7200);
    }

}
