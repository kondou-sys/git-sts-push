package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Accesslist;
import com.example.demo.model.Board;

public interface AccesslistRepository extends JpaRepository<Accesslist,Long> {

	public Accesslist findById(int id);
	
}