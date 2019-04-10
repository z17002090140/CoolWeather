package com.example.admin.helloworld;

public class ABC {
    public static int count=0;
    public static void main(String args[]){
//        System.out.println("Hello World One!");
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                System.out.println("Hello World Two!");
//            }
//        };
//        for(int i=0; i<10;i++){
//            final int j = i;
//            (new Thread(){
//                @Override
//                public void run() {
//                    System.out.println("  "+j);
//                }
//            }).start();
//           (new Thread() {
//                @Override
//                public void run() {
//                    count = count + 1;
//                    System.out.println();
//
//                }
//            };thread.start();
//        }
        (new Thread(){
            @Override
            public void run() {
                System.out.println("Hello World");
                while(true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countCar();
                }
            }
        }).start();

        (new Thread(){
            @Override
            public void run() {
                while(true){
                    System.out.println(count);
                }
            }
        }).start();
    }
    synchronized static void printCar(){
        if(count!=0){
            System.out.println("过去的车辆数=>"+count);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count = 0;
        }
    }
    synchronized static void countCar(){
        count++;
        System.out.println("又过了一辆车");
    }
}
