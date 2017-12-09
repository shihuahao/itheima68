package cn.e3.manager.service;

import cn.e3.utils.DatagridPagebean;

public interface ItemParamService {

	/**
	 * 需求:分页查询商品规格列表
	 * 参数:Integer page,Integer rows
	 * 返回值:DatagridPagebean
	 */
	public DatagridPagebean findItemParamListByPage(Integer page, Integer rows);
}
