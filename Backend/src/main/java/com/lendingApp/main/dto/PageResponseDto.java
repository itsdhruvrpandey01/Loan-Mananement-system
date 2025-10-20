package com.lendingApp.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PageResponseDto<T> {
	private List<T> contents;
	private long totalElements;
	private int totalPage;
	private int size;
	private boolean isFirst;
	private boolean isLast;
}
