package cn.e3.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

@RestController
public class FmController {

	//注入freemarker模板核心对象
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	/**
	 * 需求：测试freemarker使用
	 * @throws Exception 
	 */
	@RequestMapping("/fm/{name}")
	public String showFm(Model model, @PathVariable String name) throws Exception{
		//从核心配置对象中获取freemarker配置对象
		Configuration cf = freeMarkerConfigurer.getConfiguration();
		//直接读取服务器模板文件
		Template template = cf.getTemplate("hello.ftl");
		model.addAttribute("hello", name);
		//创建输出流对象
		Writer out = new FileWriter(new File("E:\\develops\\template\\out\\hello.html"));
		//生成html
		template.process(model, out);
		
		return "success";
	}
}
