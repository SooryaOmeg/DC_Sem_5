import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TablePrinter {
    private List<String> headers;
    private List<List<String>> rows;
    private List<Integer> columnWidths;

    public TablePrinter(List<String> headers) {
        this.headers = headers;
        this.rows = new ArrayList<>();
        this.columnWidths = new ArrayList<>(headers.size());

        // Initialize column widths with header lengths
        for (String header : headers) {
            columnWidths.add(header.length());
        }
    }

    public void addRow(List<String> row) {
        rows.add(row);
        // Update column widths if necessary
        for (int i = 0; i < row.size(); i++) {
            if (i < columnWidths.size()) {
                columnWidths.set(i, Math.max(columnWidths.get(i), row.get(i).length()));
            } else {
                columnWidths.add(row.get(i).length());
            }
        }
    }

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    private void printLine() {
        for (int width : columnWidths) {
            System.out.print("+-" + "-".repeat(width) + "-");
        }
        System.out.println("+");
    }

    public void print() {
        printLine();

        // Print headers
        for (int i = 0; i < headers.size(); i++) {
            System.out.print("| " + padRight(headers.get(i), columnWidths.get(i)) + " ");
        }
        System.out.println("|");

        printLine();

        // Print rows
        for (List<String> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                System.out.print("| " + padRight(row.get(i), columnWidths.get(i)) + " ");
            }
            System.out.println("|");
        }

        printLine();
    }

//    public static void main(String[] args) {
//        // Example usage
//        TablePrinter table = new TablePrinter(Arrays.asList("ID", "Name", "Age", "City"));
//        table.addRow(Arrays.asList("1", "John Doe", "30", "New York"));
//        table.addRow(Arrays.asList("2", "Jane Smith", "25", "Los Angeles"));
//        table.addRow(Arrays.asList("3", "Bob Johnson", "35", "Chicago"));
//        table.print();
//    }
}