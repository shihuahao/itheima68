package cn.e3.search.service;

import cn.e3.search.pojo.SolrPage;
import cn.e3.utils.E3mallResult;

public interface SearchItemService {

	/**
	 * 需求：查询索引库域字段对应数据库值写入索引库
	 * 参数：
	 * 返回值：E3mallResult
	 */
	public E3mallResult findDatabaseToSolrIndex();
	
	/**
	 * 需求：接收参数，封装参数查询索引库
	 * 参数：String qName, Integer page, Integer rows
	 * 返回值：分页包装类对象 SolrPage
	 */
	public SolrPage findSolrIndex(String qName, Integer page, Integer rows);
	
}
