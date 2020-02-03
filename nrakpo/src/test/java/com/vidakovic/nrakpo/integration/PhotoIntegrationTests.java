package com.vidakovic.nrakpo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
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
import io.restassured.http.ContentType;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PhotoIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    @Transactional
    public void createUser() {
        userRepository.deleteAll();
        userService.insertUser(new RegistrationForm("testuser", "pass", "email", UserType.ADMINISTRATOR, UserPackage.GOLD, AccountType.LOCAL));
    }

    @Test
    public void getPhotoDetailsTest() throws Exception {
        photoRepository.save(new Photo("description123","url","50X50", ImageFormat.JPEG, Arrays.asList(new Hashtag("hashtag")),userRepository.findById("testuser").get())).getId();

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
    public void updatePhotoTest() throws Exception {
        Photo photo = new Photo("description123", "url", "50X50", ImageFormat.JPEG, Arrays.asList(new Hashtag("hashtag")), userRepository.findById("testuser").get());
        photoRepository.save(photo).getId();
        PhotoApiModel photoApiModel=new PhotoApiModel(photo);
        photoApiModel.setDescription("new description");
        photoApiModel.setSizeX("60");
        photoApiModel.setSizeY("60");
        photoApiModel.setHashtags("#newhashtag");
        photoApiModel.setId(photo.getId());

        given()
                .port(port)
                .auth()
                .basic("testuser","pass")
                .body(objectMapper.writeValueAsString(photoApiModel))
                .contentType(ContentType.JSON)
                .when().request(Method.POST, "/photo/update").then()
                .statusCode(302);

        Photo updatedPhoto = photoRepository.findById(photo.getId()).get();
        assertThat(updatedPhoto.getDescription()).isEqualTo("new description");
    }




}

