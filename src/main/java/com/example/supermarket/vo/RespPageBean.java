package com.example.supermarket.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespPageBean {

    // 总条数
    private Long total;

    // 数据列表
    private List<?> data;


}