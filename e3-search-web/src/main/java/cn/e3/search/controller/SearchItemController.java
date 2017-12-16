package cn.e3.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3.search.pojo.SolrPage;
import cn.e3.search.service.SearchItemService;

@Controller
public class SearchItemController {
	//注入远程搜索服务
	@Autowired
	private SearchItemService searchItemService;

	/**
	 * 需求：接收参数，封装参数查询索引库 请求：http://localhost:8085/search.html?q= 参数：String
	 * qName, Integer page, Integer rows 返回值：search.jsp
	 */
	@RequestMapping("search")
	public String search(@RequestParam(value="q") String qName, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "60") Integer rows, Model model) {

		//乱码处理
		try {
			qName = new String(qName.getBytes("ISO8859-1"),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//调用远程service方法
		SolrPage solrPage = searchItemService.findSolrIndex(qName, page, rows);
		//回显主查询条件
		model.addAttribute("query", qName);
		//回显总页码
		model.addAttribute("totalPages", solrPage.getTotalPages());
		//回显当前页
		model.addAttribute("page", page);
		//回显商品记录
		model.addAttribute("itemList", solrPage.getItemList());
		
		return "search";
	}
}
