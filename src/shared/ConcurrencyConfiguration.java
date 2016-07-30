package shared;

import java.util.concurrent.ForkJoinPool;
//import java.util.concurrent.ThreadPoolExecutor;

/**
 * Global Concurrency Class
 * @author Robert Smith
 * @version 1.0.0
 */
public class ConcurrencyConfiguration {

    private static boolean THREADING_ENABLED = true;

    private static ForkJoinPool fjp = null;

    //private static ThreadPoolExecutor tpe = null;

    public void setThreading(boolean te){
        if (THREADING_ENABLED && !te){
            if (fjp != null && fjp.getParallelism() != 1) {
                fjp.shutdown();
                fjp = new ForkJoinPool(1);
            }
            //if (tpe != null) {
            //    tpe.shutdown();
            //}
        }

        THREADING_ENABLED = te;
    }

    public static ForkJoinPool getFJP(){
        if (fjp == null) {
            if (THREADING_ENABLED) {
                fjp = new ForkJoinPool();
            } else {
                fjp = new ForkJoinPool(1);
            }
        }
        return fjp;
    }

    //public static ThreadPoolExecutor getTPE() {
    //    return tpe;
    //}

    public static void setFJP(ForkJoinPool forkJoinPool){
        if (fjp != null && fjp != forkJoinPool){
            fjp.shutdown();
        }
        fjp = forkJoinPool;
    }

    //private static void setTPE(ThreadPoolExecutor threadPoolExecutor){
    //    if (tpe != null && tpe != threadPoolExecutor){
    //        tpe.shutdown();
    //    }
    //    tpe = threadPoolExecutor;
    //}

}
