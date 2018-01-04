package cn.e3.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.e3.manager.service.ItemParamItemService;
import cn.e3.mapper.TbItemParamItemMapper;
import cn.e3.pojo.TbItemParamItem;
import cn.e3.pojo.TbItemParamItemExample;
import cn.e3.pojo.TbItemParamItemExample.Criteria;
import cn.e3.utils.E3mallResult;

public class ItemParamItemServiceImpl implements ItemParamItemService {

	
	//注入规格参数接口
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	/**
	 * 需求：根据商品id查询规格参数
	 * 参数：Long itemId
	 * 返回值：E3mallResult
	 */
	@Override
	public E3mallResult findItemParamItemByItemId(Long itemId) {
		//创建example对象
		TbItemParamItemExample example = new TbItemParamItemExample();
		//创建criteria对象
		Criteria criteria = example.createCriteria();
		//添加条件
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		//判断规格参数数据是否存在
		TbItemParamItem tbItemParamItem = null;
		if(list!=null && list.size()>0){
			tbItemParamItem = list.get(0);
		}
		//返回值
		return E3mallResult.ok(tbItemParamItem);
	}

}
