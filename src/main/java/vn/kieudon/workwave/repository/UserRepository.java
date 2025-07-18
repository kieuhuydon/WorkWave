package vn.kieudon.workwave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.kieudon.workwave.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    
}