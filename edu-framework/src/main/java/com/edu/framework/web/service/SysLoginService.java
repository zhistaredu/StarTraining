package com.edu.framework.web.service;

import javax.annotation.Resource;

import com.edu.common.constant.UserConstants;
import com.edu.common.exception.user.*;
import com.edu.common.utils.StringUtils;
import com.edu.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.edu.common.constant.Constants;
import com.edu.common.core.domain.model.LoginUser;
import com.edu.common.core.redis.RedisCache;
import com.edu.common.exception.ServiceException;
import com.edu.common.utils.MessageUtils;
import com.edu.common.utils.ip.IpUtils;
import com.edu.framework.manager.AsyncManager;
import com.edu.framework.manager.factory.AsyncFactory;
import com.edu.framework.security.context.AuthenticationContextHolder;

import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Random;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录校验方法
 * 
 * @author edu
 */
@Component
public class SysLoginService
{

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private UserLoginService userLoginService;

    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public Map<String,String> login(String username, String password)
    {
        // 登录前置校验
        loginPreCheck(username, password);
        // 用户验证
        Authentication authentication = null;
        try
        {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "corpId","web",Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "corpId","web", Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        finally
        {
            AuthenticationContextHolder.clearContext();
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "corpId","web", Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id",loginUser.getUserId());
        claims.put("company_id",loginUser.getCompanyId());
        String token =  userLoginService.createToken(claims);

        Map<String,String> result = new HashMap<>();
        result.put("userStatus","ok");
        result.put("token",token);
        result.put("companyId",loginUser.getCompanyId());
        result.put("userId",loginUser.getUserId());
        result.put("userName", loginUser.getUser().getUserName());
        result.put("avatar",loginUser.getUser().getAvatar());
        return result;
    }

    /**
     * 登录前置校验
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginPreCheck(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "corpId","web", Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "corpId","web", Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "corpId","web", Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // IP黑名单校验
        String blackStr = configService.selectConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "corpId","web", Constants.LOGIN_FAIL, MessageUtils.message("login.blocked")));
            throw new BlackListException();
        }
    }

    /**
     * 计算dd.config的签名参数
     *
     * @param jsticket  通过微应用appKey获取的jsticket
     * @param nonceStr  自定义固定字符串
     * @param timeStamp 当前时间戳
     * @param url       调用dd.config的当前页面URL
     * @return
     * @throws Exception
     */
    public static String sign(String jsticket, String nonceStr, long timeStamp, String url) throws Exception {
        String plain = "jsapi_ticket=" + jsticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
                + "&url=" + decodeUrl(url);
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-256");
            sha1.reset();
            sha1.update(plain.getBytes("UTF-8"));
            return byteToHex(sha1.digest());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // 字节数组转化成十六进制字符串
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 因为ios端上传递的url是encode过的，android是原始的url。开发者使用的也是原始url,
     * 所以需要把参数进行一般urlDecode
     *
     * @param url
     * @return
     * @throws Exception
     */
    private static String decodeUrl(String url) throws Exception {
        URL urler = new URL(url);
        StringBuilder urlBuffer = new StringBuilder();
        urlBuffer.append(urler.getProtocol());
        urlBuffer.append(":");
        if (urler.getAuthority() != null && urler.getAuthority().length() > 0) {
            urlBuffer.append("//");
            urlBuffer.append(urler.getAuthority());
        }
        if (urler.getPath() != null) {
            urlBuffer.append(urler.getPath());
        }
        if (urler.getQuery() != null) {
            urlBuffer.append('?');
            urlBuffer.append(URLDecoder.decode(urler.getQuery(), "utf-8"));
        }
        return urlBuffer.toString();
    }

    public static String getRandomStr(int count) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 校验验证码
     * 
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor("1","2",username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor("1","2",username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }
}
