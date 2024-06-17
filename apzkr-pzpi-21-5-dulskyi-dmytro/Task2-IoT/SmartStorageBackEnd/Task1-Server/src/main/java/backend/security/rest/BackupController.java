package backend.security.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BackupController {

   @GetMapping("/backupDB")
   @SecurityRequirement(name = "bearerAuth")
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public static void backupDB() {
      ProcessBuilder processBuilder = new ProcessBuilder(
         "D:\\PostgreSQL\\16\\bin\\pg_dump.exe",
         "--host", "localhost",
         "--port", "5432",
         "--username", "postgres",
         "--no-password",
         "--blobs",
         "--verbose", "--file", "D:\\KHNURE\\Файлы, учеба\\3 курс\\Семестр 6\\АПЗ\\Project Я\\SmartStorageBackEndOG OG copy from copy\\target\\backup\\storage_backup.sql", "storage");

      try {
         final Map<String, String> env = processBuilder.environment();
         env.put("PGPASSWORD", "StudentDD28P");
         Process process = processBuilder.start();

         final BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(process.getErrorStream()));

         String line = bufferedReader.readLine();

         while (line != null) {
            System.err.println(line);
            line = bufferedReader.readLine();
         }

         bufferedReader.close();
         process.waitFor();
         System.out.println(process.exitValue());

      } catch (IOException | InterruptedException e) {
         System.out.println(e.getMessage());
      }
   }

   @PostMapping("/restoreDB")
   @SecurityRequirement(name = "bearerAuth")
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public static void restoreDB() {
      dropDatabase();
      createDatabase();

      ProcessBuilder processBuilder = new ProcessBuilder(
         "D:\\PostgreSQL\\16\\bin\\psql",
         "--username=postgres",
         "--dbname=storage",
         "--no-password",
         "--single-transaction",
         "--file=D:\\KHNURE\\Файлы, учеба\\3 курс\\Семестр 6\\АПЗ\\Project Я\\SmartStorageBackEndOG OG copy from copy\\target\\backup\\storage_backup.sql");

      try {
         final Map<String, String> env = processBuilder.environment();
         env.put("PGPASSWORD", "StudentDD28P");
         Process process = processBuilder.start();

         final BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(process.getErrorStream()));

         String line = bufferedReader.readLine();

         while (line != null) {
            System.err.println(line);
            line = bufferedReader.readLine();
         }

         bufferedReader.close();
         process.waitFor();
         System.out.println(process.exitValue());

      } catch (IOException | InterruptedException e) {
         System.out.println(e.getMessage());
      }
   }

   public static void dropDatabase() {
      // Open a connection
      try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "StudentDD28P");
           Statement statement = connection.createStatement();
      ) {
         statement.executeUpdate("UPDATE pg_database SET datallowconn = 'false' WHERE datname = 'storage'");
         statement.execute("SELECT pg_terminate_backend(pg_stat_activity.pid)\n" +
            "FROM pg_stat_activity\n" +
            "WHERE pg_stat_activity.datname = 'storage' AND pid <> pg_backend_pid()");

         statement.execute("DROP DATABASE storage");
         statement.close();
         System.out.println("Database dropped successfully...");
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public static void createDatabase() {
      // Open a connection
      try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "StudentDD28P");
           Statement statement = connection.createStatement();
      ) {
         String sql = "CREATE DATABASE storage";
         statement.executeUpdate(sql);
         statement.close();
         System.out.println("Database created successfully...");
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
}
