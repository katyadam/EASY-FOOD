package cz.muni.fi.pv168.project.storage.sql.db;

import cz.muni.fi.pv168.project.storage.DataStorageException;
import org.tinylog.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static final String PROJECT_NAME = "EASY-FOOD";
    private static final String DB_PROPERTIES_STRING = "DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false";

    public static Connection createConnection() {
        String connectionString = "jdbc:h2:%s;%s".formatted(createDbFileSystemPath(), DB_PROPERTIES_STRING);
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }


    private static Path createDbFileSystemPath() {
        String projectDir = System.getProperty("user.dir");
        Path projectDbPath = Paths.get(projectDir, "db", PROJECT_NAME);

        File parentDir = projectDbPath.getParent().toFile();

        if (parentDir.mkdirs()) {
            Logger.debug("Created a new root directory for the database: {}", projectDbPath.getParent());
        } else {
            Logger.debug("Root directory for the database already exists: {}", projectDbPath.getParent());
        }

        if (!parentDir.exists()) {
            throw new DataStorageException("Unable to create database root directory");
        }

        return projectDbPath;
    }


}


