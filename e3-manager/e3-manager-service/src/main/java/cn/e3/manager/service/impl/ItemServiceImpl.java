package cn.e3.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.manager.service.ItemService;
import cn.e3.mapper.TbItemDescMapper;
import cn.e3.mapper.TbItemMapper;
import cn.e3.mapper.TbItemParamItemMapper;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;
import cn.e3.pojo.TbItemExample;
import cn.e3.pojo.TbItemParamItem;
import cn.e3.utils.DatagridPagebean;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.IDUtils;
@Service
public class ItemServiceImpl implements ItemService {
	
	//注入商品mapper接口代理对象
	@Autowired
	private TbItemMapper itemMapper;
	//注入商品描述mapper接口代理对象
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	//注入商品规格mapper接口代理对象
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	/**
	 * 需求:根据id查询商品数据
	 */
	public TbItem findItemByID(Long itemId) {
		// 根据主键查询商品数据
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		return item;
	}

	/**
	 * 需求:分页查询商品列表
	 * 参数:Integer page,Integer rows
	 * 返回值:DatagridPagebean
	 * 思考:
	 * 服务是否发布?
	 */
	public DatagridPagebean findItemListByPage(Integer page, Integer rows) {
		// 创建example对象
		TbItemExample example = new TbItemExample();
		//在查询之前,设置分页
		PageHelper.startPage(page, rows);
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		
		//创建PageInfo,分页插件PageInfo对象封装了所有的分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		
		//创建分页包装类对象DatagridPagebean,封装分页信息
		DatagridPagebean pagebean = new DatagridPagebean();
		//设置总记录数
		pagebean.setTotal(pageInfo.getTotal());
		//设置总记录
		pagebean.setRows(list);
		
		
		//返回分页对象
		return pagebean;
	}

	/**
	 * 需求：保存商品
	 * 参数：TbItem item,TbItemDesc itemDesc,TbItemPraram praram
	 * 返回值：E3mallResult
	 */
	@Override
	public E3mallResult saveItem(TbItem item, TbItemDesc itemDesc, String itemParams) {
		//生成id
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//状态
		item.setStatus((byte) 1);
		//创建和更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		//保存商品对象
		itemMapper.insert(item);
		
		//保存商品描述对象
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		tbItemDescMapper.insert(itemDesc);
		
		//创建商品规格对象
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		
		//保存商品规格参数
		itemParamItemMapper.insertSelective(itemParamItem);
		
		return E3mallResult.ok();
	}

}
