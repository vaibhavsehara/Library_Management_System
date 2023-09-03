package org.example;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class Main {
    private static Map<String, String> userAccounts = new HashMap<>();
    private static boolean isLibrarianMenuRunning = false;
    private static boolean isMemberMenuRunning = false;

    private static List<User> users = new ArrayList<>();
    private static List<Book> books = new ArrayList<>();
    private static List<Member> members = new ArrayList<>();
    private static Library library;
    public static void main(String[] args) {
        users.add(new User("admin", "1", "Librarian"));
        library = new Library(books, members);
        loginMenu();
    }

    public static void addNewMember(String memberName, String memberPhoneNo) {
        userAccounts.put(memberName, memberPhoneNo);
    }
    public static void removeMember(String memberPhoneNo) {
        userAccounts.remove(memberPhoneNo);

        // Find and remove the member from the Library's members list
        Member memberToRemove = null;
        for (Member member : members) {
            if (member.getMemberPhoneNo().equals(memberPhoneNo)) {
                memberToRemove = member;
                break;
            }
        }

        if (memberToRemove != null) {
            members.remove(memberToRemove);
        }
    }


    public static void librarianLogin(){
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8); // UTF-8 is a standard for encoding characters in order to support a variety of languages
        System.out.println("Enter Username : ");
        String username = scanner.nextLine();
        System.out.println("Enter ID : ");
        String ID = scanner.nextLine();
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(username) && user.getID().equals(ID) && user.getRole().equals("Librarian")) {
                System.out.println("Login Successful");
                librarianMenu(  books, members);
                return;
            }
        }
        System.out.println("Invalid Credentials");
    }

    public static boolean memberLoginSuccess(String memberName, String memberPhoneNo) {
        for (Member member : members) {
            if (member.getMemberName().equalsIgnoreCase(memberName) && member.getMemberPhoneNo().equals(memberPhoneNo)) {
                return true;
            }
        }
        return false;
    }

    public static void memberLogin(){
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8); // UTF-8 is a standard for encoding characters in order to support a variety of languages
        System.out.println("Enter Username : ");
        String username = scanner.nextLine();
        System.out.println("Enter your Number (ID) : ");
        String ID = scanner.nextLine();
        Member member = library.findMemberByNumber(ID);
        if (memberLoginSuccess(username, ID)) {
            System.out.println("Login Successful");
            memberMenu();
            return;
        }else {
            System.out.println("Invalid Credentials");
        }
    }

    public static void addBook(Scanner scanner){
        System.out.println("Enter the name of the book to add: ");
        String bookName = scanner.nextLine();
        System.out.println("Enter the author of the book to add: ");
        String author = scanner.nextLine();
        int totalBookCount = 0;
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print("Enter Total Book Count : ");
                totalBookCount = scanner.nextInt();
                flag = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input");
                scanner.nextLine();
            }
        }

        Book book = new Book(bookName, author, totalBookCount);
        books.add(book);

        System.out.println("Book added successfully!");
    }
    public static void removeBookByName(Scanner scanner) {
        System.out.println("Enter the name of the book to remove: ");
        String bookNameToRemove = scanner.nextLine();
        boolean removed = false;

        // Iterate through the list of books to find and remove all books by name
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getBookName().equalsIgnoreCase(bookNameToRemove)) {
                iterator.remove(); // Remove the book from the list
                removed = true;
            }
        }

        if (removed) {
            System.out.println("Books with the name '" + bookNameToRemove + "' removed successfully!");
        } else {
            System.out.println("No books with the name '" + bookNameToRemove + "' found.");
        }
    }
    private static void loginMenu() {
        boolean isLoggedIn = false;
        while(!isLoggedIn){
            Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8); // UTF-8 is a standard for encoding characters in order to support a variety of languages
            System.out.println(" -------------------------------------");
            System.out.println(" -------------------------------------");
            System.out.println(" Library Management System ");
            System.out.println("LOGIN");
            System.out.println("1. Librarian");
            System.out.println("2. Member");
            System.out.println("3. Exit");
            System.out.println(" -------------------------------------");
            System.out.println(" -------------------------------------");
            System.out.print("Enter your choice : ");

            int choice1;
            while(true){
                try{
                    choice1 = scanner.nextInt();
                    scanner.nextLine();
                    break;
                }catch (InputMismatchException e){
                    System.out.println("Invalid Input");
                    scanner.nextLine();
                }
            }
            switch (choice1) {
                case 1:
                    librarianLogin();
                    break;
                case 2:
                    memberLogin();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8); // UTF-8 is a standard for encoding characters in order to support a variety of languages
        System.out.println(" Library Management System ");
        System.out.println("LOGIN");
        System.out.println("1. Librarian");
        System.out.println("2. Member");
        System.out.println("3. Exit");
        System.out.print("Enter your choice : ");
        int choice1 = scanner.nextInt();
        scanner.nextLine();
        switch (choice1) {
            case 1:
                librarianLogin();
                break;
            case 2:
                memberLogin();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid Choice");
        }


    }
    public static void memberMenu() {
        isMemberMenuRunning = true;
        while (isMemberMenuRunning) {
            Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8); // UTF-8 is a standard for encoding characters in order to support a variety of languages
            System.out.println(" -------------------------------------");
            System.out.println(" -------------------------------------");
            System.out.println("1. List available Books ");
            System.out.println("2. List My Books ");
            System.out.println("3. Issue book ");
            System.out.println("4. Return book ");
            System.out.println("5. Show Fine ");
            System.out.println("6. Back to Login ");
            System.out.println(" -------------------------------------");
            System.out.println(" -------------------------------------");
            System.out.print("Enter your choice : ");

            int choice3;
            while (!false) {
                try {
                    choice3 = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input");
                    scanner.nextLine();
                }
            }
            switch (choice3) {
                case 1:
                    System.out.println("All Books");
                    for (Book book : books) {
                        System.out.println("Book Name : " + book.getBookName());
                        System.out.println("Author Name : " + book.getAuthor());
                        System.out.println("Total Book Count : " + book.getTotalBookCount());
                    }
                    break;
                case 2:
                    System.out.println("My Books");
                    for (Member member : members) {
                        System.out.println("Member Name : " + member.getMemberName());
                        System.out.println("Member Phone No : " + member.getMemberPhoneNo());
                        System.out.println("Total Books Checked Out:");
                        for (Book checkedOutBook : member.checkedOutBooks) {
                            System.out.println("Book Name: " + checkedOutBook.getBookName());
                        }

                    }
                    break;

                case 3:
                    System.out.println("These are the available books : ");
                    for (Book book : books) {
                        System.out.println("BookId : " + book.getId());
                        System.out.println("Book Name : " + book.getBookName());
                        System.out.println("Author Name : " + book.getAuthor());
                        System.out.println("Total Book Count : " + book.getTotalBookCount());
                    }
                    System.out.print("Enter Member Number (ID) : ");
                    String memberId = scanner.nextLine();
                    Member member = library.findMemberByNumber(memberId);
                    if (member != null) {
                        System.out.println("Enter Book ID : ");
                        int bookId = scanner.nextInt();
                        library.issueBook(member.getMemberPhoneNo(),bookId);
                    }else {
                        System.out.println("Member not found!");
                    }
                    break;

                case 4:
                    System.out.println("Enter Member Number (ID) : ");
                    String memberId1 = scanner.nextLine().trim();
                    Member member1 = library.findMemberByNumber(memberId1);

                    if (member1 != null) {
                        System.out.println("Enter Book ID : ");
                        int bookId = scanner.nextInt();
                        Book book = library.findBookById(bookId);

                        if (book != null) {
                            if (book.isReturned()) {
                                System.out.println("Book already returned!");
                            } else {
                                // Check if the book is owned by the member
                                if (member1.checkedOutBooks.contains(book)) {
                                    library.returnBook(member1.getMemberPhoneNo(), bookId);
                                    System.out.println("Book returned successfully!");
                                } else {
                                    System.out.println("You have not checked out this book!");
                                }
                            }
                        } else {
                            System.out.println("Book not found!");
                        }
                    } else {
                        System.out.println("Member not found!");
                    }
                    break;


                case 5:
                    System.out.println("Enter Member Number (ID) : ");
                    String memberId2 = scanner.nextLine().trim();
                    Member member2 = library.findMemberByNumber(memberId2);

                    if (member2 != null) {
                        System.out.println("Enter Book ID : ");
                        int bookId = scanner.nextInt();
                        Book book = library.findBookById(bookId);

                        if (book != null) {
                            if (book.isOwned()) {
                                if (member2.checkedOutBooks.contains(book)) { // Check if the book has been returned
                                    LocalDateTime currentDate = LocalDateTime.now();
                                    LocalDateTime dueDate = book.getDueDate();

                                    // Check if the book is past the due date
                                    if (currentDate.isAfter(dueDate)) {
                                        double fine = library.calculateFine(currentDate, dueDate, true);

                                        if (fine > 0) {
                                            System.out.println("You have a fine of Rs. " + fine);
                                            System.out.println("Do you want to pay the fine? (1 - Yes, 2 - No)");
                                            int choice = scanner.nextInt();

                                            if (choice == 1) {
                                                member2.PayFine(fine);
                                                book.setReturned(true);
                                                System.out.println("Fine paid successfully!");
                                            } else {
                                                System.out.println("Fine not paid.");
                                            }
                                        } else {
                                            System.out.println("You have no fine!");
                                        }
                                    } else {
                                        System.out.println("The book has not been returned yet or is not overdue!");
                                    }
                                } else {
                                    System.out.println("The book has already been returned!");
                                }
                            } else {
                                System.out.println("This book is not owned by a member!");
                            }
                        } else {
                            System.out.println("Book not found!");
                        }
                    } else {
                        System.out.println("Member not found!");
                    }
                    break;



                case 6:
                    System.out.println("Going back to Login...............");
                    isLibrarianMenuRunning = false;
                    loginMenu();
                    break;
                default:
                    System.out.println("Invalid Choice");
            } // member menu
        }
    }
    private static void librarianMenu(List<Book>books, List<Member> members) {
        isLibrarianMenuRunning = true;
        while (isLibrarianMenuRunning) {
            library = new Library(books, members);
            Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8); // UTF-8 is a standard for encoding characters in order to support a variety of languages
            System.out.println(" -------------------------------------");
            System.out.println(" -------------------------------------");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Remove Book");
            System.out.println("4. Remove Member");
            System.out.println("5. View all members along with their books and fines to be paid");
            System.out.println("6. View all books");
            System.out.println("7. Back to Login");
            System.out.println(" -------------------------------------");
            System.out.println(" -------------------------------------");
            System.out.print("Enter your choice : ");

            int choice2;
            while (!false) {
                try {
                    choice2 = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input");
                    scanner.nextLine();
                }
            }

            switch (choice2) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    System.out.print("Enter Member Name : ");
                    String memberName = scanner.nextLine();
                    System.out.print("Enter Member Number (ID) : ");
                    String memberNumber = scanner.nextLine();
                    Member member = new Member(memberName, memberNumber);
                    library.addMember(member);
                    addNewMember(memberName, memberNumber);
                    System.out.println("Member added successfully!");
                    break;
                case 3:
                    removeBookByName(scanner);
                    break;
                case 4:
                    System.out.print("Enter Member Id : ");
                    String memberId = scanner.nextLine();
                    removeMember(memberId);

                    break;
                case 5:
                    System.out.println("All Members and Their Books with Fines:");
                    for (Member member1 : members) {
                        System.out.println("Member Name: " + member1.getMemberName());
                        System.out.println("Member Phone No: " + member1.getMemberPhoneNo());
                        System.out.println("Total Books Checked Out:");
                        if (member1.checkedOutBooks.isEmpty()) {
                            System.out.println("No books checked out.");
                        } else {
                            for (Book checkedOutBook : member1.checkedOutBooks) {
                                System.out.println("  - Book Name: " + checkedOutBook.getBookName());
                            }
                        }
                        System.out.println("Penalty Amount: Rs. " + member1.getPenalty());
                        System.out.println();
                    }
                    break;



                case 6:
                    System.out.println("All Books");
                    for (Book book : books) {
                        System.out.println("Book Name : " + book.getBookName());
                        System.out.println("Author Name : " + book.getAuthor());
                        System.out.println("Total Book Count : " + book.getTotalBookCount());
                    }
                    break;
                case 7:
                    System.out.println("Going back to Login...............");
                    isLibrarianMenuRunning = false;
                    loginMenu();
                    break;
                default:
                    System.out.println("Invalid Choice");
            } // librarian menu


        }
    }

}
class User {
    private String name;
    private String ID;
    private String role;

