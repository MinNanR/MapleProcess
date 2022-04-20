package site.minnan.mp.infrastructure.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import site.minnan.mp.domain.entity.CharacterInfo;
import site.minnan.mp.infrastructure.exception.EntityNotExistException;

import java.nio.charset.StandardCharsets;

/**
 * 角色工具类
 *
 * @author Minnan on 2022/04/15
 */
@Component
@Slf4j
public class CharacterUtils {


    private static final String QUERY_BY_NAME_BASE_URL = "https://api.maplestory.gg/v2/public/character/gms/";

    @Cacheable(cacheNames = "characterInfo", key = "#characterName", cacheManager = "cache")
    public CharacterInfo queryCharacterInfo(String characterName) {

        String queryUrl = QUERY_BY_NAME_BASE_URL + characterName;

        byte[] responseBytes = HttpUtil.get(queryUrl).getBytes(StandardCharsets.UTF_8);

        log.info("开始查询角色：{}", characterName);
        if (responseBytes.length == 0) {
            log.warn("查询失败，查询角色：{}", characterName);
            throw new EntityNotExistException("角色不存在");
        }

        JSONObject responseJson = JSONUtil.parseObj(new String(responseBytes));
        JSONObject characterData = responseJson.getJSONObject("CharacterData");
        if (characterData == null) {
            log.warn("查询失败");
            throw new EntityNotExistException("角色不存在");
        }

        return new CharacterInfo(characterData);
    }
}
