package com.example.ignite_service.repository;

import com.example.ignite_service.UserModel;
import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;

@RepositoryConfig(cacheName = "User")
public interface UsersRepository extends IgniteRepository<Long, UserModel> {
}
