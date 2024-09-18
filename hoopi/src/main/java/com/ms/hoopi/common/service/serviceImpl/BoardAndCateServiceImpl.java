package com.ms.hoopi.common.service.serviceImpl;

import com.ms.hoopi.common.model.BoardResponseDto;
import com.ms.hoopi.common.model.CategoryResponseDto;
import com.ms.hoopi.common.service.BoardAndCateService;
import com.ms.hoopi.model.entity.Board;
import com.ms.hoopi.model.entity.Category;
import com.ms.hoopi.repository.BoardRepository;
import com.ms.hoopi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardAndCateServiceImpl implements BoardAndCateService {

    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;

    @Override
    public List<CategoryResponseDto> getCategory(String boardCode) {
        List<Category> categoryEntity =  categoryRepository.findAllByBoardCode(boardCode);
        List<CategoryResponseDto> category = categoryEntity.stream().map(c -> CategoryResponseDto.builder()
                                                                                                .id(c.getId())
                                                                                                .name(c.getName())
                                                                                                .depth(c.getDepth())
                                                                                                .categoryId(c.getCategoryId())
                                                                                                .superId(c.getSuperId())
                                                                                                .build()).toList();
        return category;
    }

    @Override
    public BoardResponseDto getBoard(String boardId) {
        Board boardEntity = boardRepository.findBoardByBoardId(boardId);
        BoardResponseDto board = BoardResponseDto.builder()
                                                .boardCode(boardEntity.getBoardCode())
                                                .boardId(boardEntity.getBoardId())
                                                .superId(boardEntity.getSuperId())
                                                .name(boardEntity.getName())
                                                .depth(boardEntity.getDepth())
                                                .build();
        return board;
    }

    @Override
    public List<BoardResponseDto> getMenu() {
        List<Board> menuEntity = boardRepository.findAll();
        List<BoardResponseDto> menu = menuEntity.stream().map(m -> BoardResponseDto.builder()
                                                                                    .boardCode(m.getBoardCode())
                                                                                    .boardId(m.getBoardId())
                                                                                    .name(m.getName())
                                                                                    .depth(m.getDepth())
                                                                                    .superId(m.getSuperId())
                                                                                    .build()).toList();
        return menu;
    }
}
