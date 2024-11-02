package HospitalManagementSystem;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PatientTest {

    private Connection connection;
    private Scanner scanner;
    private Patient patient;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        scanner = new Scanner(System.in);
        patient = new Patient(connection, scanner);
    }

    @Test
    public void testAddPatient_Success() throws SQLException {
        // Mock behavior for insert statement
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulating successful insert

        patient.addPatient();

        verify(preparedStatement).setString(1, "John Doe");
        verify(preparedStatement).setInt(2, 30);
        verify(preparedStatement).setString(3, "Male");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testViewPatients() throws SQLException {
        // Prepare the mock ResultSet
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false); // Simulate one patient

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getInt("age")).thenReturn(30);
        when(resultSet.getString("gender")).thenReturn("Male");

        patient.viewPatients();

        verify(preparedStatement).executeQuery();
    }

    @Test
    public void testGetPatientById_Exists() throws SQLException {
        // Mock behavior for select statement
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulating found patient

        assertTrue(patient.getPatientById(1));
    }

    @Test
    public void testGetPatientById_NotExists() throws SQLException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simulating not found

        assertFalse(patient.getPatientById(1));
    }
}
