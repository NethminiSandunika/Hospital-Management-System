package HospitalManagementSystem;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorTest {

    private Connection connection;
    private Doctor doctor;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        doctor = new Doctor(connection);
    }

    @Test
    public void testViewDoctors() throws SQLException {
        // Prepare the mock ResultSet
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false); // Simulate one doctor

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Dr. Smith");
        when(resultSet.getString("specialization")).thenReturn("Cardiology");

        doctor.viewDoctors();

        verify(preparedStatement).executeQuery();
    }

    @Test
    public void testGetDoctorById_Exists() throws SQLException {
        // Mock behavior for select statement
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulating found doctor

        assertTrue(doctor.getDoctorById(1));
    }

    @Test
    public void testGetDoctorById_NotExists() throws SQLException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simulating not found

        assertFalse(doctor.getDoctorById(1));
    }

    @Test
    public void testCheckDoctorAvailability_Available() throws SQLException {
        // Mock behavior for availability check
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(0); // Count 0 means available

        assertTrue(doctor.checkDoctorAvailability(1, "2024-11-02"));
    }

    @Test
    public void testCheckDoctorAvailability_NotAvailable() throws SQLException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1); // Count > 0 means not available

        assertFalse(doctor.checkDoctorAvailability(1, "2024-11-02"));
    }
}