    public User(String name, String ID, String role) {
        this.name = name;
        this.ID = ID;
        this.role = role;
    }
    public String getName() {

        return name;
    }
    public String getID() {

        return ID;
    }
    public String getRole() {

        return role;
    }
}

class Book {

    private LocalDate issueDate;
    public LocalDateTime dueDate;

    private boolean returned;
    private static  Random random = new Random();
    private static int lastBookId = 0;
    private int id;
    private boolean isOwned;
    private boolean isReturned;
    private  String BookName;
    private  String Author;
    private  int totalBookCount;

    public Book( String bookName, String author, int totalBookCount) {
        this.id = ++lastBookId;
        this.BookName = bookName;
        this.Author = author;
        this.totalBookCount = totalBookCount;
        this.returned = false;
    }
    public boolean isReturned() {

        return returned;
    }
    public void setReturned(boolean returned) {
        isReturned = returned;
    }
    public boolean isOwned() {
        return isOwned;
    }
    public void setOwned(boolean owned) {

        isOwned = owned;
    }
    public int getId() {

        return id;
    }

    public void setDueDate(LocalDateTime dueDate) {

        this.dueDate = dueDate;
    }
    public LocalDateTime getDueDate() {

        return dueDate;
    }
    public String getBookName() {

        return BookName;
    }


