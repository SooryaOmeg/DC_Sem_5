import java.io.*;
import java.net.*;
import java.util.Scanner;
public class client_mul1
{
    public static void main(String[] args) throws IOException
    {
        try
        {
            Scanner scn = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("localhost");
            Socket s = new Socket(ip, 6666);
            DataInputStream dis = new
                    DataInputStream(s.getInputStream());
            DataOutputStream dos = new
                    DataOutputStream(s.getOutputStream());
            System.out.print("Enter your name: ");
            String name = scn.nextLine();
            dos.writeUTF(name);
            System.out.println(dis.readUTF());
            while (true)
            {
                System.out.println("TEST123");
                System.out.println(dis.readUTF());
                String tosend = scn.nextLine();
                dos.writeUTF(tosend);
                if(tosend.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + s);
                            s.close();
                    System.out.println("Connection closed");
                    break;
                }
            }
            scn.close();
            dis.close();
            dos.close();
        }catch(Exception e){
            System.out.println("Exited");
            System.exit(0);
        }
    }
}