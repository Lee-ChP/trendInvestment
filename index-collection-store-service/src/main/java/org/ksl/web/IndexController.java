package org.ksl.web;

import org.ksl.pojo.Index;
import org.ksl.service.IndexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Keelahselai
 * @date 2021/05/07
 * description 控制类
 */
@RestController
public class IndexController {
    @Resource
    private IndexService indexService;

    @GetMapping("/getCodes")
    public List<Index> get() {
        return indexService.fetchIndexFromThirdPart();
    }
}
