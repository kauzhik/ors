package com.project.ors.repository;

import com.project.ors.entity.Guardian;
import org.springframework.data.repository.Repository;

public interface GuardianRepository extends Repository<Guardian, Integer>{
    Guardian save(Guardian guardian);
}
