package com.pinyougou.shop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import entity.Result;
import util.FastDFSClient;

@RestController
public class UploadController {
	
	//读取配置文件value的值
	@Value("${FILE_SERVER_URL}")
	private String file_sercer_url;
	@RequestMapping("/upload")
	public Result upload(MultipartFile file) {
		
		
		//获取文件名
		String originalFilename = file.getOriginalFilename();
		String substring = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
		
		try {
			util.FastDFSClient client = new FastDFSClient("classpath:config/fdfs_client.conf");
			String fileId = client.uploadFile(file.getBytes(),substring);
			
			String url= file_sercer_url+fileId;
			return new Result(true, url);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new Result(false, "上传失败");
		}
		
	}
}
