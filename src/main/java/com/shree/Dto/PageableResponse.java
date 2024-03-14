package com.shree.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
@Builder
public class PageableResponse<T> {
 
	private List<T> content;
	private int pageNumber;
	private int pageSize;
	private Long totalElement;
	private int totalPages;
	private boolean lastPage;
	
}
