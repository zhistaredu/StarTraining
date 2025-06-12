package com.edu.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.edu.common.config.OssConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class OssUtil {

    @Autowired
    private OssConfig ossConfig;

    private OSS ossClient;

    public void ossClient(){
        ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(),ossConfig.getAccessKeyId(),ossConfig.getAccessKeySecret());
    }

    public Map<String,String> getSignature() throws ServletException, IOException {
        long expireTime = 600;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime);
        // 设置表单Map。
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, "");

        String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes("utf-8");
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);

        Map<String, String> formMap = new LinkedHashMap<String, String>();
        formMap.put("accessid", ossConfig.getAccessKeyId());
        formMap.put("policy", encodedPolicy);
        formMap.put("signature", postSignature);
        formMap.put("host", "https://"+ ossConfig.getBucket()+"."+ossConfig.getEndpoint());
        formMap.put("expire", String.valueOf(expireEndTime / 1000));
        return formMap;
    }

    /**
     * 上传字符串
     * @param objectName oss文件全路径名-默认是桶位置加全路径名，如桶位置为ossbucket，全路径名为upload/string/str.json，则数据会放到ossbucket下的upload/string包中，文件名为str.json
     * @param content 上传内容
     */
    public void uploadString(String objectName, String content){
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucket(), objectName, new ByteArrayInputStream(content.getBytes()));
        ossClient.putObject(putObjectRequest);
    }

    /**
     * 字节上传
     */
    public void uploadByte(byte[] imageBytes,String imageUrl){
        // 设置上传的文件元信息
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageBytes.length);
        metadata.setContentType("image/jpeg");

        // 上传文件到OSS
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucket(), imageUrl, new ByteArrayInputStream(imageBytes), metadata);
        ossClient.putObject(putObjectRequest);
    }

    /**
     * 上传文件
     * @param objectName 文件在oss中的全路径
     * @param filePath 上传文件的本地路径
     */
    public void uploadFile(String objectName, String filePath){
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucket(), objectName, new File(filePath));
        ossClient.putObject(putObjectRequest);
    }

    /**
     * 上传multi文件
     * @param objectName 文件全路径
     * @param multipartFile 上传的文件
     */
    public void uploadMultipartFile(String objectName, MultipartFile multipartFile){
        byte[] bytes = new byte[0];
        try {
            bytes = multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InputStream inputStream = new ByteArrayInputStream(bytes);
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucket(), objectName, inputStream);
        ossClient.putObject(putObjectRequest);
    }

    /**
     * 下载字符串数据
     * @param objectName 数据所在oss的全路径
     * @return 字符串
     * @throws IOException
     */
    public String downloadString(String objectName) throws IOException {
        OSSObject ossObject = ossClient.getObject(ossConfig.getBucket(), objectName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        StringBuilder stringBuilder = new StringBuilder();
        while (true){
            String line = bufferedReader.readLine();
            if(line==null){
                break;
            }else {
                stringBuilder.append(line);
            }
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    /**
     * 下载文件
     * @param objectName 数据所在oss全路径
     * @param fileName 文件名
     */
    public void downloadFile(String objectName, String fileName){
        ossClient.getObject(new GetObjectRequest(ossConfig.getBucket(), objectName),new File(fileName));
    }

    /**
     * 删除数据
     * @param objectName 删除文件的全路径
     */
    public void delete(String objectName){
        ossClient.deleteObject(ossConfig.getBucket(), objectName);
    }

    /**
     * 检查文件是否存在
     * @param objectName 文件所在oss中的全路径
     * @return 返回是否
     */
    public boolean checkExist(String objectName){
        boolean exist = ossClient.doesObjectExist(ossConfig.getBucket(), objectName);
        return exist;
    }
}