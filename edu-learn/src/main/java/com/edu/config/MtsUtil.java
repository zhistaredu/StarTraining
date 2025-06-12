package com.edu.config;

import com.alibaba.fastjson2.JSON;
import com.aliyun.mts20140618.models.QueryJobListResponse;
import com.aliyun.mts20140618.models.SubmitJobsResponse;
import com.aliyun.tea.TeaException;
import com.edu.common.config.MtsConfig;
import com.edu.common.config.OssConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MtsUtil {

    @Autowired
    private MtsConfig mtsConfig;

    @Autowired
    private OssConfig ossConfig;

    private com.aliyun.mts20140618.Client mtsClient;

    /**
     * 使用AK&SK初始化账号Client
     * @return Client
     * @throws Exception
     */
    public void createMtsClient() throws Exception {
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Mts
        config.endpoint = "mts.cn-hangzhou.aliyuncs.com";
        mtsClient =  new com.aliyun.mts20140618.Client(config);
    }

    public String submitTransCodeJob(String object,String outputObject,String type) {
        String jobId = "";
        String templateId = mtsConfig.getVideoTemplateId();
        if (type.equals("audio")){
            templateId = mtsConfig.getAudioTemplateId();
        }
        Map<String,String> input = new HashMap<>();
        input.put("Bucket",ossConfig.getBucket());
        input.put("Location",mtsConfig.getOssRegion());
        input.put("Object",object);
        Map<String,String> output = new HashMap<>();
        output.put("OutputObject",outputObject);
        output.put("TemplateId", templateId);
        List<Object> outputs = new ArrayList<>();
        outputs.add(output);
        com.aliyun.mts20140618.models.SubmitJobsRequest submitJobsRequest = new com.aliyun.mts20140618.models.SubmitJobsRequest()
                .setInput(JSON.toJSONString(input))
                .setOutputs(JSON.toJSONString(outputs))
                .setPipelineId(mtsConfig.getPipelineId())
                .setOutputBucket(ossConfig.getBucket());
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            SubmitJobsResponse result = mtsClient.submitJobsWithOptions(submitJobsRequest, runtime);
            jobId = result.body.jobResultList.jobResult.get(0).job.jobId;
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return jobId;
    }

    public Map<String,Object> queryJobList(String jobIds) {
        com.aliyun.mts20140618.models.QueryJobListRequest queryJobListRequest = new com.aliyun.mts20140618.models.QueryJobListRequest()
                .setJobIds(jobIds);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        String transStatus = "";
        Long percent = 0L;
        String duration = "0";
        try {
            // 复制代码运行请自行打印 API 的返回值
            QueryJobListResponse result = mtsClient.queryJobListWithOptions(queryJobListRequest, runtime);
            transStatus = result.body.jobList.job.get(0).state;
            percent = result.body.jobList.job.get(0).percent;
            duration = result.body.jobList.job.get(0).output.properties.duration;
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        Map<String,Object> response = new HashMap<>();
        response.put("percent",percent);
        response.put("duration",duration);
        response.put("transStatus",transStatus);
        return response;
    }
}
