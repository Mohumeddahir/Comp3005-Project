import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class main {
    public static void main(String []args){
        try (
                Connection con = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:3000/postgres","postgres","khalid");
                Statement s = con.createStatement();
        ) {
            System.out.println("Welcome to our Look Inna Store");
            Scanner sc1 = new Scanner(System.in);
            Scanner sc2 = new Scanner(System.in);
            boolean owner = false;
            //System.in is a standard input stream
            System.out.print("Enter the username: ");
            String username = sc1.nextLine(); //reads string
            System.out.print("Enter the password: ");
            String password = sc2.nextLine();
            System.out.print("The username is : " + username);
            System.out.print("\nThe password is : " + password);
            if (username == "khalid" || username == "Sean") {
                owner = true;
            }

            System.out.println("\n Choose one of the following options");
            System.out.println("1. Browsing our BookStore ");
            System.out.println("2. Check out ");
            System.out.println("3. Tracking an order ");
            System.out.println("4. Publishers");
            System.out.println("5. Add a book to the bookstore \n");

            Scanner sc3 = new Scanner(System.in);
            int num = Integer.parseInt(sc3.nextLine());
            switch (num) {
                case 1:
                    System.out.println("1. Browsing our BookStore ");
                    break;
                case 2:
                    System.out.println("2. Check out ");
                    break;
                case 3:
                    System.out.println("3. Tracking an order ");
                    break;
                case 4:
                    if (owner) {
                        System.out.println("4. Publishers");
                    } else {
                        System.out.println(" Your not authorized ");
                    }
                    break;
                case 5:
                    if (owner) {
                        System.out.println("5. Add a book to the bookstore \n");
                    } else {
                        System.out.println(" You cannot add Books ");
                    }

                    break;
            }
        }catch(Exception e){e.printStackTrace();

            }
        }
}


