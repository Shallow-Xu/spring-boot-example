package com.ppc.spring.example.jpamultidatasource;

import com.ppc.spring.example.jpamultidatasource.entity.business.Business;
import com.ppc.spring.example.jpamultidatasource.entity.user.User;
import com.ppc.spring.example.jpamultidatasource.repository.business.BusinessRepository;
import com.ppc.spring.example.jpamultidatasource.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shallow
 */
@RestController
@SpringBootApplication
public class JpaMultiDatasourceApplication {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BusinessRepository businessRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaMultiDatasourceApplication.class, args);
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/business/{id}")
    public Business getBusiness(@PathVariable Long id) {
        return businessRepository.findById(id).orElse(null);
    }
}
