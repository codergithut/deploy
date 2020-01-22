package com.tj;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.netty.util.internal.MacAddressUtil;

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by tianjian on 2020/1/8.
 */
public class DeployVerticle extends AbstractVerticle {

    Cache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(60 * 3, TimeUnit.SECONDS)
            .build();
    public void start() {

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route(HttpMethod.POST,"/deploy").handler(routingContext -> {

            // 所有的请求都会调用这个处理器处理
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");
            System.out.println("we get data");
            try {
                System.out.println(exec("nohup ./root/jar/task/starup.sh &"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 写入响应并结束处理
            response.end("Hello World from Vert.x-Web!");
        });
        server.requestHandler(router::accept).listen(8082, "0.0.0.0");
    }

    public static String exec(String command) throws InterruptedException {
        String returnString = "";
        Process pro = null;
        Runtime runTime = Runtime.getRuntime();
        if (runTime == null) {
            System.err.println("Create runtime false!");
        }
        try {
            pro = runTime.exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                returnString = returnString + line + "\n";
            }
            input.close();
            output.close();
            pro.destroy();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return returnString;
    }

}
