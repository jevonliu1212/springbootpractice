package com.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springboot.websocket.Message;
import com.springboot.websocket.Response;
@Service
public class WebSocketService {

	@Autowired
    private SimpMessagingTemplate template;
	
	public void sendMessage(Message message) throws Exception{
            Thread.sleep(1500);
            System.out.println("send==========="+JSON.toJSONString(message));
            template.convertAndSendToUser(message.getUser(), "/queue/notifications", message.getName());
    }
}
