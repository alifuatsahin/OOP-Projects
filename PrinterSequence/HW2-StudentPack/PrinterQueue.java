import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterQueue implements IMPMCQueue<PrintItem>
{
    // TODO: This is all yours
    private final Lock lock = new ReentrantLock();
    //private final Condition Producible = lock.newCondition();
    //private final Condition Consumable = lock.newCondition();
    private BlockingQueue<PrintItem> roomQueue;
    private final int maxElementCount;
    private boolean closedQueue;
    public PrinterQueue(int maxElementCount)
    {
        // TODO: Implement
        this.maxElementCount = maxElementCount;
        this.roomQueue = new PriorityBlockingQueue<>(maxElementCount, new PriorityComparator());
        this.closedQueue = false;
        // You can change this signature but also don't forget to
        // change the instantiation signature on the
        // Printer room
    }

    static class PriorityComparator implements Comparator<PrintItem> {
        @Override
        public int compare(PrintItem o1, PrintItem o2) {
            if(o1.getPrintType() == o2.getPrintType()){
                return 0;
            }
            else if(o1.getPrintType() == PrintItem.PrintType.INSTRUCTOR){
                return -1;
            }
            else{
                return 1;
            }
        }
    }

    @Override
    public void Add(PrintItem data) throws QueueIsClosedException {
        this.lock.lock();
        try{
            while(RemainingSize() == 0) {
                if (this.closedQueue) {
                    throw new QueueIsClosedException();
                }
                //this.Producible.await();
            }
            if(this.closedQueue){
                throw new QueueIsClosedException();
            }
            this.roomQueue.add(data);
            //this.Consumable.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public synchronized PrintItem Consume() throws QueueIsClosedException {
        try {
            return this.roomQueue.remove();
        }
        catch(NoSuchElementException e){
            if(this.closedQueue) {
                throw new QueueIsClosedException();
            }
            else{
                return null;
            }
        }
    }

    @Override
    public int RemainingSize() {
        return (maxElementCount - roomQueue.size());
    }

    @Override
    public void CloseQueue() {
        this.closedQueue = true;
    }
}
