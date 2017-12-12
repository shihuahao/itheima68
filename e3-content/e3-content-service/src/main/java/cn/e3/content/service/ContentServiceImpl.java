package cn.e3.content.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.border.EmptyBorder;

import org.aspectj.weaver.patterns.ExactAnnotationFieldTypePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.mapper.TbContentMapper;
import cn.e3.pojo.TbContent;
import cn.e3.pojo.TbContentExample;
import cn.e3.pojo.TbContentExample.Criteria;
import cn.e3.utils.AdItem;
import cn.e3.utils.DatagridPagebean;
import cn.e3.utils.E3mallResult;

@Service
public class ContentServiceImpl implements ContentService {
	
	//注入mapper代理接口对象
	@Autowired
	private TbContentMapper tbContentMapper;
	
	//注入大广告宽高
	@Value("${WIDTH}")
	private Integer WIDTH;
	
	@Value("${WIDTHB}")
	private Integer WIDTHB;
	
	@Value("${HEIGHT}")
	private Integer HEIGHT;
	
	@Value("${HEIGHTB}")
	private Integer HEIGHTB;

	/**
	 * 需求：根据外键查询此节点列表 
	 * 参数：Long categoryId,Integer page,Integer rows 
	 * 返回值：DatagridPagebean
	 */
	@Override
	public DatagridPagebean findContentListByPage(Long categoryId, Integer page, Integer rows) {
		//创建example对象
		TbContentExample example = new TbContentExample();
		//创建criteria对象
		Criteria criteria = example.createCriteria();
		//设置查询参数：根据外键查询广告内容
		criteria.andCategoryIdEqualTo(categoryId);
		//设置分页查询参数
		PageHelper.startPage(page, rows);
		//执行查询语句
		List<TbContent> list = tbContentMapper.selectByExample(example);
		//创建分页包装类对象
		DatagridPagebean pagebean = new DatagridPagebean();
		//创建PageInfo对象
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		//设置参数
		pagebean.setRows(list);
		pagebean.setTotal(pageInfo.getTotal());
		//返回
		return pagebean;
	}

	/**
	 * 需求：创建广告内容数据
	 * 参数：TbContent content
	 * 返回值：E3mallResult
	 */
	@Override
	public E3mallResult createContent(TbContent content) {
		//补全参数
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		//调用mapper方法
		tbContentMapper.insertSelective(content);
		return E3mallResult.ok();
	}

	/**
	 * 初始化大广告位数据
	 * @param categoryId
	 * @return List<AdItem>
	 */
	@Override
	public List<AdItem> findContentAdList(Long categoryId) {
		//创建广告数据封装集合对象
		ArrayList<AdItem> adList = new ArrayList<AdItem>();
		
		//创建example对象
		TbContentExample example = new TbContentExample();
		//创建criteria对象
		Criteria criteria = example.createCriteria();
		//添加条件
		criteria.andCategoryIdEqualTo(categoryId);
		//调用mapper接口方法
		List<TbContent> list = tbContentMapper.selectByExample(example);
		
		//循环广告内容数据，把广告内容数据封装广告集合对象
		for (TbContent content : list) {
			//创建广告对象
			AdItem ad = new AdItem();
			//提示信息
			ad.setAlt(content.getSubTitle());
			//售卖地址
			ad.setHref(content.getUrl());
			//图片地址
			ad.setSrc(content.getPic());
			ad.setSrcB(content.getPic2());
			//设置图片宽高
			ad.setHeight(HEIGHT);
			ad.setHeightB(HEIGHTB);
			ad.setWidth(WIDTH);
			ad.setWidthB(WIDTHB);
			//把广告对象添加到广告集合
			adList.add(ad);
		}
		
		return adList;
	}

}
