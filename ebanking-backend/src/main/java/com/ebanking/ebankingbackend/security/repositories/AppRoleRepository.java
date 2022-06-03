package com.ebanking.ebankingbackend.security.repositories;

import com.ebanking.ebankingbackend.security.entities.AppRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRoleName(String roleName);
}
