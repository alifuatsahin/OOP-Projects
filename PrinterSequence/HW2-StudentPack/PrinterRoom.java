import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrinterRoom
{
    protected boolean closedRoom = false;
    protected ArrayList<Thread> threads = new ArrayList<>();
    private class Printer implements Runnable
    {
        // TODO: Implement
        // ....
        private IMPMCQueue<PrintItem> roomQueue;
        private int id;
        private PrintItem output;
        private final Thread printer;
        public Printer(int id, IMPMCQueue<PrintItem> roomQueue)
        {
            // TODO: Implement
            this.roomQueue = roomQueue;
            this.id = id;
            SyncLogger.Instance().Log(SyncLogger.ThreadType.CONSUMER, this.id,
                    String.format(SyncLogger.FORMAT_PRINTER_LAUNCH, this.id));
            printer = new Thread(this);
            threads.add(printer);
            printer.start();
        }

        @Override
        public void run() {
            while(true){
                try {
                    this.output = this.roomQueue.Consume();
                    if(this.output != null && this.output.print()){
                        SyncLogger.Instance().Log(SyncLogger.ThreadType.CONSUMER, this.id,
                            String.format(SyncLogger.FORMAT_PRINT_DONE, this.output));
                    }
                }
                catch(QueueIsClosedException e){
                    if(closedRoom) {
                        break;
                    }
                }
            }
            SyncLogger.Instance().Log(SyncLogger.ThreadType.CONSUMER, this.id,
                    String.format(SyncLogger.FORMAT_TERMINATING, this.output));
        }
    }

    private IMPMCQueue<PrintItem> roomQueue;
    private final List<Printer> printers;

    public PrinterRoom(int printerCount, int maxElementCount)
    {
        // Instantiating the shared queue
        roomQueue = new PrinterQueue(maxElementCount);

        // Let's try streams
        // Printer creation automatically launches its thread
        printers = Collections.unmodifiableList(IntStream.range(0, printerCount)
                                                         .mapToObj(i -> new Printer(i, roomQueue))
                                                         .collect(Collectors.toList()));
        // Printers are launched using the same queue
    }

    public boolean SubmitPrint(PrintItem item, int producerId)
    {
        // TODO: Implement
        try {
            SyncLogger.Instance().Log(SyncLogger.ThreadType.PRODUCER, producerId,
                        String.format(SyncLogger.FORMAT_ADD, item.toString()));
            this.roomQueue.Add(item);
        } catch (QueueIsClosedException e) {
            return false;
        }
        return true;
    }

    public void CloseRoom()
    {
        // TODO: Implement
        this.closedRoom = true;
        this.roomQueue.CloseQueue();

        for(Thread thread: threads){
            try {
                thread.join();
            }
            catch(InterruptedException e){
            }
        }
    }
}
