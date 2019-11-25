package cn.orekiyuta.ark.service;

import cn.orekiyuta.ark.mapper.AdMapper;
import cn.orekiyuta.ark.model.Ad;
import cn.orekiyuta.ark.model.AdExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by orekiyuta on  2019/11/25 - 19:17
 **/
@Service
public class AdService {

    @Autowired
    private AdMapper adMapper;

    public List<Ad> list(String pos){
        AdExample navExample =new AdExample();
        navExample.createCriteria()
                .andStatusEqualTo(1)
                .andPosEqualTo(pos)
                .andGmtStartLessThan(System.currentTimeMillis())
                .andGmtEndGreaterThan(System.currentTimeMillis());
        return  adMapper.selectByExample(navExample);
    }
}
