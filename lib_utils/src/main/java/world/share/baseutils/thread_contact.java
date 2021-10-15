package world.share.baseutils;

public class thread_contact {

    static {
        System.loadLibrary("native-lib");
    }

    public native static void execute(Runnable runnable);

    public void start(){

    }

    public void run(){

    }

    public void end(){

    }

}
