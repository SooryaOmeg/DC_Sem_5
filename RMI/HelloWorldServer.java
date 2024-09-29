import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class HelloWorldServer {
    public static void main(String[] args) {
        try {
            // Create the registry on a different port (e.g., 2000)
            LocateRegistry.createRegistry(2000);

            // Instantiate the implementation
            HelloWorldImpl helloWorld = new HelloWorldImpl();

            // Bind the remote object to a name in the registry
            Naming.rebind("rmi://localhost:2000/HelloWorld", helloWorld);

            System.out.println("HelloWorld server is running on port 2000...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