    public String getAuthor() {

        return Author;
    }

    public int getTotalBookCount() {

        return totalBookCount;
    }
    public void setTotalBookCount(int totalBookCount) {

        this.totalBookCount = totalBookCount;
    }

}

class Member {

    List<Book> checkedOutBooks = new ArrayList<>();
    private static  Random random = new Random();
    private static int lastMemberId = 0;
    private  int memberId;
    private String memberName;

    private  String memberPhoneNo;
    private  int totalBooksCheckedOut;
    private double penaltyAmount;

    public Member(String memberName, String memberPhoneNo) {
        this.memberId = ++lastMemberId; // Assign a new member ID
        this.memberName = memberName;
        this.memberPhoneNo = memberPhoneNo;
        this.totalBooksCheckedOut = 0; // Initialize totalBooksCheckedOut to 0
        this.penaltyAmount = 0.0;
    }



    public String getMemberName() {
        return memberName;
    }



    public String getMemberPhoneNo() {
        return memberPhoneNo;
    }



    public double getPenalty() {
        return penaltyAmount;
    }
    public void setPenaltyAmount(double penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }
    public void addPenalty(double amount) {
        this.penaltyAmount += amount;
    }
    public void PayFine(double amount) {
        if (amount <= 0.0) {
            System.out.println("Invalid amount!");
        } else {
            penaltyAmount -= amount;
            System.out.println("Fine paid successfully!");
        }
    }

}

