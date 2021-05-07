package org.ksl.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Keelahselai
 * @date 2021/05/07
 * description 指数类
 */
@Data
public class Index implements Serializable {

    private static final long serialVersionUID = -4549799188274866574L;

    private String name;
    private String code;
}
