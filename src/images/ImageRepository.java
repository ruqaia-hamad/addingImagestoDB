package images;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ImageRepository {
    public static void saveImage(Connection connection, String imageUrl, String imageType) throws SQLException, IOException {
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Convert image file to byte array
        Path imagePath = Paths.get(imageUrl);
        byte[] imageData = Files.readAllBytes(imagePath);

        String sql = "INSERT INTO images (created_date, image_type, image_data) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, formattedDate);
            statement.setString(2, imageType);
            statement.setBytes(3, imageData);
            statement.executeUpdate();
        }
    }
}
