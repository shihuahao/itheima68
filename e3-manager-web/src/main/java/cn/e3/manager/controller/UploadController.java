package cn.e3.manager.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3.utils.FastDFSClient;
import cn.e3.utils.JsonUtils;
import cn.e3.utils.KindEditorModel;

@Controller
public class UploadController {
	
	//注入图片服务器地址
	@Value("${IMAGE_URL}")
	private String IMAGE_URL;

	/**
	 * 需求：使用fastDFS分布式文件系统上传图片
	 * 请求：/pic/upload
	 * 参数：uploadFile
	 * 返回值：
	 * 成功时：
	 * {
        "error" : 0,
        "url" : "http://www.example.com/path/to/file.ext",
        "message":null
	   }
	 * 失败时：
	 * {
        "error" : 1,
        "url" : null,
        "message" : "上传失败"
	   }
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadPic(MultipartFile uploadFile){
		
		try {
			//获取上传文件名称
			String originalFilename = uploadFile.getOriginalFilename();
			//获取文件扩展名
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			
			//创建fastDFS工具类对象，实现文件上传
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			//上传
			//返回图片虚拟地址
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
			
			//组合图片服务器绝对地址
			url = IMAGE_URL+url;
			
			//上传图片成功
			//创建KindEditorModel对象，封装图片上传信息
			KindEditorModel model = new KindEditorModel();
			model.setError(0);
			model.setUrl(url);
			
			//转换json字符串
			String picJson = JsonUtils.objectToJson(model);

			return picJson;
			
		} catch (Exception e) {
			e.printStackTrace();
			//上传图片失败
			KindEditorModel model = new KindEditorModel();
			model.setError(1);
			model.setMessage("上传失败");
			//转换json字符串
			String picJson = JsonUtils.objectToJson(model);

			return picJson;
		}
		
	}
}
