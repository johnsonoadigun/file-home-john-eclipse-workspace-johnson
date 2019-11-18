package com.johnson.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.johnson.model.Role;





public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findRoleByName(String name);

}