package com.yida.project.serviceImpl;

import com.yida.project.entity.Message;
import com.yida.project.mapper.MessageMapper;
import com.yida.project.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
    @Autowired(required = false)
    private MessageMapper messageMapper;

    @Override
    public void add(Message me) {
        messageMapper.inser(me);
    }

    @Override
    public List<Message> selectall() {
        return messageMapper.selectAll();
    }

    @Override
    public Message selecton(Integer id) {
        return messageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateon(Message me) {
        messageMapper.updateByPrimaryKeySelective(me);
    }

    @Override
    public void delete(Integer id) {
        messageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Message> meselect(Integer focue) {
        return messageMapper.selectme(focue);
    }

    @Override
    public List<Message> selectallto(String title, String begin, String end) {
        return messageMapper.selectallto(title, begin, end);
    }

    @Override
    public List<Message> selectallto2(String title, String begin, String end) {
        return messageMapper.selectallto2(title, begin, end);
    }


}
