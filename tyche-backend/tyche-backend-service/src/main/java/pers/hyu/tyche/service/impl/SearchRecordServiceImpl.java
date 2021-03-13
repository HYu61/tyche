package pers.hyu.tyche.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import pers.hyu.tyche.enums.RedisKeyEnum;
import pers.hyu.tyche.service.SearchRecordService;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Heng Yu
 * @version 1.0
 * @see pers.hyu.tyche.service.SearchRecordService
 */
@Service
public class SearchRecordServiceImpl implements SearchRecordService {
    @Value("${default-hotSearch.default_1}")
    private String defaultSearchOne;
    @Value("${default-hotSearch.default_2}")
    private String defaultSearchTwo;
    @Value("${default-hotSearch.default_3}")
    private String defaultSearchThree;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addSearchContent(String searchContent) {
        long timeout = (getExpireTimeInMillionSecond() - System.currentTimeMillis()); // set the expired timeout in million second.
        String key = RedisKeyEnum.HOT_SEARCH_KEY.getKey();

        stringRedisTemplate.watch(key);
        stringRedisTemplate.multi();
        stringRedisTemplate.boundZSetOps(key).incrementScore(searchContent, 1d);
        stringRedisTemplate.boundZSetOps(key).expire(timeout, TimeUnit.MILLISECONDS);
        stringRedisTemplate.exec();
    }

    @Override
    public Set<String> getHotSearch(Integer topNum) {

        // get the  hot search from the redis.
        String key = RedisKeyEnum.HOT_SEARCH_KEY.getKey();
        Set<String> hotSearchSet = stringRedisTemplate.boundZSetOps(key).reverseRange(0, topNum - 1);

        // records in the redis is less than the topNum, add the default search set.
       if(hotSearchSet.size() < topNum){
           hotSearchSet.addAll( Arrays.asList(new String[]{defaultSearchOne, defaultSearchTwo,defaultSearchThree}));
        }
        return hotSearchSet;
    }

    /**
     * Get the million second of the next day at 12:00 am.
     *
     * @return The million second to the next 12:00 am.
     */
    private long getExpireTimeInMillionSecond(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

}
