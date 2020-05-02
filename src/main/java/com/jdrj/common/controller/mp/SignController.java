package com.jdrj.common.controller.mp;

import com.jdrj.common.utils.R;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/mp/sign")
public class SignController {
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private WxMpService wxMpService;


    //服务器配置 微信公众号验签专用
    @RequestMapping("check")
    public void check(ServletResponse response, String timestamp, String nonce, String signature, String echostr) {
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.error("不合法");
            throw new RuntimeException("微信验签失败");
        }
        PrintWriter o = null;
        try {
            o = new PrintWriter(response.getWriter());
            o.print(echostr);
        } catch (IOException e) {
            log.error("写回微信端错误{}", e.getMessage());
        } finally {
            o.close();
        }
    }

    /**
     * 构建js-sdk所需配置
     * @param url
     * @return
     */
    @RequestMapping(value = "url", method = RequestMethod.GET)
    public R getSign(@RequestParam String url) {
        try {
            return R.ok().put("sign",wxMpService.createJsapiSignature(url));
        } catch (WxErrorException e) {
            log.error(e.toString());
            return R.error(e.toString());
        }
    }

}
