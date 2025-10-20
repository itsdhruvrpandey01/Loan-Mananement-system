package com.lendingApp.main.helper;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lendingApp.main.dto.PageResponseDto;

public class PaginationUtils {

    public static <T, U> PageResponseDto<U> buildPageResponse(Page<T> page, List<U> contentList) {
        PageResponseDto<U> response = new PageResponseDto<>();
        response.setTotalElements(page.getTotalElements());
        response.setTotalPage(page.getTotalPages());
        response.setSize(page.getSize());
        response.setFirst(page.isFirst());
        response.setLast(page.isLast());
        response.setContents(contentList);
        return response;
    }
}

