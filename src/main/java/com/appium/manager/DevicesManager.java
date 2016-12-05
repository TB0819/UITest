package com.appium.manager;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.github.cosysoft.device.android.AndroidDevice;

public class DevicesManager<T extends AndroidDevice> {
	protected final ReentrantLock lock;
    protected final Condition notEmpty;
    protected final TreeSet<AndroidDevice> devicesInUse = new TreeSet<>();
    protected final Set<T> devices = new TreeSet<>();

    DevicesManager(){
    	lock = new ReentrantLock(true);
        notEmpty = lock.newCondition();
    }
    
    public AndroidDevice take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            // log.debug("devices in use size {}", devicesInUse);
            while (devicesInUse.isEmpty()) {
                System.out.println("take need wait ....");
                notEmpty.await();
            }
            return devicesInUse.pollFirst();

        } finally {
            lock.unlock();
        }
    }
    
    public void put(AndroidDevice device) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            devicesInUse.add(device);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }

    }
    
    public Set<T> getDevices() {
        return new TreeSet<T>(devices);
    }
}
