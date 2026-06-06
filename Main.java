import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static Connection getConnection() {

        try {

            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librarydb",
                    "root",
                    "rootinstaller@0"   // change to your mysql password
            );

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        new LibraryGUI();
    }
}