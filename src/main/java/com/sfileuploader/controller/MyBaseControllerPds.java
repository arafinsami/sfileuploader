package com.sfileuploader.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class MyBaseControllerPds {

	@Autowired
	HttpServletRequest httpServletRequest;

	@Value("${pdsUploadPath}")
	String pdsUploadPath;

	protected String uploadFile(MultipartFile file) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
		String date = dateFormat.format(new Date());
		String name = date + file.getOriginalFilename().replace(' ', '-');
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				File dir = new File(pdsUploadPath + "/");
				if (!dir.exists())
					dir.mkdirs();
				File serverFile = new File(dir + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				return name;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
}
