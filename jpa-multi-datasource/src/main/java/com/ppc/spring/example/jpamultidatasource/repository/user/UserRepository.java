package com.ppc.spring.example.jpamultidatasource.repository.user;

import com.ppc.spring.example.jpamultidatasource.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Shallow
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
