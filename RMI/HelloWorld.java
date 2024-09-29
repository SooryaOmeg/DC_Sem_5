import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloWorld extends Remote {
    String sayHello(String message) throws RemoteException;
    abstract String getScore(String message) throws RemoteException;
}


