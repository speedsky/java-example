package com.my.main;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

private static Lock lock = new ReentrantLock();
	
	private static int num1 = 0;
	private static int num2 = 0;
	
	
	public static void main(String[] args) {
		lockDemo();
		SyncDemo();
	}
	/**
	 * 本机测试下50万自增基本能确定性能,但是不是特别明显,50万差距还是挺大的
	 * 50万以下数据synchronized优于Lock
	 * 50万以上数据Lock优于synchronized
	 *  累加：500000    ReentrantLock锁：耗时-16
	 *  累加：500000    synchronized锁：耗时-15
	 *  累加：1000000		ReentrantLock锁：耗时-29
	 *	累加：1000000		synchronized锁：耗时-30
	 * 
	 */
	public static void lockDemo(){
		long start = System.currentTimeMillis();
		for(int i=0;i<500000;i++){
			final int num = i;
			new Runnable() {
				@Override
				public void run() {
					lock(num);
				}
			}.run();
		}
		long end = System.currentTimeMillis();
		System.out.println("累加："+num1);
		System.out.println("ReentrantLock锁：耗时-"+ (end-start));
	}
	public static void SyncDemo(){
		long start = System.currentTimeMillis();
		for(int i=0;i<500000;i++){
			final int num = i;
			new Runnable() {
				@Override
				public void run() {
					sync(num);
				}
			}.run();
		}
		long end = System.currentTimeMillis();
		System.out.println("累加："+num2);
		System.out.println("synchronized锁：耗时-"+ (end-start));
	}
    public static void lock(int i){
    	lock.lock();
    	num1 ++;
    	lock.unlock();
    }
    public static synchronized void sync(int i){
    	num2 ++;
    }

}
