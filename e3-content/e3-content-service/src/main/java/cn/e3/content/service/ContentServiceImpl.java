package cn.e3.content.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.patterns.ExactAnnotationFieldTypePattern;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.jedis.dao.JedisDao;
import cn.e3.mapper.TbContentMapper;
import cn.e3.pojo.TbContent;
import cn.e3.pojo.TbContentExample;
import cn.e3.pojo.TbContentExample.Criteria;
import cn.e3.utils.AdItem;
import cn.e3.utils.DatagridPagebean;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.JsonUtils;

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
	
	//注入首页缓存唯一标识
	@Value("${INDEX_CACHE}")
	private String INDEX_CACHE;
	
	//注入jedisDao对象
	@Autowired
	private JedisDao jedisDao;

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
	 * 删除，修改，添加广告数据时先删除缓存
	 * 用户再次查询，缓存已经不存在了，需要从新数据库查询新的数据
	 * 从而达到缓存同步目的
	 */
	@Override
	public E3mallResult createContent(TbContent content) {
		//先删除缓存
		jedisDao.hdel(INDEX_CACHE, content.getCategoryId()+"");
		
		//补全参数
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		//调用mapper方法
		tbContentMapper.insertSelective(content);
		return E3mallResult.ok();
	}

	/**
	 * 查询首页加载广告数据
	 * @param categoryId
	 * @return List<AdItem>
	 * 首页，获取其他广告页面加载广告数据是从数据库中查询，为了减轻数据库压力，给广告服务添加缓存
	 * 广告数据查询时，首先先查询redis缓存，如果缓存中有数据，直接返回即可，否则再次查询数据库
	 * 但同时，把查询的数据库数据放入缓存
	 * redis缓存服务器数据库结构：
	 * 缓存的数据结构：hash
	 * key：INDEX_CACHE（首页缓存） FOOD_CACHE（食品页面缓存）
	 * field：categoryId（缓存分类）
	 * value：json（缓存数据）
	 * 缓存流程：
	 * 1，先查询缓存，如果有缓存，直接返回，不再查询数据库
	 * 2，如果缓存不存在，查询数据库，同时需要把查询的数据放入缓存
	 */
	@Override
	public List<AdItem> findContentAdList(Long categoryId) {
		
		try {
			//先查询缓存
			String AdJson = jedisDao.hget(INDEX_CACHE, categoryId+"");
			//判断redis中是否存在缓存
			if(StringUtils.isNotBlank(AdJson)){
				//把广告json字符串数据转换成集合对象，返回
				List<AdItem> adList = JsonUtils.jsonToList(AdJson, AdItem.class);
				
				return adList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		
		//放入 缓存,当其他用户查询广告数据的时候，查询缓存数据
		jedisDao.hset(INDEX_CACHE, categoryId+"", JsonUtils.objectToJson(adList));
		
		return adList;
	}

}
