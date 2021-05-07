package org.ksl.service;

import org.ksl.pojo.Index;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Keelahselai
 * @date 2021/05/07
 * description 指数服务组件
 *
 */
@Service
public class IndexService {

    @Resource
    private RestTemplate restTemplate;

    /**
     * 从第三方获取指数数据
     * @return 转换后的实体列表
     */
    public List<Index> fetchIndexFromThirdPart() {
        List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/codes.json",List.class);
        return convertMapToIndex(temp);
    }

    /**
     * 将第三方原始数据转成index实体
     * @param temp 第三方原始数据
     * @return 转换后的实体列表
     */
    private List<Index> convertMapToIndex(List<Map> temp) {
        List<Index> indexList = new ArrayList<>();
        for (Map map : temp) {
            String code = map.get("code").toString();
            String name = map.get("name").toString();
            Index index = new Index();
            index.setCode(code);
            index.setName(name);
            indexList.add(index);
        }

        return indexList;
    }

}
