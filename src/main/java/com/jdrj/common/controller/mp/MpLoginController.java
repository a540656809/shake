package com.jdrj.common.controller.mp;

import com.jdrj.common.utils.R;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公众号token登录授权
 *
 * @author kkjoe
 */
@RestController
@RequestMapping("/app")
public class MpLoginController {
    @Autowired
    private WxMpService wxMpService;
    /**
     * 微信公众号 用户登录 记录用户数据
     */
    @PostMapping("/login")
    public R login(@RequestParam String code, String lang) throws WxErrorException {
        //1.用户传code过来 根据code 获取用户openId
        //2.查找openId 是否存在于UserWechat表中 存在则生成token信息   不在则绑定账号 并且生产token信息
        WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpService.oauth2getAccessToken(code),lang);
        if(null == wxMpUser){
            return R.error("请使用微信浏览器登陆");
        }

        return R.ok(wxMpUser.toString());
    }


}
