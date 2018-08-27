package com.springboot.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.springboot.websocket.Message;
import com.springboot.websocket.Response;

@Controller
public class WebSocketController {

	@MessageMapping("/welcome")//浏览器发送请求通过@messageMapping 映射/welcome 这个地址。
    @SendTo("/topic/getResponse")//服务器端有消息时,会订阅@SendTo 中的路径的浏览器发送消息。
    public Response say(Message message) throws Exception {
        Thread.sleep(1000);
        return new Response("Welcome, " + message.getName() + "!");
    }
	
	@SubscribeMapping("/topic/getResponse")
    public Response sub() {
		System.out.println("XXX用户订阅了我。。。");
        return new Response("感谢你订阅了我。。。");
    }
}
