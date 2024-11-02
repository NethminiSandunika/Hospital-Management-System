package HospitalManagementSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalManagementSystemTest {
    private Connection connection;
    private Doctor doctor;
    private Patient patient;
    private Scanner scanner;

    @BeforeEach
    public void setUp() throws SQLException {
        // Setup the database connection for tests
        String url = "jdbc:mysql://localhost:3306/hospital";
        String username = "root";
        String password = "";
        connection = DriverManager.getConnection(url, username, password);
        scanner = new Scanner(System.in);
        patient = new Patient(connection, scanner);
        doctor = new Doctor(connection);
    }

    @Test
    public void testCheckDoctorAvailability() {
        int doctorId = 1; // Replace with a valid doctor ID
        String appointmentDate = "2024-11-01"; // Replace with a test date
        boolean isAvailable = doctor.checkDoctorAvailability(doctorId, appointmentDate);
        assertTrue(isAvailable, "Doctor should be available on this date");
    }

    @Test
    public void testGetDoctorById() {
        int doctorId = 1; // Replace with a valid doctor ID
        boolean exists = doctor.getDoctorById(doctorId);
        assertTrue(exists, "Doctor should exist with the given ID");
    }

    @Test
    public void testGetPatientById() {
        int patientId = 1; // Replace with a valid patient ID
        boolean exists = patient.getPatientById(patientId);
        assertTrue(exists, "Patient should exist with the given ID");
    }

    @Test
    public void testAddPatient() {
        // This test would typically involve adding a patient and checking if it was added successfully.
        // For this example, you might want to implement a way to validate if a patient was added correctly.
    }

    @Test
    public void testViewPatients() {
        // Similar to adding a patient, this test would involve verifying that patients can be viewed correctly.
        // This could be checked by validating the output or the state of the database.
    }

    // Clean up resources after tests
    @AfterEach
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        scanner.close();
    }
}
