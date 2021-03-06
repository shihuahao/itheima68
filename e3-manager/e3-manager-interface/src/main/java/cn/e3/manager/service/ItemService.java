package cn.e3.manager.service;

import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;
import cn.e3.utils.DatagridPagebean;
import cn.e3.utils.E3mallResult;

public interface ItemService {
	
	/**
	 * 需求:根据id查询商品数据
	 */
	public TbItem findItemByID(Long itemId);
	
	/**
	 * 需求:分页查询商品列表
	 * 参数:Integer page,Integer rows
	 * 返回值:DatagridPagebean
	 */
	public DatagridPagebean findItemListByPage(Integer page,Integer rows);

	/**
	 * 需求：保存商品
	 * 参数：TbItem item,TbItemDesc itemDesc,TbItemPraram praram
	 * 返回值：E3mallResult
	 */
	public E3mallResult saveItem(TbItem item, TbItemDesc itemDesc, String itemParams);

	/**
	 * 需求：根据Id查询商品描述
	 * @param itemId
	 * @return
	 */
	public TbItemDesc findItemDescById(Long itemId);
}
