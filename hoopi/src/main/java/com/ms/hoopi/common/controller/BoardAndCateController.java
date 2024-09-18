package com.ms.hoopi.common.controller;

import com.ms.hoopi.common.model.BoardResponseDto;
import com.ms.hoopi.common.model.CategoryResponseDto;
import com.ms.hoopi.common.service.BoardAndCateService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("hoopi")
@RequiredArgsConstructor
public class BoardAndCateController {

    private final BoardAndCateService boardAndCateService;

    @GetMapping("/board")
    public BoardResponseDto getBoard(@RequestParam String boardId) {
        return boardAndCateService.getBoard(boardId);
    }

    @GetMapping("/category")
    public List<CategoryResponseDto> getCategory(@RequestParam String boardCode) {

        return boardAndCateService.getCategory(boardCode);
    }

    @GetMapping("/menu")
    public List<BoardResponseDto> getMenu() {
        return boardAndCateService.getMenu();
    }
}
