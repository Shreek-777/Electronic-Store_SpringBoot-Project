package com.shree.Helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.shree.Dto.PageableResponse;
import com.shree.Dto.UserDto;
import com.shree.Entities.UserDetails;

public class Helper {

	public static <U,V>PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type){
		
		// now from this page to convert into
				List<U> entity = page.getContent();

				// now stream and map on userlist on by one and save to dtoList objet and return
				List<V> dtoList = entity.stream()
						       .map((object) -> new ModelMapper().map(object, type))
						       .collect(Collectors.toList());
				PageableResponse<V> response= new PageableResponse<>();
				 
				response.setContent(dtoList);
				response.setPageNumber(page.getNumber());
				response.setLastPage(page.isLast());
				response.setPageSize(page.getSize());
				response.setTotalElement(page.getTotalElements());
				response.setTotalPages(page.getTotalPages());
				
				return response;
	}
	
//	public static Sort sortDir(String sortDir, String sortBy) {
//		
//		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//		
//		return sort;
//	}
//	
	

//	    public static Sort sortDir(String sortDir, String sortBy) {
//	        return sortDir.equalsIgnoreCase("desc") ?
//	                Sort.by(Sort.Order.desc(sortBy)) :
//	                Sort.by(Sort.Order.asc(sortBy));
//	    }

	    // Other helper methods...
	
	    
	    public static PageRequest createPageRequest(int pageNumber, int pageSize, String sortDir, String sortBy) {
//	        Sort sort = sortDir.equalsIgnoreCase("desc") ?
//	                Sort.by(Sort.Order.desc(sortBy)) :
//	                Sort.by(Sort.Order.asc(sortBy));

	        return PageRequest.of(pageNumber, pageSize);
	    }

}
