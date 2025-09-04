package com.examly.springapp;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import java.io.File;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = SpringappApplication.class)
@AutoConfigureMockMvc
class SpringappBookingTests {

    @Autowired
    private MockMvc mockMvc;

    // ---------- Core API Tests ----------

    @Order(1)
    @Test
    void AddBookingReturns200() throws Exception {
        String bookingData = """
                {
                    "customerName": "John Doe",
                    "pickupLocation": "Central Park",
                    "dropLocation": "Times Square",
                    "rideTime": "2025-08-25T12:00:00"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookings")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingData)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Order(2)
    @Test
    void GetAllBookingsReturnsArray() throws Exception {
        mockMvc.perform(get("/api/bookings")
                        .with(jwt())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @Order(3)
    @Test
    void GetBookingByIdReturns200() throws Exception {
        mockMvc.perform(get("/api/bookings/1")
                        .with(jwt())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").exists())
                .andReturn();
    }

    @Order(4)
    @Test
    void UpdateBookingReturns200() throws Exception {
        String updatedBooking = """
                {
                    "customerName": "Jane Doe",
                    "pickupLocation": "Broadway",
                    "dropLocation": "Wall Street",
                    "rideTime": "2025-08-25T15:00:00"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/bookings/1")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBooking)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Jane Doe"))
                .andReturn();
    }

    @Order(5)
    @Test
    void DeleteBookingReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/bookings/1")
                        .with(jwt())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Booking deleted successfully!"))
                .andReturn();
    }

    // ---------- Project Structure Tests ----------

    @Test
    void ControllerDirectoryExists() {
        String directoryPath = "src/main/java/com/examly/springapp/controller";
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    @Test
    void BookingControllerFileExists() {
        String filePath = "src/main/java/com/examly/springapp/controller/BookingController.java";
        File file = new File(filePath);
        assertTrue(file.exists() && file.isFile());
    }

    @Test
    void ModelDirectoryExists() {
        String directoryPath = "src/main/java/com/examly/springapp/model";
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    @Test
    void BookingModelFileExists() {
        String filePath = "src/main/java/com/examly/springapp/model/Booking.java";
        File file = new File(filePath);
        assertTrue(file.exists() && file.isFile());
    }

    @Test
    void RepositoryDirectoryExists() {
        String directoryPath = "src/main/java/com/examly/springapp/repository";
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    @Test
    void ServiceDirectoryExists() {
        String directoryPath = "src/main/java/com/examly/springapp/service";
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    @Test
    void BookingServiceClassExists() {
        checkClassExists("com.examly.springapp.service.BookingService");
    }

    @Test
    void BookingModelClassExists() {
        checkClassExists("com.examly.springapp.model.Booking");
    }

    @Test
    void BookingModelHasCustomerNameField() {
        checkFieldExists("com.examly.springapp.model.Booking", "customerName");
    }

    @Test
    void BookingModelHasPickupLocationField() {
        checkFieldExists("com.examly.springapp.model.Booking", "pickupLocation");
    }

    @Test
    void BookingModelHasDropLocationField() {
        checkFieldExists("com.examly.springapp.model.Booking", "dropLocation");
    }

    @Test
    void BookingModelHasRideTimeField() {
        checkFieldExists("com.examly.springapp.model.Booking", "rideTime");
    }

    @Test
    void BookingModelHasFareField() {
        checkFieldExists("com.examly.springapp.model.Booking", "fare");
    }

    @Test
    void BookingRepoExtendsJpaRepository() {
        checkClassImplementsInterface("com.examly.springapp.repository.BookingRepository", "org.springframework.data.jpa.repository.JpaRepository");
    }

    @Test
    void CorsConfigurationClassExists() {
        checkClassExists("com.examly.springapp.configuration.CorsConfiguration");
    }

    @Test
    void CorsConfigurationHasConfigurationAnnotation() {
        checkClassHasAnnotation("com.examly.springapp.configuration.CorsConfiguration", "org.springframework.context.annotation.Configuration");
    }

    @Test
    void BookingNotFoundExceptionClassExists() {
        checkClassExists("com.examly.springapp.exception.BookingNotFoundException");
    }

    @Test
    void BookingNotFoundExceptionExtendsRuntimeException() {
        try {
            Class<?> clazz = Class.forName("com.examly.springapp.exception.BookingNotFoundException");
            assertTrue(RuntimeException.class.isAssignableFrom(clazz),
                    "BookingNotFoundException should extend RuntimeException");
        } catch (ClassNotFoundException e) {
            fail("BookingNotFoundException class does not exist.");
        }
    }

    // ---------- Helpers ----------

    private void checkClassExists(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            fail("Class " + className + " does not exist.");
        }
    }

    private void checkFieldExists(String className, String fieldName) {
        try {
            Class<?> clazz = Class.forName(className);
            clazz.getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            fail("Field " + fieldName + " in class " + className + " does not exist.");
        }
    }

    private void checkClassImplementsInterface(String className, String interfaceName) {
        try {
            Class<?> clazz = Class.forName(className);
            Class<?> interfaceClazz = Class.forName(interfaceName);
            assertTrue(interfaceClazz.isAssignableFrom(clazz));
        } catch (ClassNotFoundException e) {
            fail("Class " + className + " or interface " + interfaceName + " does not exist.");
        }
    }

    private void checkClassHasAnnotation(String className, String annotationName) {
        try {
            Class<?> clazz = Class.forName(className);
            Class<?> annotationClazz = Class.forName(annotationName);
            assertTrue(clazz.isAnnotationPresent((Class<? extends java.lang.annotation.Annotation>) annotationClazz));
        } catch (ClassNotFoundException e) {
            fail("Class " + className + " or annotation " + annotationName + " does not exist.");
        }
    }
}
