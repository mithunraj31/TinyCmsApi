package config;

import com.cms.dto.StonkamAccessTokenDto;
import com.cms.serviceimpl.StonkamServiceImpl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class TestContextConfiguration {

    @Bean
    public StonkamServiceImpl stonkamServiceImpl() {
        return new StonkamServiceImpl();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public StonkamAccessTokenDto stonkamAccessToken() {
        return new StonkamAccessTokenDto();
    }
}