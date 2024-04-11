/**
 * Runs all threads
 */

public class BridgeRunner {

    public static void main(String[] args) {
		// check the number of arguments inputted
        if (args.length != 2) {
            System.out.println("Usage: java BridgeRunner <bridge limit> <num cars>");
            return;
        }

        int bridgeLimit, numCars;
        try {
			// check if args are valid
            bridgeLimit = Integer.parseInt(args[0]);
            numCars = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
			// handle invalid args
            System.out.println("Error: bridge limit and/or num cars must be positive integers.");
            return;
        }

        if (bridgeLimit <= 0 || numCars <= 0) {
			// no negative numbers!
            System.out.println("Error: bridge limit and/or num cars must be positive.");
            return;
        }

		// create bridge object
        Bridge bridge = new OneLaneBridge(bridgeLimit);
		// Create array to hold car threads
        Thread[] carThreads = new Thread[numCars];

        for (int i = 0; i < numCars; i++) {
			// Create car object
            Car car = new Car(i, bridge);
			// Create thread for each car and start them
            carThreads[i] = new Thread(car);
            carThreads[i].start();
        }

        for (Thread thread : carThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("All cars have crossed!!");
    }
}
