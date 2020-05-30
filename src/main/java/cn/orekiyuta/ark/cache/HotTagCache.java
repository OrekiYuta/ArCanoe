package cn.orekiyuta.ark.cache;

import cn.orekiyuta.ark.dto.HotTagDTO;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by orekiyuta on  2019/11/23 - 17:59
 **/

@Component
public class HotTagCache {

    private List<String> hots =new ArrayList<>();
    private List<Integer> hotP =new ArrayList<>();

    public void updateTags(Map<String,Integer> tags){
        int max=20;
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max); //优先队列

        tags.forEach((name,priority)->{
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if (priorityQueue.size() < max){
                priorityQueue.add(hotTagDTO);
            }else {
                HotTagDTO minHot = priorityQueue.peek();
                if (hotTagDTO.compareTo(minHot) > 0){
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });

        List<String> sortedTags = new ArrayList<>();
        List<Integer> sortedPriority = new ArrayList<>();//-A

        HotTagDTO poll = priorityQueue.poll();
//        hots.add(poll.getName());
        while (poll != null){
            sortedTags.add(0,poll.getName());
            sortedPriority.add(0,poll.getPriority());//-A
            poll = priorityQueue.poll();
        }
        hots = sortedTags;
        hotP = sortedPriority;
    }

    public List<String> getHots() {
        return hots;
    }

    public List<Integer> getHotP(){
        return hotP;
    }

    public void setHots(List<String> hots) {
        this.hots = hots;
    }
}
