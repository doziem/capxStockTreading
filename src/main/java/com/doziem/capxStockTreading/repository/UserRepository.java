package com.doziem.capxStockTreading.repository;

import com.doziem.capxStockTreading.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
