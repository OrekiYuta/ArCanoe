package cn.orekiyuta.ark.schedule;

import cn.orekiyuta.ark.cache.HotTagCache;
import cn.orekiyuta.ark.mapper.QuestionMapper;
import cn.orekiyuta.ark.model.Question;
import cn.orekiyuta.ark.model.QuestionExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by orekiyuta on  2019/11/23 - 17:04
 **/
@Component

public class HotTagTasks {

//    private static final Logger log = LoggerFactory.getLogger(HotTagTasks.class);
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

//    @Scheduled(fixedRate = 1000 * 60 * 60)
    @Scheduled(fixedRate = 3)
//    @Scheduled(cron = "0 0 6,19 * * *")
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 5 ;
//        log.info("The time is start {}", dateFormat.format(new Date()));
        List<Question> list = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
        while (offset == 0 || list.size() == limit){
            list=questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,limit));
            for (Question question : list) {

                String[] tags = StringUtils.split(question.getTag(),",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null){
                        priorities.put(tag,priority + 5 + question.getCommentCount());
                    }else {
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }

//        priorities.forEach(
//                (k, v) ->{
//                    System.out.print(k);
//                    System.out.print(":");
//                    System.out.print(v);
//                    System.out.println();
//                }
//        );

        hotTagCache.updateTags(priorities);

//        log.info("The time is stop {}", dateFormat.format(new Date()));
    }

}
