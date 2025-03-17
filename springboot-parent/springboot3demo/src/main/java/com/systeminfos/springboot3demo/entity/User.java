package com.systeminfos.springboot3demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zhangqi
 * @version 1.0
 * @desc
 * @date 2024-12-12 11:24:24
 */
@Data
@TableName("user")
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("")
    private Long id;
    @ApiModelProperty("")
    private String username;
    @ApiModelProperty("")
    private String password;
    @ApiModelProperty("")
    private String name;
    @ApiModelProperty("")
    private String status;
}