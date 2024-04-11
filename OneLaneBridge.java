import java.util.ArrayList;

/**
 * Extends the abstract Bridge class
 * This class allows cars to enter and exit a one lane bridge
 * @author Tyler
 */


public class OneLaneBridge extends Bridge {
    private int bridgeLimit;
    private int carsOnBridge;

    // Constructor initializing bridge limit and cars on bridge
    public OneLaneBridge(int bridgeLimit){
        super();
        this.bridgeLimit = bridgeLimit;
        this.carsOnBridge = 0;
    }

    /**
     * Allows cars to arrive at bridge. Waits until safe for the car to arrive, 
     * depending on bridge limit and direction. when they succesfully arrive update 
     * entry time then adds car to bridge before incrementing carsOnBridge count.
     * Notifies waiting threads after.
     *
     * @param c The car object representing the arriving cars.
     * @throws InterruptedException If any thread is interrupted while waiting.
     */
    @Override
    public synchronized void arrive(Car c) throws InterruptedException {
        while (carsOnBridge >= bridgeLimit || (carsOnBridge > 0 && c.getDirection() != direction)) {
            wait();
        }

        // If no cars, get the direction
        if (carsOnBridge == 0) {
            direction = c.getDirection();
        }

        // update entry time, add car to bridge, and increment count
        c.setEntryTime(currentTime);
        bridge.add(c);
        carsOnBridge++;
        System.out.println("Bridge (dir=" + direction + "): " + bridge);
        currentTime++;
        notifyAll();
    }

    /**
     * Allows cars to exit the bridge. Waits until safe for cars to exit, 
     * then allows them to leave and decrements carsOnBrige count.
     * Notifies all waiting threads after they exit.
     *
     * @param c The car object representing cars that will exit.
     * @throws InterruptedException If any thread is interrupted while waiting.
     */
    @Override
    public synchronized void exit(Car c) throws InterruptedException {
        // wait until safe
        while(!bridge.isEmpty() && bridge.get(0) != c) {
            wait();
        }

        // remove car and decrement count
        if(!bridge.isEmpty()){
            bridge.remove(0);
            carsOnBridge--;
            System.out.println("Bridge (dir=" + direction + "): " + bridge);
            notifyAll();
        }
    }
}