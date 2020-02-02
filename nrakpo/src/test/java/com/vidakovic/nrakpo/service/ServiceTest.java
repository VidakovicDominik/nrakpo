package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.data.repository.UserRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * <p>
 * <b>Title: ServiceTest  </b>
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
 * PA1 02-Feb-20
 * @since 02-Feb-20 10:25:42
 */

@SpringBootTest
public class ServiceTest {

    @MockBean
    UserRepository userRepository;

}

