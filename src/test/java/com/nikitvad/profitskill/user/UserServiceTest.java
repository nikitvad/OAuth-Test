package com.nikitvad.profitskill.user;

import com.nikitvad.profitskill.util.Measurable;
import com.nikitvad.profitskill.util.PerformanceMater;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    public UserService userService;

    @Test(expected = Exception.class)
    public void testRegistration() {
        userService.registerUser(UserRegistrationDto.builder()
                .username("usertest")
                .password("password")
                .build());

        userService.registerUser(UserRegistrationDto.builder()
                .username("usertest")
                .password("password")
                .build());
    }

    @Test
    public void testGetAllUsers() {
        List<UserDto> allUsers = userService.getAllUsers();
        assert allUsers.size() > 0;
    }


//    @Test
//    public void performanceTest() {
//
//
//        PerformanceMater.mater((Measurable<?>)()->{
//            for (int i = 0; i < 1000; i++) {
//                userService.registerUser(UserDto.builder()
//                        .username("usertest" + i)
//                        .password("password")
//                        .build());
//            }
//            return new Object();
//        });
//
//
//        List mater = PerformanceMater.mater((Measurable<List>) () -> userService.getAllUsers());
//
//    }
}