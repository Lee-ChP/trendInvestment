package org.ksl.service;

import cn.hutool.core.collection.CollectionUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.ksl.pojo.Index;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "indices")
public class IndexService {

    @Resource
    private RestTemplate restTemplate;

    /**
     * 从第三方获取指数数据
     * @return 转换后的实体列表
     *
     * HystrixProperty: 超时配置，否则默认为1s
     */
    @HystrixCommand(fallbackMethod = "third_part_not_connected",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    @Cacheable(key = "'all_codes'")
    public List<Index> fetchIndexFromThirdPart() {
        List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/codes.json", List.class);
        return convertMapToIndex(temp);
    }

    public List<Index> third_part_not_connected() {
        log.error("third_part_not_connected()");
        Index index = new Index();
        index.setCode("000000");
        index.setName("无效指数代码");
        return CollectionUtil.toList(index);
    }

    /**
     * 将第三方原始数据转成index实体
     *
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
