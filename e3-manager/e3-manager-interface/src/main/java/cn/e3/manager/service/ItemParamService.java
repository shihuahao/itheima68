package cn.e3.manager.service;

import cn.e3.utils.DatagridPagebean;
import cn.e3.utils.E3mallResult;

public interface ItemParamService {

	/**
	 * 需求:分页查询商品规格列表
	 * 参数:Integer page,Integer rows
	 * 返回值:DatagridPagebean
	 */
	public DatagridPagebean findItemParamListByPage(Integer page, Integer rows);
	
	/**
	 * 需求：根据分类id查询商品的规格模板
	 * 参数：Long dategoryId
	 * 返回值：E3MallResult.ok(ItemParam)
	 */
	public E3mallResult findItemParamByDategoryId(Long dategoryId);
	
}
