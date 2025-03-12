package com.systeminfos.bigdata;

import java.io.*;

class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class JavaSerializationTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Person person = new Person("Alice", 30);

        // 序列化
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(person);

        byte[] serializedData = byteOut.toByteArray();
        System.out.println("Java序列化数据大小: " + serializedData.length);

        // 反序列化
        ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(serializedData));
        Person deserializedPerson = (Person) objIn.readObject();
    }
}