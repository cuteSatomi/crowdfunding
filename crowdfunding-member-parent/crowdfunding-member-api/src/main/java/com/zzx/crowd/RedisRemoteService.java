package com.zzx.crowd;

import com.zzx.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * @author zzx
 * @date 2021-01-14 11:59:55
 */
@Component
@FeignClient("crowd-redis")
public interface RedisRemoteService {

    /**
     * 向redis中设置键和值的远程接口
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(
            @RequestParam("key") String key,
            @RequestParam("value") String value);

    /**
     * 向redis中设置带过期时间的键和值的远程接口
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     * @return
     */
    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeout(
            @RequestParam("key") String key,
            @RequestParam("value") String value,
            @RequestParam("time") long time,
            @RequestParam("timeUnit") TimeUnit timeUnit);

    /**
     * 根据string类型的key来获取值的远程接口
     * @param key
     * @return
     */
    @RequestMapping("/get/redis/string/value/by/key/remote")
    ResultEntity<String> getRedisStringValueByKeyRemote(@RequestParam("key")String key);

    /**
     * 根据所给的key删除redis缓存的远程接口
     * @param key
     * @return
     */
    @RequestMapping("/remove/redis/key/remote")
    ResultEntity<String> removeRedisKeyRemote(@RequestParam("key")String key);
}
