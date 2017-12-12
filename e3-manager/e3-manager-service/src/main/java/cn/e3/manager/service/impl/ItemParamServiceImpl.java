package cn.e3.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.manager.service.ItemParamService;
import cn.e3.mapper.TbItemParamMapper;
import cn.e3.pojo.TbItemParam;
import cn.e3.pojo.TbItemParamExample;
import cn.e3.pojo.TbItemParamExample.Criteria;
import cn.e3.utils.DatagridPagebean;
import cn.e3.utils.E3mallResult;

@Service
public class ItemParamServiceImpl implements ItemParamService {
	
	//注入商品规格mapper接口代理对象
	@Autowired
	private TbItemParamMapper itemParamMapper;

	/**
	 * 需求:分页查询商品规格列表
	 * 参数:Integer page,Integer rows
	 * 返回值:DatagridPagebean
	 */
	@Override
	public DatagridPagebean findItemParamListByPage(Integer page, Integer rows) {
		//创建example对象
		TbItemParamExample itemParamExample = new TbItemParamExample();
		//在查询之前，设置分页
		PageHelper.startPage(page, rows);
		//查询
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(itemParamExample);
		
		//创建PageInfo,分页插件PageInfo对象封装了所有的分页信息
		PageInfo<TbItemParam> pageInfo = new PageInfo<TbItemParam>(list);
		
		//创建DatagridPagebean对象
		DatagridPagebean pagebean = new DatagridPagebean();
		//设置记录
		pagebean.setRows(list);
		//设置总记录数
		pagebean.setTotal(pageInfo.getTotal());
		
		return pagebean;
	}

	/**
	 * 需求：根据分类id查询商品的规格模板
	 * 参数：Long dategoryId
	 * 返回值：E3MallResult.ok(ItemParam)
	 */
	@Override
	public E3mallResult findItemParamByDategoryId(Long dategoryId) {
		//创建example对象
		TbItemParamExample example = new TbItemParamExample();
		//创建criteria对象
		Criteria criteria = example.createCriteria();
		//添加条件
		criteria.andItemCatIdEqualTo(dategoryId);
		//调用mapper接口方法
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		
		TbItemParam itemParam = null;
		
		//判断此分类是否关联了模板
		if(list!=null && list.size()>0){
			itemParam=list.get(0);
		}
		return E3mallResult.ok(itemParam);
	}

}