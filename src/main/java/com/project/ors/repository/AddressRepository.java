package com.project.ors.repository;

import com.project.ors.entity.Address;
import org.springframework.data.repository.Repository;

public interface AddressRepository extends Repository<Address, Integer>{
    Address save(Address address);
}
