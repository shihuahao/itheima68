package cn.e3.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3.manager.service.ItemService;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;

@Controller
public class ItemDetailController {
	
	//注入商品服务对象
	@Autowired
	private ItemService itemService;

	/**
	 * 需求：进入商品详情页面
	 * 请求：{itemId}
	 * 业务：
	 * 商品详情页面需要什么数据
	 * 1，商品数据
	 * 2，商品描述
	 * 3，商品规格
	 */
	@RequestMapping("{itemId}")
	public String showItem(@PathVariable Long itemId, Model model){
		//根据id查询商品数据
		TbItem item = itemService.findItemByID(itemId);
		//根据Id查询商品描述数据
		TbItemDesc itemDesc = itemService.findItemDescById(itemId);
		
		model.addAttribute("item", item);
		
		model.addAttribute("itemDesc", itemDesc);
		
		return "item";
	}
}
