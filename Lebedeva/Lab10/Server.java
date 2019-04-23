import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server extends UnicastRemoteObject implements TimeTask {
    boolean runs = true;
    protected String adminName = "admin";
    protected String adminPassword = "admin";

    class Time{
        String password;
        int time;
        Time(String password){
            this.password=password;
            time=0;
        }
        Time(String password, int time){
            this.password=password;
            this.time=time;
        }
    }

    Map<String,Time> times = new HashMap<>();
    protected Integer nowID = 0;
    protected ArrayList <Integer> nowtimes = new ArrayList<>();
    protected ArrayList <String> connectedNames = new ArrayList<>();
    protected ArrayList <String> connectedPasswords = new ArrayList<>();

    public Server() throws RemoteException{
        super();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
            int number = Integer.parseInt(reader.readLine());
            for(int i=0;i < number; i++){
                String name = reader.readLine();
                String password = reader.readLine();
                int time = Integer.parseInt(reader.readLine());
                Time tmp = new Time(password,time);
                times.put(name,tmp);
            }
            reader.close();

        }
        catch (Exception e){

        }
    }

    @Override
    public int register(String name, String password) throws RemoteException {
        int newID = -1;
        synchronized (connectedNames) {
            if (connectedNames.contains(name))
                throw new RemoteException("Arleady connected");
            synchronized (nowID) {
                newID = nowID++;
            }
            connectedNames.add(name);
            connectedPasswords.add(password);
        }
        synchronized (times){
            if(!times.containsKey(name)){
                Time tmp = new Time(password);
                times.put(name,tmp);
            }
        }
        return newID;
    }

    @Override
    public void unregister(String name, String password) throws RemoteException {
        synchronized (connectedNames){
            int idx = connectedNames.indexOf(name);
            if(connectedPasswords.get(idx).equals(password)){
                synchronized (times){
                    Time thisTime = times.get(name);
                    thisTime.time += nowtimes.get(idx);
                }
                connectedNames.set(idx, "");
                connectedPasswords.set(idx, "");
            }
            else
                throw new RemoteException("You are not this person");
        }
    }

    @Override
    public void stopServer(String name, String password) throws RemoteException {
        synchronized (adminName){
            if(adminName.equals(name) && adminPassword.equals(password)){
                synchronized (connectedNames){
                    synchronized (times){
                        for(int i = 0; i < nowID; i++){
                            String nameConnected = connectedNames.get(i);
                            Time timeConnected = times.get(nameConnected);
                            timeConnected.time+=nowtimes.get(i);
                        }
                        try{
                            BufferedWriter out = new BufferedWriter(new FileWriter("data.txt"));
                            out.write(times.size()+"\n");
                            for (Map.Entry<String, Time> entry : times.entrySet()) {
                                out.write(entry.getKey()+"\n");
                                out.write(entry.getValue().password+"\n");
                                out.write(entry.getValue().time+"\n");
                            }
                            out.close();
                        }
                        catch (Exception e){

                        }
                    }
                }

            }
        }
        runs = false;

    }

    @Override
    public void addTime(int id) throws RemoteException {
        synchronized (nowtimes){
            nowtimes.set(id, nowtimes.get(id) + 5);
        }
    }

    public static void main(String[] args) throws Exception{
        Server server = new Server();
        System.setProperty("work","127.0.0.2");
        String name = System.getProperty("work", "firstremote");
        Naming.rebind(name,server);
        while (server.runs){

        }
    }
}