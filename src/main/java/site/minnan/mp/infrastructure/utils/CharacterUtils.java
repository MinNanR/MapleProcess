package site.minnan.mp.infrastructure.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
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

        log.info("开始查询角色：{}", characterName);

//        HttpRequest request = HttpUtil.createGet(queryUrl);
//        request.cookie("cf_chl_2=ab7e9f4f7e40552; cf_chl_prog=x13; cf_clearance=GRwjxxh1JvKVj940PoXpjuGRAbfxUNsuDn" +
//                ".9zy3TT94-1651716370-0-150");
//        request.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
//                "Chrome/96.0.4664.55 Safari/537.36 Edg/96.0.1054.41");
//        HttpResponse execute = request.execute();
//        byte[] responseBytes = execute.bodyBytes();

        byte[] responseBytes = HttpUtil.get(queryUrl).getBytes(StandardCharsets.UTF_8);

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
