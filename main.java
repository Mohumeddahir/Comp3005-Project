import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class main {
    public boolean owner; // Flag that signals if owner is logged in
    public String[] owners; // Array of owners names. To log in as an owner, use their name as the username

    // Runs look inna book instance
    public static void main(String []args){
        main db_conn = new main();
    }

    // Constructor that logs user/owner into the store. It also retrieves the owner names from the db,
    // to check against whoever logs in.
    // NOTE: YOU can enter anything for user and password right now
    public main() {
        try (
                Connection con = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:3000/postgres","postgres","khalid");
                Statement s = con.createStatement();
        ) {
            System.out.println("Welcome to our Look Inna Store");
            Scanner sc1 = new Scanner(System.in);
            Scanner sc2 = new Scanner(System.in);
            owner = false;
            owners = new String[2];

            System.out.print("Enter the username: ");
            String username = sc1.nextLine(); //reads string
            System.out.print("Enter the password: ");
            String password = sc2.nextLine();
            System.out.print("The username is : " + username);
            System.out.print("\nThe password is : " + password);

            // Gets the owners from the db
            String query = "select name from OWNER";
            try (Statement stmt = con.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("\n\nOwners:");
                int i = 0;
                while (rs.next()) {
                    String name = rs.getString("name");
                    owners[i] = name;
                    System.out.println(name);
                    i++;
                }
                if (username.equals(owners[0]) || username.equals(owners[1])) {
                    System.out.println("An owner is logged on");
                    owner = true;
                }
                store_menu(con);

            } catch (SQLException e) {
                System.out.println("There was an error: \n" + e);
            }
            // Open the store menu for the user to choose what to do
            store_menu(con);
        }catch(Exception e){e.printStackTrace();}
    }

    // Opens menu
    private void store_menu(Connection con) throws SQLException {
        System.out.println("\n Choose one of the following options");
        System.out.println("1. Browsing our BookStore ");
        System.out.println("2. Check out a book");
        System.out.println("3. Tracking an order ");
        System.out.println("4. Browse list of Publishers");
        System.out.println("5. Add a book to the bookstore");
        System.out.println("6. View Reports");
        System.out.println("7. Exit the program\n");

        Scanner sc = new Scanner(System.in);
        int num = Integer.parseInt(sc.nextLine());

        switch (num) {
            case 1:
                System.out.println("1. Browsing our BookStore ");
                browseBookstore(con);
                break;
            case 2:
                System.out.println("2. Check out ");
                checkout(con);
                break;
            case 3:
                System.out.println("3. Tracking an order ");
                track_order(con);
                break;
            case 4:
                if (owner) {
                    System.out.println("4. Publishers");
                    view_publishers(con);
                } else {
                    System.out.println(" You're not authorized to view publishers");
                    store_menu(con);
                }
                break;
            case 5:
                if (owner) {
                    System.out.println("5. Add a book to the bookstore \n");
                    add_book(con);
                } else {
                    System.out.println(" You are not authorized to add books");
                    store_menu(con);
                }
                break;
            case 6:
                if (owner) {
                    System.out.println("6. View Reports \n");
                    view_reports(con);
                } else {
                    System.out.println(" You are not authorized to view Reports");
                    store_menu(con);
                }
                break;
            case 7:
                System.exit(0);
        }
    }

    // 1. View the bookstore catalogue
    // Fully implemented
    private void browseBookstore(Connection con) throws SQLException {
        String query = "select * from BOOK";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("# Title        Author   Price   # Pages  # in Stock");
            int count = 1;
            while (rs.next()) {
                //String title = rs.getString("title");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int num_of_pages = rs.getInt("number_of_pages");
                float price = rs.getInt("price");
                int num_books = rs.getInt("num_books");
                System.out.println(count +". " + title + ", " + author + ", $" + price +  ", " + num_of_pages + ", " + num_books);
                count++;
            }
            store_menu(con);
        } catch (SQLException e) {
            System.out.println("There was an error: \n" + e);
        }
    }

    // 2. Checks out the user
    // TODO
    private void checkout(Connection con) throws SQLException {
        System.out.println("Welcome to the checkout");
        System.out.println("Unfortunately this is not yet implemented. Come again soon!\n");
        System.out.print("Enter the title of the book that you want to checkout: ");
        Scanner sc1 = new Scanner(System.in);
        String title = sc1.nextLine(); //reads string

        String query = "select book_id from BOOK where title in ('"+title+"')";//we search a book by title
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            String book_id = "";
            while(rs.next()) {
                book_id = rs.getString("book_id");
                System.out.println("the book_id is " + book_id);
            }
            //String query2 = "insert into orders values('07', '1', 'Ottawa' '222 hal dr', '2220 hal dr')";
            //insert into orders values ('01','5', 'Warehouse', '101 Rev Ave', '101 Rev Ave');
            System.out.println("Where is the location of the order?");
            Scanner scanner = new Scanner(System.in);
            String location = scanner.nextLine();
            System.out.println("What is the delivery date (12th Dec 2021) ?");
            Scanner sc2 = new Scanner(System.in);
            String date = sc2.nextLine();
            System.out.println("What is the address?");
            Scanner sc3 = new Scanner(System.in);
            String address = sc3.nextLine();
            Statement stmt2 = con.createStatement();
            int order_id = 0;
            try {
                Random rand = new Random(); //instance of random class
                int upperbound = 1000;
                //generate random values from 0-24
                order_id = rand.nextInt(upperbound);


                stmt2.executeUpdate("insert into orders values('"+order_id+"', '1', '"+location+"', '"+date+"', '"+address+"')");
                // String query = "insert INTO BOOK VALUES ('" + book_id + "','" + pub_id + "','" + author + "','"
                //                + title + "'," + num_pages + "," + price + "," + num_books + ")";
            }catch(SQLException sqle) {
                System.out.println("Could not insert tuple into orders " + sqle);
                store_menu(con);
            }
                Statement stmt3 = con.createStatement();
                try {
                    stmt3.executeUpdate("insert into order_book values('"+ order_id +"', '"+book_id+"')");
                }catch(SQLException sqle) {
                    System.out.println("Could not insert tuple into order_book " + sqle);
                    store_menu(con);
                }
            System.out.println("Successfully inserted it to the orders and order_book table ");
            store_menu(con);
        } catch (SQLException e) {
            System.out.println("There was an error in finding the book title: \n" + e);
            store_menu(con);
        }

    }

    // 3. View orders made by each user
    // Fully implemented
    private void track_order(Connection con) throws SQLException {
        System.out.println("Track an order:");

        String query = "select * from track_order";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Order ID  User ID  Tracking Num");
            while (rs.next()) {
                String order_id = rs.getString("order_id");
                String user_id = rs.getString("user_id");
                int track_num = rs.getInt("tracking_number");
                System.out.println(order_id + " " + user_id + " " + track_num);
            }
            store_menu(con);
        } catch (SQLException e) {
            System.out.println("There was an error: \n" + e);
            store_menu(con);
        }
    }

    // 4. Shows a list of publishers (owners only)
    // Fully implemented
    private void view_publishers(Connection con) throws SQLException {
        System.out.println("The following is a list of all publishers:");

        String query = "select * from PUBLISHER";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Name      Address   Phone Number     Email     Bank Acc");
            while (rs.next()) {
                String pub_id = rs.getString("publisher_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone_number = rs.getString("phone_number");     // Will return error if it is set to int and i have NO IDEA why
                String email = rs.getString("email");
                int banking_acc = rs.getInt("banking_acc");
                System.out.println(pub_id + " " + name + " " + address + " " + phone_number +  " " + email + " " + banking_acc);
            }
            store_menu(con);
        } catch (SQLException e) {
            System.out.println("There was an error: \n" + e);
            store_menu(con);
        }
    }

    // 5. Adds a book to the book table (owners only)
    // Fully implemented
    private void add_book(Connection con) {
        System.out.println("So you want to add a book, let's get the book details.");
        System.out.println("First, what is the Title of the book? ");
        Scanner sc1 = new Scanner(System.in);
        String title = sc1.nextLine();
        System.out.println("Who is the Author of the book?");
        Scanner sc2 = new Scanner(System.in);
        String author = sc2.nextLine();
        System.out.println("How many pages does the book have?");
        Scanner sc3 = new Scanner(System.in);
        int num_pages = Integer.parseInt(sc3.nextLine());
        System.out.println("What is the price of one copy?");
        Scanner sc4 = new Scanner(System.in);
        float price = Float.parseFloat(sc4.nextLine());
        System.out.println("How many books are you adding to the stock?");
        Scanner sc5 = new Scanner(System.in);
        int num_books = Integer.parseInt(sc5.nextLine());
        System.out.println("What is the id of the publisher? ");
        Scanner sc6 = new Scanner(System.in);
        String pub_id = sc6.nextLine();
        System.out.println("Give the book an id (ie, 'book15')");
        Scanner sc7 = new Scanner(System.in);
        String book_id = sc7.nextLine();

        System.out.println("Summary of book to be added: ");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Number of pages: " + num_pages);
        System.out.println("Price of one copy: $" + price);
        System.out.println("Quantity in stock: " + num_books);
        System.out.println("Publisher ID: " + pub_id);
        System.out.println("Book ID: " + book_id);

        String query = "insert INTO BOOK VALUES ('" + book_id + "','" + pub_id + "','" + author + "','"
                + title + "'," + num_pages + "," + price + "," + num_books + ")";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(query);
            System.out.println("Successful insert");
            store_menu(con);
        } catch (SQLException e) {
            System.out.println("There was an error: \n" + e);
            add_book(con);  // Calls this function again if user inputs wrong value types
        }
    }

    // 6. View Reports (owners only)
    // Fully implemented
    public void view_reports(Connection con) throws SQLException {
        System.out.println("Viewing reports:");

        String query = "select * from REPORT";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Book ID Owner ID Sales Expenditure Genre Author");
            while (rs.next()) {
                String book_id = rs.getString("book_id");
                String owner_id = rs.getString("owner_id");
                int sales = rs.getInt("sales");
                int expenditure = rs.getInt("expenditure");
                String genre = rs.getString("genre");
                String author = rs.getString("author");
                System.out.println(book_id + " " + owner_id + " $" + sales + " $" + expenditure +  " " + genre + " " + author);
            }
            store_menu(con);
        } catch (SQLException e) {
            System.out.println("There was an error: \n" + e);
            store_menu(con);
        }
    }
}
