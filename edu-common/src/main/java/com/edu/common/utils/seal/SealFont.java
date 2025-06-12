package com.edu.common.utils.seal;

import lombok.Data;

@Data
public class SealFont {
    private String text;
    private String family = "宋体";
    private Integer size;
    private Boolean bold = true;
    private Double space;
    private Integer margin;
}
