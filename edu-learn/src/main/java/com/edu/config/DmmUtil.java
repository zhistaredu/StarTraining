package com.edu.config;

import com.aliyun.imm20200930.models.GenerateWebofficeTokenResponse;
import com.aliyun.imm20200930.models.RefreshWebofficeTokenResponse;
import com.aliyun.tea.TeaException;
import com.edu.common.config.ImmConfig;
import com.edu.common.config.OssConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DmmUtil {

    @Autowired
    private ImmConfig immConfig;

    @Autowired
    private OssConfig ossConfig;

    private com.aliyun.imm20200930.Client immClient;

    /**
     * 使用AK&SK初始化账号Client
     * @return Client
     * @throws Exception
     */
    public void createImmClient() throws Exception {
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();

        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/imm
        config.endpoint = "imm.cn-hangzhou.aliyuncs.com";
        immClient =  new com.aliyun.imm20200930.Client(config);
    }

    public Map<String,Object> getWebOfficeToken(String object, String userName) {
        String sourceURI = "oss://"+ ossConfig.getBucket() +"/" + object;
        com.aliyun.imm20200930.models.WebofficePermission webofficePermission = new com.aliyun.imm20200930.models.WebofficePermission()
                .setReadonly(true);
        com.aliyun.imm20200930.models.WebofficeUser webofficeUser = new com.aliyun.imm20200930.models.WebofficeUser()
                .setName(userName);
        com.aliyun.imm20200930.models.GenerateWebofficeTokenRequest generateWebofficeTokenRequest = new com.aliyun.imm20200930.models.GenerateWebofficeTokenRequest()
                .setProjectName(immConfig.getProjectName())
                .setSourceURI(sourceURI)
                .setFilename(object)
                .setUser(webofficeUser)
                .setPermission(webofficePermission);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        Map<String,Object> result = new HashMap<>();
        try {
            // 复制代码运行请自行打印 API 的返回值
            GenerateWebofficeTokenResponse respond = immClient.generateWebofficeTokenWithOptions(generateWebofficeTokenRequest, runtime);
            result.put("accessToken",respond.body.accessToken);
            result.put("refreshToken",respond.body.refreshToken);
            result.put("accessTokenExpiredTime",respond.body.accessTokenExpiredTime);
            result.put("refreshTokenExpiredTime",respond.body.refreshTokenExpiredTime);
            result.put("requestId",respond.body.requestId);
            result.put("webofficeURL",respond.body.webofficeURL);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return result;
    }

    public Map<String,Object> refreshWebofficeToken(String accessToken, String refreshToken) {
        com.aliyun.imm20200930.models.RefreshWebofficeTokenRequest refreshWebofficeTokenRequest = new com.aliyun.imm20200930.models.RefreshWebofficeTokenRequest()
                .setProjectName(immConfig.getProjectName())
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        Map<String,Object> result = new HashMap<>();
        try {
            // 复制代码运行请自行打印 API 的返回值
            RefreshWebofficeTokenResponse respond = immClient.refreshWebofficeTokenWithOptions(refreshWebofficeTokenRequest, runtime);
            result.put("accessToken",respond.body.accessToken);
            result.put("refreshToken",respond.body.refreshToken);
            result.put("accessTokenExpiredTime",respond.body.accessTokenExpiredTime);
            result.put("requestId",respond.body.requestId);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return result;
    }
}
