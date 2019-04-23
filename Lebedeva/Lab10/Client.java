import java.rmi.Naming;

public class Client {
    public static void main(String[] args) throws Exception {
        String url = System.getProperty("time","rmi://firstremote");
        TimeTask timeTask = (TimeTask) Naming.lookup(url);
        int ID = timeTask.register("Vika","Lebedeva");
        for(int i = 0;i < 5;i++){
            timeTask.addTime(ID);
        }
        timeTask.unregister("Vika Lebedeva","Lebedeva");
    }
}