package cn.e3.solr.cloud;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class MySolrCoud {
	
	/**
	 * 需求：使用solrj连接solr集群
	 * @throws Exception 
	 */
	@Test
	public void solrCloud() throws Exception{
		
		//指定连接zookeeper服务器地址
		String zkHost = "192.168.213.129:2182,192.168.213.129:2183,192.168.213.129:2184";
		//创建solr集群对象
		CloudSolrServer solrServer = new CloudSolrServer(zkHost);
		
		//指定查询索引库
		solrServer.setDefaultCollection("item");
		
		//创建solrQuery对象封装参数
		SolrQuery solrQuery = new SolrQuery();
		//查询所有
		solrQuery.setQuery("*:*");
		//从集群服务器查询索引库
		QueryResponse response = solrServer.query(solrQuery);
		//从返回值结果中获取结果集合对象
		SolrDocumentList results = response.getResults();
		
		//循环文档集合对象
		for (SolrDocument sDoc : results) {
			//获取商品标题
			String item_title = (String) sDoc.get("item_title");
			System.out.println(item_title);
		}
	}

}
