package com.bridgelabz.fundoonote.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class NoteDTO
{
	@NotBlank
	private String title;
	@NotBlank
	private String description;
}
