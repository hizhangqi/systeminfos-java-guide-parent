package com.systeminfos.springboot3demo.jdk21;

public class Record {
    record Point(int x, int y) {
    }
    
    static void testRecordPattern(Object obj) {
        if (obj instanceof Point(int x, int y)) {
            System.out.println("Point coordinates: x=" + x + ", y=" + y);
        }
    }

    public static void main(String[] args) {
        Point point = new Point(10, 20);
        testRecordPattern(point);
        testRecordPattern(new Object());
    }
}
