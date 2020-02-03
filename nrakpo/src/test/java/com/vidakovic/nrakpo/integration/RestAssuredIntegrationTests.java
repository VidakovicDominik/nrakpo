package com.vidakovic.nrakpo.integration;

import com.vidakovic.nrakpo.controller.form.RegistrationForm;
import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.enums.AccountType;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.UserService;
import io.restassured.http.Method;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * <p>
 * <b>Title: RestAssuredIntegrationTests  </b>
 * </p>
 * <p>
 * <b> Description:
 *
 *
 * </b>
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) ETK 2020
 * </p>
 * <p>
 * <b>Company:</b> Ericsson Nikola Tesla d.d.
 * </p>
 *
 * @author ezviddo
 * @version PA1
 * <p>
 * <b>Version History:</b>
 * </p>
 * <br>
 * PA1 03-Feb-20
 * @since 03-Feb-20 16:56:42
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestAssuredIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @BeforeAll
    @Transactional
    public void createUser() {
        userRepository.deleteAll();
        userService.insertUser(new RegistrationForm("testuser", "pass", "email", UserType.ADMINISTRATOR, UserPackage.GOLD, AccountType.LOCAL));
    }

    @Test
    public void getPhotoDetailsTest() throws Exception {
        photoRepository.save(createPhoto()).getId();

        given()
                .port(port)
                .auth()
                .form("testuser","pass")
                .when().request(Method.GET, "/photo/1").then().
                assertThat().body(containsString("testuser"))
                .assertThat().body(containsString("description"))
                .statusCode(200);
    }

    @Test
    public void getPhotosTest() throws Exception {
        photoRepository.save(createPhoto()).getId();

        given()
                .port(port)
                .auth()
                .form("testuser","pass")
                .when().request(Method.GET, "/home").then().
                assertThat().body(containsString("testuser"))
                .assertThat().body(containsString("description"))
                .statusCode(200);
    }

    private Photo createPhoto() {
        return new Photo("description123", "url", "50X50", ImageFormat.JPEG, Arrays.asList(new Hashtag("hashtag")), userRepository.findById("testuser").get());
    }
}

