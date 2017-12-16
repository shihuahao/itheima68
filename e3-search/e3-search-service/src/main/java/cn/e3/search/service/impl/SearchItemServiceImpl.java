package cn.e3.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import cn.e3.search.dao.SearchItemDao;
import cn.e3.search.mapper.SearchItemMapper;
import cn.e3.search.pojo.SearchItem;
import cn.e3.search.pojo.SolrPage;
import cn.e3.search.service.SearchItemService;
import cn.e3.utils.E3mallResult;

@Service
public class SearchItemServiceImpl implements SearchItemService {

	//注入solr服务
	@Autowired
	private SolrServer solrServer;
	
	//注入搜索mapper接口代理对象
	@Autowired
	private SearchItemMapper searchItemMapper;
	
	//注入dao
	@Autowired
	private SearchItemDao searchItemDao;
	/**
	 * 需求：查询索引库域字段对应数据库值写入索引库
	 * 参数：
	 * 返回值：E3mallResult
	 */
	@Override
	public E3mallResult findDatabaseToSolrIndex() {
		try {
			//查询索引库域字段对应数据库值
			List<SearchItem> list = searchItemMapper.findDatabaseToSolrIndex();
			//循环集合数据，把数据封装到doc文档对象，实现索引库写入
			for (SearchItem searchItem : list) {
				//创建一个文档对象，封装索引域字段对应值
				SolrInputDocument doc = new SolrInputDocument();
				//封装文档域所对应数据库查询值
				doc.addField("id", searchItem.getId());
				//标题
				doc.addField("item_title", searchItem.getTitle());
				//卖点
				doc.addField("item_sell_point", searchItem.getSell_point());
				//价格
				doc.addField("item_price", searchItem.getPrice());
				//图片地址
				doc.addField("item_image", searchItem.getImage());
				//商品类别
				doc.addField("item_category_name", searchItem.getCategory_name());
				//商品描述
				doc.addField("item_desc", searchItem.getItem_desc());
				
				//写入索引库
				solrServer.add(doc);
			}
			//提交
			solrServer.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return E3mallResult.ok();
	}

	/**
	 * 需求：接收参数，封装参数查询索引库
	 * 参数：String qName, Integer page, Integer rows
	 * 返回值：分页包装类对象 SolrPage
	 */
	@Override
	public SolrPage findSolrIndex(String qName, Integer page, Integer rows) {
		//创建solrQuery封装参数，solr所有的参数及查询条件都是由solrQuery进行封装
		SolrQuery solrQuery = new SolrQuery();
		//判断参数是否为空
		if(qName!=null && !"".equals(qName)){
			solrQuery.setQuery(qName);
		}else{
			//查询所有
			solrQuery.setQuery("*:*");
		}
		
		//分页
		int startNo = (page-1)*rows;
		solrQuery.setStart(startNo);
		solrQuery.setRows(rows);
		//高亮
		//开启高亮
		solrQuery.setHighlight(true);
		//设置高亮字段
		solrQuery.addHighlightField("item_title");
		//设置高亮前缀
		solrQuery.setHighlightSimplePre("<font color='red'>");
		//设置高亮后缀
		solrQuery.setHighlightSimplePost("</font>");
		
		//设置默认查询字段--复制域
		solrQuery.set("df", "item_keywords");
		
		//调用dao查询索引库
		SolrPage solrPage = searchItemDao.findSolrIndex(solrQuery);
		
		//设置当前页
		solrPage.setPage(page);
		//设置总页码数
		Integer totalCount = solrPage.getTotalCount();
		int pages = totalCount/rows;
		if(totalCount%rows>0){
			page++;
		}
		solrPage.setTotalPages(pages);
		
		return solrPage;
	}

}
