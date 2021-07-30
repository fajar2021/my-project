package org.repository;

import java.util.List;
import org.model.Stuff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Stuff, Long>{
	
	
	public List<Stuff> findByPublished(boolean published);
	public List<Stuff> findByTitleContaining(String title);
	
}
