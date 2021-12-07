public class PrimeNumbers {

    //keeps track of prime numbers
    static int i;

    //used for hundred threads - 100 prime numbers per thread
    static int hundred = 100;

    //used for thousand threads - 10 prime numbers per thread
    static int ten = 10;

    public void primeCalc(int range)
    {
        String primeNumbers = "";
        int num = 0;

        for (i = i ; i <= range; i++) {
            int c = 0;
            for (num = i; num >= 1; num--) {
                
                // check if prime
                if (i % num == 0) {
                    
                    // increment counter
                    c = c + 1;
                }
            }
            
            if (c == 2) {
                primeNumbers = primeNumbers + i + " ";
            }
        }
        System.out.println("\nPrime numbers: \n" + primeNumbers);
        
    }

    /*** Thread runs entire prime number calculation */
    public class one_thread extends Thread
    {
        public void run()
        {
            System.out.println("Thread " + Thread.currentThread().getId());
            
            //Calcualtes all prime number from 1 - 10,000
            primeCalc(10000);

           
        }
        //loop to 10,000 for prime numbers
    }

    public class hundred_threads extends Thread
    {
        public void run()
        {
            System.out.println("Thread " + Thread.currentThread().getId());
            
            //Calcualtes all prime number from 1 - 10,000
            //this does every hundred prime numbers
            primeCalc(hundred);
            hundred+=100;
        }
    }

    public class thousand_threads extends Thread
    {
        public void run()
        {
            System.out.println("Thread " + Thread.currentThread().getId());
            
            //Calcualtes all prime number from 1 - 10,000
            //this does every ten prime numbers
            primeCalc(ten);
            ten+=10;
        }
    }

    public static void main(String[] args) throws Exception {

        //1 thread for all 10,000 numbers
        PrimeNumbers p1 = new PrimeNumbers();
        PrimeNumbers.one_thread t1 = p1.new one_thread();
        
        //one thread run
        //t1.run();
        System.out.println("End of 1 thread");

        //***** 100 threads**
        System.out.println("Beginning of 100 threads");
        for(int x = 0; x < 100; x++)
        {
            PrimeNumbers.hundred_threads t2 = p1.new hundred_threads();
            //t2.start();

        }

        System.out.println("End of 100 threads");

        
        //*** 1,000 threads *
        System.out.println("Beginning of 1,000 threads");
        for(int x = 0; x < 1000; x++)
        {
            
            PrimeNumbers.thousand_threads t3 = p1.new thousand_threads();
            t3.start();

        }
        System.out.println("End of 1,000 threads");


    }

}

