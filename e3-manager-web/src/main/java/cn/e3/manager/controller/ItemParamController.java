package cn.e3.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.manager.service.ItemParamService;
import cn.e3.utils.DatagridPagebean;
import cn.e3.utils.E3mallResult;

@Controller
public class ItemParamController {
	
	//注入service服务对象
	@Autowired
	private ItemParamService itemParamService;

	/**
	 * 需求:分页查询商品规格列表
	 * 请求：/item/param/list
	 * 参数:Integer page,Integer rows
	 * 返回值:DatagridPagebean
	 */
	@RequestMapping("/item/param/list")
	@ResponseBody
	public DatagridPagebean findItemParamListByPage(@RequestParam(defaultValue="1") Integer page, @RequestParam(defaultValue="30") Integer rows){
		
		//远程调用service分页方法
		DatagridPagebean pagebean = itemParamService.findItemParamListByPage(page, rows);
		
		return pagebean;
	}
	
	/**
	 * 需求：根据分类id查询商品的规格模板
	 * 请求：/item/param/query/itemcatid/{categoryId}
	 * 参数：Long dategoryId
	 * 返回值：E3MallResult.ok(ItemParam)
	 */
	@RequestMapping("/item/param/query/itemcatid/{dategoryId}")
	@ResponseBody
	public E3mallResult findItemParamByDategoryId(@PathVariable Long dategoryId){
		//调用service远程服务方法
		E3mallResult result = itemParamService.findItemParamByDategoryId(dategoryId);
		
		return result;
	}
}
