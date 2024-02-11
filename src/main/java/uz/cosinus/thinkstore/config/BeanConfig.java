package uz.cosinus.thinkstore.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableTransactionManagement
public class BeanConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean(name = "eskizRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .rootUri("https://notify.eskiz.uz")
                .build();

    }


}
