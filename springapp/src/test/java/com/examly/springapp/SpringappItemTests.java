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
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = SpringappApplication.class)
@AutoConfigureMockMvc
class SpringappPostTests {

    @Autowired
    private MockMvc mockMvc;

    // ---------- Core API Tests ----------

    @Order(1)
    @Test
    void AddPostReturns200() throws Exception {
        String postData = """
                {
                    "caption": "Hello World",
                    "imageUrl": "https://example.com/test.jpg",
                    "hashtags": "#test",
                    "timestamp": "2025-08-30T12:00:00"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/addPost")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postData)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Order(2)
    @Test
    void GetAllPostsReturnsArray() throws Exception {
        mockMvc.perform(get("/api/posts/allPosts")
                        .with(jwt())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @Order(3)
    @Test
    void GetPostsByHashtagReturns200() throws Exception {
        mockMvc.perform(get("/api/posts/byHashtag")
                        .with(jwt())
                        .param("tag", "#test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].caption").exists())
                .andReturn();
    }

    @Order(4)
    @Test
    void GetPostsSortedByTimeReturns200() throws Exception {
        mockMvc.perform(get("/api/posts/sortedByTime")
                        .with(jwt())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
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
    void PostControllerFileExists() {
        String filePath = "src/main/java/com/examly/springapp/controller/PostController.java";
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
    void PostModelFileExists() {
        String filePath = "src/main/java/com/examly/springapp/model/Post.java";
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
    void PostServiceClassExists() {
        checkClassExists("com.examly.springapp.service.PostService");
    }

    @Test
    void PostModelClassExists() {
        checkClassExists("com.examly.springapp.model.Post");
    }

    @Test
    void PostModelHasCaptionField() {
        checkFieldExists("com.examly.springapp.model.Post", "caption");
    }

    @Test
    void PostModelHasImageUrlField() {
        checkFieldExists("com.examly.springapp.model.Post", "imageUrl");
    }

    @Test
    void PostModelHasHashtagsField() {
        checkFieldExists("com.examly.springapp.model.Post", "hashtags");
    }

    @Test
    void PostModelHasTimestampField() {
        checkFieldExists("com.examly.springapp.model.Post", "timestamp");
    }

    @Test
    void PostRepoExtendsJpaRepository() {
        checkClassImplementsInterface("com.examly.springapp.repository.PostRepository", "org.springframework.data.jpa.repository.JpaRepository");
    }

    @Test
    void PostNotFoundExceptionClassExists() {
        checkClassExists("com.examly.springapp.exception.PostNotFoundException");
    }

    @Test
    void PostNotFoundExceptionExtendsRuntimeException() {
        try {
            Class<?> clazz = Class.forName("com.examly.springapp.exception.PostNotFoundException");
            assertTrue(RuntimeException.class.isAssignableFrom(clazz),
                    "PostNotFoundException should extend RuntimeException");
        } catch (ClassNotFoundException e) {
            fail("PostNotFoundException class does not exist.");
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
}
