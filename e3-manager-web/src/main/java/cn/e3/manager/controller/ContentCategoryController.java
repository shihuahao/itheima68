package cn.e3.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.content.service.ContentCategoryService;
import cn.e3.utils.TreeNode;

@Controller
public class ContentCategoryController {

	//注入广告service远程服务对象
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * 需求：根据父id查询节点下面的子节点
	 * 请求：/content/category/list
	 * 参数：Long parentId
	 * 返回值：json格式List<treeNode>
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<TreeNode> findContentCategoryTreeNodeList(@RequestParam(value="id",defaultValue="0") Long parentId){
		//调用service远程服务方法
		List<TreeNode> list = contentCategoryService.findContentCategoryTreeNodeList(parentId);
		return list;
	}
}
