package com.ms.hoopi.common.service;

import com.ms.hoopi.common.model.BoardResponseDto;
import com.ms.hoopi.common.model.CategoryResponseDto;

import java.util.List;

public interface BoardAndCateService {
    public List<CategoryResponseDto> getCategory(String boardCode);

    BoardResponseDto getBoard(String boardId);

    List<BoardResponseDto> getMenu();
}
