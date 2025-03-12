package com.systeminfos.shardingJdbc4.spi;

import java.util.ServiceLoader;

public class SpiDemo {

    public static void main(String[] args) {
        ServiceLoader<MyService> services = ServiceLoader.load(MyService.class);
        services.forEach(
                MyService::execute
        );
    }


}
