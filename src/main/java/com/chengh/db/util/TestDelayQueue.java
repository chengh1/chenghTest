package com.chengh.db.util;

import com.chengh.db.entity.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.DelayQueue;

/**
 *
 * 2019年07月22日15:12:02
 */
public class TestDelayQueue {
    //是否开启自动取消功能
    int isStarted = 1;
    //延迟队列，用来存放订单对象
    DelayQueue<Order> queue = new DelayQueue();

    public static void main(String[] args) {
        TestDelayQueue testDelayQueue = new TestDelayQueue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //新建一个线程，用来模拟定时取消订单job
        Thread t1 = new Thread(() -> {
            System.out.println("开启自动取消订单job,当前时间："+ LocalDateTime.now().format(formatter));
            while (testDelayQueue.isStarted == 1) {
                try {
                    Order order = testDelayQueue.queue.take();
                    order.setStatus("CANCELED");

                    System.out.println("订单：" + order.getOrderNo() + "付款超时，自动取消，当前时间："+ LocalDateTime.now().format(formatter));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        //新建一个线程，模拟提交订单
        Thread t2 = new Thread(() -> {
            //定义最早的订单的创建时间
            long beginTime = System.currentTimeMillis();
            //下面模拟6个订单，每个订单的创建时间依次延后3秒
            testDelayQueue.queue.add(new Order("SO001", "A", 100, "CREATED", new Date(beginTime), new Date(beginTime + 3000)));
            beginTime += 3000L;
            testDelayQueue.queue.add(new Order("SO002", "B", 100, "CREATED", new Date(beginTime), new Date(beginTime + 3000)));
            beginTime += 3000L;
            testDelayQueue.queue.add(new Order("SO003", "C", 100, "CREATED", new Date(beginTime), new Date(beginTime + 3000)));
            beginTime += 3000L;
            testDelayQueue.queue.add(new Order("SO004", "D", 100, "CREATED", new Date(beginTime), new Date(beginTime + 3000)));
            beginTime += 3000L;
            testDelayQueue.queue.add(new Order("SO005", "E", 100, "CREATED", new Date(beginTime), new Date(beginTime + 3000)));
            beginTime += 3000L;
            testDelayQueue.queue.add(new Order("SO006", "F", 100, "CREATED", new Date(beginTime), new Date(beginTime + 3000)));
        });
        t2.start();
    }

}
