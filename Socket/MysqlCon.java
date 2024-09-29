import java.sql.*;
import java.util.*;
import java.io.*;
class MysqlCon{
    public void disp_ques(String name, DataOutputStream dos, DataInputStream dis){
        try{
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/soorya?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root","");
            Statement stmt=con.createStatement();
            Statement altstmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from question");
            System.out.println(rs);
            while(rs.next()) {
                String ques = rs.getString("ques");
                int id = rs.getInt("id");
                dos.writeUTF("ID: " + id + ".  " + ques);
                String reply = dis.readUTF();
                try{
                    String pqr = "alter table question add column " +  name + " varchar(10)";
                    System.out.println(pqr);
                    altstmt.executeUpdate(pqr);
                } catch (java.lang.Exception e){
                }
                String ans = "update question set " +  name + " = " + '"'+reply +'"' +" where id=" + id;
                System.out.println(ans);
                altstmt.executeUpdate(ans);
                System.out.println("indaddjs");
            }
            System.out.println("abcd");
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

}