//package com.bootdo.common.config;
//
//import me.chanjar.weixin.common.bean.menu.WxMenu;
//import me.chanjar.weixin.common.bean.menu.WxMenuButton;
//import me.chanjar.weixin.common.error.WxErrorException;
//import me.chanjar.weixin.mp.api.WxMpConfigStorage;
//import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
//
//import static me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
//
///**
// * 微信公众号菜单配置
// */
//public class MenuConfig {
//
//    /**
//     * 定义菜单结构
//     *
//     * @return
//     */
//    protected static WxMenu getMenu() {
//        MainConfig mainConfig = new MainConfig("wx2ecebcda3a0b1938", "017bad7964ddc755c7bafa74e6e719ff", "kkjoe", "aesKey");
//
//        WxMpService wxMpService = mainConfig.wxMpService();
//
//        WxMenu menu = new WxMenu();
//        WxMenuButton button2 = new WxMenuButton();
//        button2.setName("顶层菜单");
//        button2.setType(MenuButtonType.CLICK);
//
//        WxMenuButton button21 = new WxMenuButton();
//        button21.setType(MenuButtonType.VIEW);
//        button21.setName("跳转测试");
//        String jumpUrl = "http://3d776b8a.ngrok.io/shake";
//        button21.setUrl(wxMpService.oauth2buildAuthorizationUrl(jumpUrl, "snsapi_userinfo", ""));
//
//
//        button2.getSubButtons().add(button21);
//        menu.getButtons().add(button2);
//
//        return menu;
//    }
//
//
//    public static class MainConfig {
//
//        private String appId;
//        private String appSecret;
//        private String token;
//        private String aesKey;
//
//        /**
//         * 为了生成自定义菜单使用的构造函数
//         *
//         * @param appId
//         * @param appSecret
//         * @param token
//         * @param aesKey
//         */
//        protected MainConfig(String appId, String appSecret, String token, String aesKey) {
//            this.appId = appId;
//            this.appSecret = appSecret;
//            this.token = token;
//            this.aesKey = aesKey;
//        }
//
//        public WxMpConfigStorage wxMpConfigStorage() {
//            WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
//            configStorage.setAppId(this.appId);
//            configStorage.setSecret(this.appSecret);
//            configStorage.setToken(this.token);
//            configStorage.setAesKey(this.aesKey);
//            return configStorage;
//        }
//
//        public WxMpService wxMpService() {
//            WxMpService wxMpService = new WxMpServiceImpl();
//            wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
//            return wxMpService;
//        }
//
//    }
//
//    /**
//     * 运行此main函数即可生成公众号自定义菜单，注意要修改MainConfig构造方法行代码的对应四个参数为实际值
//     *
//     * @param args
//     */
//    public static void main(String[] args) {
//        MainConfig mainConfig = new MainConfig("wx2ecebcda3a0b1938", "017bad7964ddc755c7bafa74e6e719ff", "kkjoe", "aesKey");
//        WxMpService wxMpService = mainConfig.wxMpService();
//        try {
//            wxMpService.getMenuService().menuCreate(getMenu());
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
