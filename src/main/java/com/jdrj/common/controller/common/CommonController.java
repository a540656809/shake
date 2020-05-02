package com.jdrj.common.controller.common;

import com.jdrj.common.utils.R;
import com.jdrj.socket.controller.MessageController;
import com.corundumstudio.socketio.SocketIOServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;


@Controller
public class CommonController {

    @Autowired
    private SocketIOServer server;


    //提交名单
    @ResponseBody
    @RequestMapping(value = "sure", method = RequestMethod.POST)
    public R sure(@RequestParam String name){
        if(StringUtils.isEmpty(name)){
            return R.error("姓名不能为空");
        }
        if(name.length() > 5 ){
            return R.error("名字太长了");
        }
        if(MessageController.map.containsKey(name)){
            return R.error("该姓名已存在");
        }
        return R.ok();
    }

    //广播游戏开始
    @ResponseBody
    @RequestMapping(value = "start", method = RequestMethod.GET)
    public R start(){
        if(MessageController.status != 0){
            return R.error();
        }
        MessageController.status = 1;
        server.getBroadcastOperations().sendEvent("start", true);
        return R.ok();
    }

    //广播游戏结束
    @ResponseBody
    @RequestMapping(value = "end", method = RequestMethod.GET)
    public R end(){
        if(MessageController.status != 1){
            return R.error();
        }
        MessageController.status = 2;
        server.getBroadcastOperations().sendEvent("end", true);
        return R.ok();
    }

    //广播游戏开始
    @ResponseBody
    @RequestMapping(value = "clear", method = RequestMethod.GET)
    public R clear(){
        //遍历map
        if(MessageController.status != 2){
            return R.error();
        }
        MessageController.status = 0;
        Set<String> strings = MessageController.map.keySet();
        for ( String key : strings) {
            MessageController.map.put(key, 0);
        }
        server.getBroadcastOperations().sendEvent("clear", true);
        return R.ok();
    }

    @RequestMapping(value = "shake", method = RequestMethod.GET)
    public String shake(){
        return "shake";
    }
}
