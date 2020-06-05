package com.asura.enxin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表; InnoDB free: 6144 kB
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户姓名--例如张三
     */
    private String username;

    /**
     * 登录用户名
     */
    private String loginName;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 职位
     */
    private String position;

    /**
     * 所属部门
     */
    private String department;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenum;

    /**
     * 是否为管理者 0==管理者 1==员工
     */
    private Integer ismanager;

    /**
     * 是否系统自带数据 
     */
    private Integer isystem;

    /**
     * 状态，0：正常，1：删除，2封禁
     */
    @TableField("Status")
    private Integer Status;

    /**
     * 用户描述信息
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户id
     */
    private Long tenantId;


}
