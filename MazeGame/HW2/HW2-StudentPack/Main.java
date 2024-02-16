import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main
{
    static class Producer implements Runnable
    {

        // TODO: You may want to implement this class to test your code
        private PrinterRoom room;
        private List<PrintItem> jobs;
        private int id;
        public Producer(int id, PrinterRoom room, List<PrintItem> jobs){
            this.room = room;
            this.jobs = jobs;
            this.id = id;
        }
        public void run()
        {
            // TODO: Provide a thread join functionality for the main thread
            SyncLogger.Instance().Log(SyncLogger.ThreadType.PRODUCER, this.id,
                    String.format(SyncLogger.FORMAT_PRODUCER_LAUNCH, this.id));
            try{
                for (int i = 0; i < this.jobs.size(); i++)
                {
                    //Thread.sleep(new Random().ints(0,3).findFirst().getAsInt() * 1000);
                    // Printing and display the elements in ArrayList

                    if(!room.SubmitPrint(this.jobs.get(i), this.id))
                    {
                        SyncLogger.Instance().Log(SyncLogger.ThreadType.PRODUCER, this.id,
                                String.format(SyncLogger.FORMAT_ROOM_CLOSED, this.jobs.get(i)));
                    }
                }
            }
            finally {
                System.out.println("Job " + id + " is exiting...");
            }
        }
    }

    public static void main(String args[]) throws InterruptedException
    {
        PrinterRoom room = new PrinterRoom(2, 30);

        ArrayList<PrintItem> arr1 = new ArrayList<>();

        for(int i = 0;i < 25;i++)
        {
            Random rd = new Random();
            Random r = new Random();
            if(rd.nextBoolean())
            {
                arr1.add(new PrintItem(r.ints(100,102).findFirst().getAsInt(), PrintItem.PrintType.INSTRUCTOR,i));
            }
            else
            {
                arr1.add(new PrintItem(r.ints(100,102).findFirst().getAsInt(), PrintItem.PrintType.STUDENT,i));
            }
        }

        ArrayList<PrintItem> arr2 = new ArrayList<>();
        for(int i = 25;i < 50;i++)
        {
            Random rd = new Random();
            Random r = new Random();
            if(rd.nextBoolean())
            {


                arr2.add(new PrintItem(r.ints(100,102).findFirst().getAsInt(), PrintItem.PrintType.INSTRUCTOR,i));
            }
            else
            {
                arr2.add(new PrintItem(r.ints(100,400).findFirst().getAsInt(), PrintItem.PrintType.STUDENT,i));
            }
        }

        ArrayList<PrintItem> arr3 = new ArrayList<>();
        for(int i = 50;i < 100;i++)
        {
            Random rd = new Random();
            Random r = new Random();
            if(rd.nextBoolean())
            {
                arr3.add(new PrintItem(r.ints(100,400).findFirst().getAsInt(), PrintItem.PrintType.INSTRUCTOR,i));
            }
            else
            {
                arr3.add(new PrintItem(r.ints(250,700).findFirst().getAsInt(), PrintItem.PrintType.STUDENT,i));
            }
        }

        Producer p1 = new Producer(0, room, arr1);
        Producer p2 = new Producer(1, room, arr2);
        Producer p3 = new Producer(2, room, arr3);

        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);
        Thread t3 = new Thread(p3);

        t1.start();
        t2.start();
        t3.start();

        // Wait a little we are doing produce on the same thread that will do the close
        // actual tests won't do this.
        Thread.sleep((long)(5 * 1000));
        // Log before close
        SyncLogger.Instance().Log(SyncLogger.ThreadType.MAIN_THREAD, 0,
                "Closing Room");
        room.CloseRoom();
        // This should print only after all elements are closed (here we wait 3 seconds so it should be immediate)


        t1.join();
        t2.join();
        t3.join();
        SyncLogger.Instance().Log(SyncLogger.ThreadType.MAIN_THREAD, 0,
                "Room is Closed");
    }
}