class Library {
    private static List<Book> books;
    private List<Member> members;

    public Library(List<Book> books, List<Member> members) {
        this.books = books;
        this.members = members;
    }
    public Book findBookById(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null; // Book not found with the given ID
    }

    public void issueBook(String memberNumber, int bookId) {
        Member member = findMemberByNumber(memberNumber);
        if (member != null) {
            Book book = findBookById(bookId);
            if (book != null) {
                if (!book.isReturned()) { // Check if the book is already returned
                    if (book.getTotalBookCount() != 0) {
                        if (member.getPenalty() == 0) {
                            if (member.checkedOutBooks.size() < 5) {
                                member.checkedOutBooks.add(book);
                                book.setOwned(true);
                                book.setTotalBookCount(book.getTotalBookCount() - 1);
                                book.setDueDate(LocalDateTime.now().plusSeconds(10)); // Set the due date
                                book.setReturned(true); // Mark the book as returned
                                System.out.println("Book issued successfully!");
                            } else {
                                System.out.println("You have already checked out 5 books!");
                            }
                        } else {
                            System.out.println("You have a fine of Rs. " + member.getPenalty());
                        }
                    } else {
                        System.out.println("No copies of the book are available");
                    }
                } else {
                    System.out.println("The book has already been returned!");
                }
            } else {
                System.out.println("Book not found!");
            }
        } else {
            System.out.println("Member not found!");
        }
    }

