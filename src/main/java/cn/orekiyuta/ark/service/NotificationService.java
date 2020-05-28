package cn.orekiyuta.ark.service;

import cn.orekiyuta.ark.dto.NotificationDTO;
import cn.orekiyuta.ark.dto.PaginationDTO;
import cn.orekiyuta.ark.dto.QuestionDTO;
import cn.orekiyuta.ark.enums.NotificationStatusEnum;
import cn.orekiyuta.ark.enums.NotificationTypeEnum;
import cn.orekiyuta.ark.exception.CustomizeErrorCode;
import cn.orekiyuta.ark.exception.CustomizeException;
import cn.orekiyuta.ark.mapper.NotificationMapper;
import cn.orekiyuta.ark.mapper.UserMapper;
import cn.orekiyuta.ark.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by orekiyuta on  2019/11/8 - 17:35
 **/
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        Integer totalPage;

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);

        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);

        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1 ;
        }

        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset=size * (page -1);

        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        if(notifications.size() == 0){
            return paginationDTO;
        }
//        Set<Long> disUserIds = notifications.stream().map(notification -> notification.getNotifier()).collect(Collectors.toSet());
//        ArrayList<Long> userIds = new ArrayList<>(disUserIds);
//        UserExample userExample = new UserExample();
//        userExample.createCriteria()
//                .andIdIn(userIds);
//        List<User> users = userMapper.selectByExample(userExample);
//        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));

        List<NotificationDTO> notificationDTOS =new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfTyoe(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        paginationDTO.setData((notificationDTOS));
        return paginationDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return  notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())){
                throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfTyoe(notification.getType()));

        return notificationDTO;
    }

    public void deleteByQuestionId(Long id) {
        notificationMapper.deleteByQuestionId(id);

    }
}
