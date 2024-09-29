import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
public class Server
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket ss = new ServerSocket(6666);
        while (true)
        {
            Socket s = null;
            try
            {
                s = ss.accept();
                System.out.println("A new client is connected : " + s);
                DataInputStream dis = new
                        DataInputStream(s.getInputStream());
                DataOutputStream dos = new
                        DataOutputStream(s.getOutputStream());
                System.out.println("Assigning new thread for this client");
                        Thread t = new ClientHandler(s,dis, dos);
                t.start();
            }
            catch (Exception e){
                ss.close();
                e.printStackTrace();
            }
        }
    }
}
class ClientHandler extends Thread
{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    // Constructor
    public ClientHandler(Socket s, DataInputStream dis,
                         DataOutputStream dos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
    @Override
    public void run()
    {
        String received;
        String toreturn;
        String name = "abcd";
        MysqlCon ms = new MysqlCon();
        try{
            name = dis.readUTF();
            System.out.println("Client name: " + name);
            dos.writeUTF("Hello " + name + " . Please Enter a message"+". Type Exit to terminate connection.");
        }
        catch(Exception e){
            System.out.println(e);
        }
        while (true)
        {
            try {
                ms.disp_ques(name, dos, dis);
                received = dis.readUTF();
                if(received.equals("Exit"))
                {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }
                break;
            }
            catch (IOException e) {
                System.exit(0);
                e.printStackTrace();
            }
        }
        try
        {
            this.dis.close();
            this.dos.close();
        }catch(IOException e){
            System.exit(0);
            e.printStackTrace();
        }
    }
}