package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{


	public List<Comment> findByUser(Optional<User> user);
	public List<Comment> findByUser(User user);
	
	
}
