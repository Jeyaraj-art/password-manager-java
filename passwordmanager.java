import java.util.*;
import java.sql.*;

// Account class
class Account {
    String website;
    String username;
    String password;

    Account(String website, String username, String password) {
        this.website = website;
        this.username = username;
        this.password = password;
    }
}

public class passwordmanager {

    static Scanner sc = new Scanner(System.in);
    static Connection con;

    // DATABASE CONNECTION
    static void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/password_manager",
                    "root",
                    "your_password"
            );

            System.out.println("Database Connected Successfully!\n");

        } catch (Exception e) {
            System.out.println("Connection Error: " + e.getMessage());
        }
    }

    // LOGIN
    static boolean login() {
        System.out.print("Enter Master PIN: ");
        String pin = sc.nextLine();

        if (pin.equals("1234")) {
            System.out.println("Login Successful!\n");
            return true;
        } else {
            System.out.println("Wrong PIN!");
            return false;
        }
    }

    // ADD ACCOUNT
    static void addAccount() {
        try {
            System.out.print("Website: ");
            String website = sc.nextLine();

            System.out.print("Username: ");
            String username = sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine();

            String query = "INSERT INTO accounts(website, username, password) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, website);
            ps.setString(2, username);
            ps.setString(3, password);

            ps.executeUpdate();

            System.out.println("Account Saved Successfully!\n");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // VIEW ACCOUNTS
    static void viewAccounts() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM accounts");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Website: " + rs.getString("website"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Password: " + rs.getString("password"));
                System.out.println("----------------------------");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        connectDB();

        if (!login())
            return;

        while (true) {
            System.out.println("1. Add Account");
            System.out.println("2. View Accounts");
            System.out.println("3. Exit");

            System.out.print("Choose option: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    addAccount();
                    break;
                case 2:
                    viewAccounts();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
