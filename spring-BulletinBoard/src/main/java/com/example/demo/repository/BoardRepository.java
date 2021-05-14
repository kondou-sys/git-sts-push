package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Board;
import java.util.Collection;

public interface BoardRepository extends JpaRepository<Board,Long> {
		
	public Board findById(int id);
	public void deleteById(int id);
	Collection<Board> findByContentLike(String content);
	
}
