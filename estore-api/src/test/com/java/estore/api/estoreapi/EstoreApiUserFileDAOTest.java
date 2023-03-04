import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Tag("Persistence-tier")
@TestPropertySource(locations="classpath:application-test.properties")
public class UserFileDAOTest {

    @Autowired
    private UserFileDAO userFileDAO;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${users.file}")
    private String usersFilename;

    @BeforeEach
    void setUp() throws IOException {
        // Delete the users file before each test
        assertTrue(new File(usersFilename).delete());
        userFileDAO.load();
    }

    @Test
    void testAddUser() {
        User user = new User(0, "John Doe");
        userFileDAO.addUser(user);
        User[] users = userFileDAO.getUsers();
        assertEquals(1, users.length);
        assertEquals(user, users[0]);
    }

    @Test
    void testAddUserWithEmptyName() {
        User user = new User(0, "");
        assertThrows(IllegalArgumentException.class, () -> userFileDAO.addUser(user));
    }


    @Test
    void testUpdateUser() {
        User user = new User(0, "John Doe");
        userFileDAO.addUser(user);
        User updatedUser = new User(user.getId(), "Jane Doe");
        userFileDAO.updateUser(updatedUser);
        User[] users = userFileDAO.getUsers();
        assertEquals(1, users.length);
        assertEquals(updatedUser, users[0]);
    }

    @Test
    void testDeleteUser() {
        User user = new User(0, "John Doe");
        userFileDAO.addUser(user);
        userFileDAO.deleteUser(user.getId());
        User[] users = userFileDAO.getUsers();
        assertEquals(0, users.length);
    }

    @Test
    void testGetUsers() {
        User user1 = new User(0, "John Doe");
        User user2 = new User(0, "Jane Doe");
        userFileDAO.addUser(user1);
        userFileDAO.addUser(user2);
        User[] users = userFileDAO.getUsers();
        assertEquals(2, users.length);
        assertTrue(Arrays.asList(users).contains(user1));
        assertTrue(Arrays.asList(users).contains(user2));
    }

    @Test
    void testGetUsersWithFilter() {
        User user1 = new User(0, "John Doe");
        User user2 = new User(0, "Jane Doe");
        userFileDAO.addUser(user1);
        userFileDAO.addUser(user2);
        User[] users = userFileDAO.getUsers("Jane");
        assertEquals(1, users.length);
        assertTrue(Arrays.asList(users).contains(user2));
        assertFalse(Arrays.asList(users).contains(user1));
    }

    @Test
    void testLoad() throws IOException {
        User user = new User(0, "John Doe");
        userFileDAO.addUser(user);
        userFileDAO.save();
        UserFileDAO userFileDAO2 = new UserFileDAO(usersFilename, objectMapper);
        User[] users = userFileDAO2.getUsers();
        assertEquals(1, users.length);
        assertEquals(user, users[0]);
    }

    @Test
    void testSave() throws IOException {
        User user = new User(0, "John Doe");
        userFileDAO.addUser(user);
        userFileDAO.save();
        User[] users = objectMapper.readValue(new File(usersFilename), User[].class);
        assertEquals(1,users.length);
        assertEquals(user, users[0]);
    }
}