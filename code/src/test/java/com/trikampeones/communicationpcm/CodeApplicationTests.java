package com.trikampeones.communicationpcm;

import controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CodeApplicationTests {

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() throws Exception{
        assertThat(userController).isNotNull();
    }

}
