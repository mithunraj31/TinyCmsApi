package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDateTime;

import com.cms.dto.StonkamAccessTokenDto;
import com.cms.serviceimpl.StonkamServiceImpl;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import config.TestContextConfiguration;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = TestContextConfiguration.class)
public class StomkamServiceTests {

    @Autowired
    private StonkamAccessTokenDto stonkamAccessTokenDto;

    @Autowired
    private StonkamServiceImpl stonkamServiceImpl;

    @Test
    @Order(1)
    public void whenStartApplication_thenAccessTokenShouldNotBeZero() {
        final long token = stonkamAccessTokenDto.getAccessToken();
        assertEquals(token, 0);
    }

    @Test
    @Order(2)
    public void whenStartApplicationAndRefreshToken_thenObtainedAccessTokenShouldNotBeZero() {
        final long token = this.stonkamServiceImpl.refreshAccessToken();
        assertNotEquals(token, 0);
    }

    @Test
    @Order(3)
    public void whenRefreshToken_thenSingletonAccessTokenShouldNotBeZero() {
        final long token = stonkamAccessTokenDto.getAccessToken();
        assertNotEquals(token, 0);
    }

    @Test
    @Order(4)
    public void ifTokenNotExpire_contextTokenShouldSameServiceResultToken() {
        final long serviceResultToken = this.stonkamServiceImpl.refreshAccessToken();
        final long contextToken = stonkamAccessTokenDto.getAccessToken();
        assertEquals(serviceResultToken, contextToken);
    }

    @Test
    @Order(5)
    public void whenTokenExpired_thenTheObtainedTokenShouldBeChanged() {
        final long oldContextToken = stonkamAccessTokenDto.getAccessToken();
        stonkamAccessTokenDto.setExipreDateTime(LocalDateTime.now().plusMinutes(-15));
        final long serviceResultToken = this.stonkamServiceImpl.refreshAccessToken();
        assertNotEquals(oldContextToken, serviceResultToken);
    }
}