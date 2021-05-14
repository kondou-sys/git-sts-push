package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {
	@Id
	@GeneratedValue
	private Long id;
	
	private String crateUser;
	
	private String title;
	
	private Date datetime;
	
	@NotBlank
	@Size(max = 500)
	private String content;

}
