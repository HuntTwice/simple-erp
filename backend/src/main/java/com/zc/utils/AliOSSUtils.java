package com.zc.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@Data
public class AliOSSUtils {

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    @Value("${simple-erp.aliyun.OSS.endpoint}")
    private String endpoint;

    // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
    @Value("${simple-erp.aliyun.OSS.accessKeyId}")
    private String accessKeyId;

    @Value("${simple-erp.aliyun.OSS.accessKeySecret}")
    private String accessKeySecret;

    // 填写Bucket名称，例如examplebucket。
    @Value("${simple-erp.aliyun.OSS.bucketName}")
    private String bucketName;


    public String upload(MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();

        //避免文件覆盖
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //新的文件名
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//https://qmx111.oss-cn-hangzhou.aliyuncs.com/3a4ce8b9-1b9c-4bd2-9c6c-15077c9c6636.jpg
        String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
        try {
//            // 填写字符串。
//            String content = "Hello OSS，你好世界";

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
//            String url = endpoint.split("//")[0]+"//"+bucketName+"."+endpoint.split("//")[1]+"/"
//            result.getResponse().getUri();
            inputStream.close();


        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }

}
