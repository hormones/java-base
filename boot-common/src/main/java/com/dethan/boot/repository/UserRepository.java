package com.dethan.boot.repository;

import com.dethan.boot.entity.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, String> {

    User save(User user);
}
