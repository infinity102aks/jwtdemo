package com.ankit.scheduler.loggers;

import com.ankit.scheduler.Entity.User;
import com.ankit.scheduler.models.AuthResponseDTO;
import com.ankit.scheduler.models.LoginDTO;
import com.ankit.scheduler.models.RegisterDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserServiceLogging {
    private static final Logger logger = LogManager.getLogger(UserServiceLogging.class);

    @Before("execution(* com.ankit.scheduler.services.UserService.createNewUser(..)) && args(dto)")
    public void logBeforeCreateUser(RegisterDTO dto) {
        logger.info("Creating a new user with details: " + dto);
    }

    @AfterReturning(pointcut = "execution(* com.ankit.scheduler.services.UserService.createNewUser(..))", returning = "result")
    public void logAfterCreateUser(User result) {
        logger.info("User created successfully: " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.ankit.scheduler.services.UserService.createNewUser(..))", throwing = "ex")
    public void logAfterCreateUserFailed(Exception ex) {
        logger.error("User creation failed with message: " + ex.getMessage());
    }

    @Before("execution(* com.ankit.scheduler.services.UserService.login(..)) && args(dto)")
    public void logBeforeLogging(LoginDTO dto) {
        logger.info("Login request from user: " + dto);
    }

    @AfterReturning(pointcut = "execution(* com.ankit.scheduler.services.UserService.login(..))", returning = "dto")
    public void logAfterAuthentication(AuthResponseDTO dto) {
        logger.info("User Logged in with token: " + dto);
    }

    @AfterThrowing(pointcut = "execution(* com.ankit.scheduler.services.UserService.login(..))", throwing = "ex")
    public void logAfterAuthentication(Exception ex) {
        logger.error("User Authentication failed with message: " + ex.getMessage());
    }


}
