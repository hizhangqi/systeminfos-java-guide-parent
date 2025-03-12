package com.systeminfos.bigdata;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * @Author laoyan
 * @Description TODO
 * @Date 2022/8/1 14:11
 * @Version 1.0
 */
public class TestXLH {

    public static void main(String[] args) throws Exception {
        User user = new User("zhangsan", 20);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("/Users/zhangqi/Documents/Workspaces/systeminfos/systeminfos-java-parent/data-parent/hadoop-demo/src/main/resources/user/user1.txt"));
        objectOutputStream.writeObject(user);
        objectOutputStream.close();

        UserWritable user2 = new UserWritable("zhangsan", 20);
        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new FileOutputStream("/Users/zhangqi/Documents/Workspaces/systeminfos/systeminfos-java-parent/data-parent/hadoop-demo/src/main/resources/user/user2.txt"));
        user2.write(objectOutputStream2);
        objectOutputStream2.close();

    }
}
