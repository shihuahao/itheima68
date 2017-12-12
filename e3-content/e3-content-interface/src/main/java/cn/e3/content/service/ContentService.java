package cn.e3.content.service;

import java.util.List;

import cn.e3.pojo.TbContent;
import cn.e3.utils.AdItem;
import cn.e3.utils.DatagridPagebean;
import cn.e3.utils.E3mallResult;

public interface ContentService {

	/**
	 * 需求：根据节点id查询广告内容数据进行分页展示
	 * 参数：Long categoryId,Integer page,Integer rows 
	 * 返回值：DatagridPagebean
	 */
	public DatagridPagebean findContentListByPage(Long categoryId,Integer page,Integer rows);
	
	/**
	 * 需求：创建广告内容数据
	 * 参数：TbContent content
	 * 返回值：E3mallResult
	 */
	public E3mallResult createContent(TbContent content);

	/**
	 * 初始化大广告数据
	 * @param bIG_AD_CATEGORY_ID
	 * @return List<AdItem>
	 */
	public List<AdItem> findContentAdList(Long categoryId);
}