    Member findMemberByNumber(String memberNumber) {
        for (Member member : members) {
            if (member.getMemberPhoneNo().equals(memberNumber)) {
                return member;
            }
        }
        return null;
    }

    public void returnBook(String memberNumber, int bookId) {
        Member member = findMemberByNumber(memberNumber);

        if (member != null) {
            Book book = findBookById(bookId);

            if (book != null) {
                if (member.checkedOutBooks.contains(book)) {
                    if (!book.isReturned()) { // Check if the book has been returned
                        member.checkedOutBooks.remove(book);
                        book.setTotalBookCount(book.getTotalBookCount() + 1);
                        book.setReturned(true); // Mark the book as returned
                        System.out.println("Book returned successfully!");

                        LocalDateTime currentDate = LocalDateTime.now();
                        LocalDateTime dueDate = book.getDueDate();

                        // Check if the book is past the due date
                        if (currentDate.isAfter(dueDate)) {
                            double fine = calculateFine(currentDate, dueDate, true);

                            if (fine > 0) {
                                System.out.println("You have a fine of Rs. " + fine);
                                member.PayFine(fine); // Assuming you have a payFine method in your Member class
                            }
                        }
                    } else {
                        System.out.println("The book has already been returned!");
                    }
                } else {
                    System.out.println("You have not checked out this book!");
                }
            } else {
                System.out.println("Book not found!");
            }
        } else {
            System.out.println("Member not found!");
        }
    }

    public double calculateFine(LocalDateTime currentDate, LocalDateTime dueDate, boolean isOwned) {
        if (isOwned && currentDate.isAfter(dueDate)) {
            Duration duration = Duration.between(dueDate, currentDate);
            long secondsLate = duration.getSeconds();
            double finePerSecond = 1.0; // 1 Rs per second (adjust as needed)
            double fine = secondsLate * finePerSecond;
            return fine;
        } else {
            return 0.0; // No fine if returned before due date or if the book is not owned
        }
    }


    public void addBook(Book book) {
        books.add(book);
    }
    public boolean removeBookByName(String bookName) {
        return books.removeIf(book -> book.getBookName().equalsIgnoreCase(bookName));
    }


    public void addMember(Member member) {
        members.add(member);
    }



    public void removeMember(String memberNumber) {
        Member member = findMemberByNumber(memberNumber);
        if (member != null) {
            members.remove(member);
            System.out.println("Member removed successfully!");
        } else {
            System.out.println("Member not found!");
        }
    }
        }
