package com.ppc.spring.example.jpamultidatasource.repository.business;

import com.ppc.spring.example.jpamultidatasource.entity.business.Business;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Shallow
 */
public interface BusinessRepository extends JpaRepository<Business, Long> {
}
