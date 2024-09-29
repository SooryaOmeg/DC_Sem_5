import java.rmi.Naming;
import java.util.*;

public class HelloWorldClient {
    public static void main(String[] args) {
        try {
            HelloWorld helloWorld = (HelloWorld) Naming.lookup("rmi://localhost:2000/HelloWorld");
            Scanner s = new Scanner(System.in);
            String inp;
            while(true) {
                System.out.println("Scorecard");
                System.out.print("Enter your name: ");
                inp = s.nextLine();

                String response = helloWorld.sayHello(inp);
                System.out.println("Server response:\n");

                Vector<String> head = new Vector<>();
                head.addElement("id");
                head.addElement("question");
                head.addElement("answer");
                head.addElement("response");

                TablePrinter table = new TablePrinter(head);
                StringBuilder string = new StringBuilder();
                StringBuilder values = new StringBuilder();

                String score = helloWorld.getScore(inp);
                Vector<String> sc = new Vector<>();
                sc.addElement("Score");
                TablePrinter score_table = new TablePrinter(sc);
                score_table.addRow(Collections.singletonList(score));

                String[] rows = new String[4];
                int count = 0;

                for (int i = 0; i < response.length(); i++) {
                    if (response.charAt(i) != '\n') {
                        string.append(response.charAt(i));
                    } else {
                        for (int j = 0; j < string.length(); j++) {
                            if (string.charAt(j) != '\t') {
                                values.append(string.charAt(j));
                            } else {
                                rows[count] = values.toString();
                                count++;
                                values = new StringBuilder();
                            }
                            if (j == string.length() - 1) {
                                rows[count] = values.toString();
                                count = 0;
                                values = new StringBuilder();
                            }
                        }
                        try {
                            List<String> vals = new Vector<>(Arrays.asList(rows));
                            table.addRow(vals);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        string = new StringBuilder();
                    }
                }
                table.print();
                score_table.print();
                
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            throw new RuntimeException(e);
        }
    }
}
