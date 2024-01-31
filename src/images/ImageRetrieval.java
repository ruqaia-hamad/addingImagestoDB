package images;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageRetrieval {
    public static void retrieveImage(Connection connection, int imageId, String outputPath) throws SQLException, IOException {
        String sql = "SELECT image_data FROM images WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, imageId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve image data
                    byte[] imageData = resultSet.getBytes("image_data");

                    // Save image to file
                    saveImageToFile(imageData, outputPath);
                }
            }
        }
    }

    private static void saveImageToFile(byte[] imageData, String outputPath) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(outputPath)) {
            outputStream.write(imageData);
        }
    }
}
