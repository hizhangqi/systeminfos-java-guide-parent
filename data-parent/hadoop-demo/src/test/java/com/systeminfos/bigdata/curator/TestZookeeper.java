package com.systeminfos.bigdata.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author laoyan
 * @Description TODO
 * @Date 2022/8/4 9:49
 * @Version 1.0
 * <p>
 * java 连接zk
 * 创建节点
 * 创建数据
 * 删除节点
 * 设置监听等
 * <p>
 * （了解）
 */
public class TestZookeeper {
    CuratorFramework newClient;

    @Before
    public void init() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 2);
        newClient = CuratorFrameworkFactory.newClient("bigdata01:2181,bigdata02:2181,bigdata03:2181", 1000, 1000, retryPolicy);
        newClient.start();
    }

    @After
    public void destory() {
        // 记得断开连接
        newClient.close();
    }

    @Test
    public void testCreate() throws Exception {

        // 各种操作
        // 创建一个永久节点，不带数据
        newClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/hello1","老闫真帅".getBytes());
        // 创建一个临时节点，带数据
        newClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/hello2", "老闫真帅".getBytes());

        Thread.sleep(10000);

    }

    @Test
    public void testSet() throws Exception {
        newClient.setData().forPath("/hello1", "我是数据".getBytes());
    }

    @Test
    public void testDelete() throws Exception {
        // delete
        newClient.delete().forPath("/hello1");
        //deleteall
        newClient.delete().deletingChildrenIfNeeded().forPath("/bigdata001");
    }

    @Test
    public void testGetData() throws Exception {
        byte[] bytes = newClient.getData().forPath("/a1");
        System.out.println(new String(bytes));
    }

    @Test
    public void testWatch() throws Exception {

        TreeCache treeCache = new TreeCache(newClient, "/hello1");
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                ChildData data = treeCacheEvent.getData();
                if (data != null) {
                    switch (treeCacheEvent.getType()) {
                        case NODE_ADDED:
                            System.out.println(data.getData());
                            // getPath()  当前正在操作的路径
                            if (data.getData() != null) {
                                System.out.println("这个路径上的数据是什么：" + new String(data.getData()));
                            }
                            System.out.println("新增了个路径：" + data.getPath());
                            break;
                        case NODE_REMOVED:
                            System.out.println("删除了:" + data.getPath());
                            break;
                        case NODE_UPDATED:
                            System.out.println("这个路径" + data.getPath() + "修改了节点数据,新的数据是：" + new String(data.getData()));
                            break;
                    }
                }
            }
        });

        treeCache.start();

        Thread.sleep(1000 * 120);
    }
}
