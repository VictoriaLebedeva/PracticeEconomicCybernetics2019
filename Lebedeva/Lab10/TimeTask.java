import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TimeTask extends Remote {
    public int register(String name, String password) throws RemoteException;
    public void unregister(String name, String password) throws RemoteException;
    public void addTime(int id) throws RemoteException;
    public void stopServer(String name, String password) throws RemoteException;
}