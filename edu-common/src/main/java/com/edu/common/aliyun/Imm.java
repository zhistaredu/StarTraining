package com.edu.common.aliyun;

import com.aliyun.tea.TeaException;

public class Imm {
    private static String accessKeyId = "";
    private static String accessKeySecret = "";

    /**
     * 使用AK&SK初始化账号Client
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.imm20200930.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "";
        return new com.aliyun.imm20200930.Client(config);
    }

    public void createOfficeConversionTask(String projectName,String sourceUri,String targetType) throws Exception {
        com.aliyun.imm20200930.Client client = createClient();
        com.aliyun.imm20200930.models.CreateOfficeConversionTaskRequest createOfficeConversionTaskRequest = new com.aliyun.imm20200930.models.CreateOfficeConversionTaskRequest()
                .setProjectName(projectName)
                .setSourceURI(sourceUri)
                .setTargetType(targetType);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            client.createOfficeConversionTaskWithOptions(createOfficeConversionTaskRequest, runtime);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }
}
