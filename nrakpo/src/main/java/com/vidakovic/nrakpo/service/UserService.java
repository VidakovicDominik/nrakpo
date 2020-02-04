package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.controller.apimodel.UserApiModel;
import com.vidakovic.nrakpo.controller.form.RegistrationForm;

import java.util.List;

/**
 * <p>
 * <b>Title: UserService  </b>
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
 * PA1 04-Feb-20
 * @since 04-Feb-20 15:19:42
 */
public interface UserService {

    List<UserApiModel> getUsers();

    UserApiModel getUser(String username);

    void updateUser(UserApiModel userApiModel);

    void insertUser(RegistrationForm registrationForm);

    void checkMonthlyConsumption(String username);
}
