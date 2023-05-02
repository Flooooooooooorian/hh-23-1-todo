package de.neuefische.todo.backend;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public Cloudinary createCloudinary() {
        return new Cloudinary();
    }
}
