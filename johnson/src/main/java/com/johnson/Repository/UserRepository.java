package com.johnson.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;


import com.johnson.model.EntityUser;

public interface UserRepository extends JpaRepository<EntityUser,Long>{
	
	public EntityUser findByUsername(String username);

	public EntityUser findByEmail(String userEmail);
		
	
	public EntityUser findUserById( Long id);

	public List<EntityUser> findByUsernameContaining(String username);
}
