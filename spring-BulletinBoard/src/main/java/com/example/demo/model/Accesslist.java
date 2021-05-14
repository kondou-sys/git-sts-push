package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Accesslist")
public class Accesslist {

	@Id
	@GeneratedValue
	private Long id;
	
	private Date datetime;
	
	private String username;
	
	private String action;
		
}
