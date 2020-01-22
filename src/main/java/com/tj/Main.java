package com.tj;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

/**
 * Created by tianjian on 2020/1/8.
 */
public class Main {
    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();
        DeploymentOptions options = new DeploymentOptions().setWorker(true);

        vertx.deployVerticle(DeployVerticle.class.getName(), options);
    }
}
