package HospitalManagementSystem;

import java.sql.Connection;  // for SQL database connection
import java.sql.PreparedStatement; //for preparing and executing SQL statements
import java.sql.ResultSet; //to read the data from the database
import java.sql.SQLException; // for handling SQL exceptions
import java.util.Scanner; //for any user input

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    // Method to retrieve and display all doctors from the doctors table.
    public void viewDoctors() {
        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-10s | %-18s | %-16s |\n", id, name, specialization);
                System.out.println("+------------+--------------------+------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This method retrieves a specific doctor’s record based on an ID
    public boolean getDoctorById(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if a record was found
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to check if a doctor is available on a specific date
    public boolean checkDoctorAvailability(int doctorId, String appointmentDate) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0; // Available if count is 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Default to not available on error
    }
}
