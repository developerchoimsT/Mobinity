package com.ms.hoopi.common.model;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardResponseDto {
    private String boardCode;
    private String name;
    private String depth;
    private String superId;
    private String boardId;

}
