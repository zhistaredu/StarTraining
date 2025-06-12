package com.edu.common.utils;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

@Configuration
public class RestUtils {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static JSONObject get(String url, Map<String,String> urlParams){
        return get(urlToUri(url,urlParams));
    }

    //在处理企业微信某些参数时有问题
    public static JSONObject get(String url){
        return get(URI.create(url));
    }

    private static JSONObject get(URI uri){
        ResponseEntity<JSONObject> responseEntity =restTemplate.getForEntity(uri,JSONObject.class);
        serverIsRight(responseEntity);   //判断服务器返回状态码
        return responseEntity.getBody();
    }

    public static JSONObject post(String url,Map<String,String> urlParams,JSONObject json){
        //组装url
        return post(urlToUri(url,urlParams),json);
    }

    public static JSONObject post(String url,JSONObject json){
        //组装urL
        return post(URI.create(url),json);
    }

    private static JSONObject post(URI uri,JSONObject json){
        //组装url
        //设置提交json格式数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> request = new HttpEntity(json, headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(uri,request,JSONObject.class);
        serverIsRight(responseEntity);  //判断服务器返回状态码
        return responseEntity.getBody();
    }

    private static URI urlToUri(String url,Map<String,String> urlParams){
        //设置提交json格式数据
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        for(Map.Entry<String,String> entry : urlParams.entrySet())  {
            uriBuilder.queryParam((String)entry.getKey(),  (String) entry.getValue()) ;
        }
        return  uriBuilder.build(true).toUri();
    }

    public static JSONObject upload(String url, MultiValueMap formParams){
        //设置表单提交
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formParams, headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url,request,JSONObject.class);
        serverIsRight(responseEntity);  //判断服务器返回状态码
        return responseEntity.getBody();
    }

    public static String download(String url,String targetPath) throws IOException {

        ResponseEntity<byte[]> rsp = restTemplate.getForEntity(url, byte[].class);
        if(rsp.getStatusCode() != HttpStatus.OK){
            System.out.println("文件下载请求结果状态码：" + rsp.getStatusCode());
        }
        // 将下载下来的文件内容保存到本地
        Files.write(Paths.get(targetPath), Objects.requireNonNull(rsp.getBody()));
        return targetPath;

    }

    public static byte[] dowload(String url){
        ResponseEntity<byte[]> rsp = restTemplate.getForEntity(url, byte[].class);
        return rsp.getBody();
    }

    private static void serverIsRight(ResponseEntity responseEntity){
        if(responseEntity.getStatusCodeValue()==200){
//            System.out.println("服务器请求成功：{}"+responseEntity.getStatusCodeValue());
        }else {
            System.out.println("服务器请求异常：{}"+responseEntity.getStatusCodeValue());
        }
    }


}
