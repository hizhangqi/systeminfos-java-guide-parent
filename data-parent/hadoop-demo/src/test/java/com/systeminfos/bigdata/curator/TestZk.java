package com.systeminfos.bigdata.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

public class TestZk {

    CuratorFramework curator = null;

    @Before
    public void init() {
        // 1. 创建一个连接(自动重连)
        RetryNTimes retry = new RetryNTimes(2, 5);// 重连10次，每次间隔1000秒
        // 2. 创建一个客户端对象。
        curator = CuratorFrameworkFactory.newClient("bigdata01:2181,bigdata02:2181,bigdata03:2181", retry);
        // 3. 启动客户端
        curator.start();
    }

    // 新增节点  永久的
    @Test
    public void testAddNode() throws Exception {
        // 这个里面可以创建四种类型的节点  永久节点 临时节点 永久序列节点 临时序列节点
        String s = curator.create().withMode(CreateMode.PERSISTENT).forPath("/a1", "测试".getBytes());
        System.out.println(s);
    }

    // 获取zk的数据
    @Test
    public void testGetData() throws Exception {
        byte[] bytes = curator.getData().forPath("/a1");
        // byte数组如何变为字符串，使用 String 的构造方法
        System.out.println(new String(bytes));
    }

    // 修改数据
    @Test
    public void testSetData() throws Exception {
        curator.setData().forPath("/a1", "我是打水哥".getBytes());
    }

    // 删除节点
    @Test
    public void testDeleteNode() throws Exception {
        curator.delete().forPath("/a1");
    }

    // 判断一个节点是否存在
    @Test
    public void testIsExists() throws Exception {
        // Stat 表示一个节点信息，如果获取不到节点信息，为null
        Stat stat = curator.checkExists().forPath("/a1");
        System.out.println(stat == null ? "该节点不存在" : "存在");
    }

    // 获取子节点信息，只能获取一层
    @Test
    public void testGetChild() throws Exception {
        List<String> list = curator.getChildren().forPath("/");
        for (String fileName : list) {
            System.out.println(fileName);
        }
    }

    // 给一个节点添加监听器  get /abc watch
    @Test
    public void testSetWatch() throws Exception {
        NodeCache nodeCache = new NodeCache(curator, "/a1");
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                byte[] data = nodeCache.getCurrentData().getData();
                System.out.println(new String(data));
            }
        });
        // 这个地方添加这句话的意思是不让程序跑结束，否则一切都白搭了
        Thread.sleep(Integer.MAX_VALUE);
    }


    // 给一个节点的子节点添加监听器  ls /abc watch
    @Test
    public void testSetWatchChild() throws Exception {
        PathChildrenCache cache = new PathChildrenCache(curator, "/a1", true);
        cache.start();
        // 添加监听
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                // 需要知道是新增了还是修改了还是删除了
                System.out.println(event.getType());
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("添加了一个节点");
                        System.out.println(event.getData().getPath());
                        System.out.println(Objects.nonNull(event.getData()) ? new String(event.getData().getData()) : null);
                        break;
                    case CHILD_UPDATED:
                        System.out.println("数据更新了");
                        System.out.println(event.getData().getPath());
                        System.out.println(Objects.nonNull(event.getData()) ? new String(event.getData().getData()) : null);
                        break;
                    case CHILD_REMOVED:
                        System.out.println("节点被删除了");
                        System.out.println(event.getData().getPath());
                        break;
                    default:
                        System.out.println("节点发生了其他变化");
                        break;
                }
            }
        });

        // 这个地方添加这句话的意思是不让程序跑结束，否则一切都白搭了
        Thread.sleep(Integer.MAX_VALUE);
    }

}
