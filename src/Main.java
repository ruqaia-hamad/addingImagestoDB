import images.DatabaseConnection;
import images.ImageRepository;
import images.ImageRetrieval;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final String SAVE_IMAGE_PATH = "C:\\Users\\ruqha\\OneDrive\\Desktop\\animals.jpg";
    private static final String RETRIEVE_OUTPUT_PATH = "C:\\Users\\ruqha\\OneDrive\\Desktop\\images\\animal.jpg";

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in); Connection connection = DatabaseConnection.connect()) {

            if (connection == null) {
                System.err.println("Failed to establish a database connection.");
                return;
            }

            boolean isExitMenu = false;

            while (!isExitMenu) {
                System.out.println("Please Choose Number From Menu:               \n");
                System.out.println("| 1- Save Image to Database                      |");
                System.out.println("| 2- Retrieve image from Database                |");
                System.out.println("| 3- Exit                                        |");

                int op = sc.nextInt();

                switch (op) {
                    case 1:
                        try {
                            String imageType = "jpg";
                            ImageRepository.saveImage(connection, SAVE_IMAGE_PATH, imageType);
                            System.out.println("Image saved to the database successfully.");
                        } catch (IOException e) {
                            System.err.println("Error saving the image: " + e.getMessage());
                        }
                        break;

                    case 2:
                        try {
                            int imageId = 1;
                            ImageRetrieval.retrieveImage(connection, imageId, RETRIEVE_OUTPUT_PATH);
                            System.out.println("Image retrieved and saved successfully.");
                        } catch (IOException e) {
                            System.err.println("Error retrieving the image: " + e.getMessage());
                        }
                        break;

                    case 3:
                        isExitMenu = true;
                        System.out.println("Exiting the menu. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid option. Please choose a valid number.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
}
