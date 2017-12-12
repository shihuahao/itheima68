package cn.e3.content.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import cn.e3.mapper.TbContentCategoryMapper;
import cn.e3.pojo.TbContentCategory;
import cn.e3.pojo.TbContentCategoryExample;
import cn.e3.pojo.TbContentCategoryExample.Criteria;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.TreeNode;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	//注入mapper接口代理对象
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	/**
	 * 需求：根据父id查询节点下面的子节点
	 * 参数：Long parentId
	 * 返回值：List<treeNode>
	 */
	@Override
	public List<TreeNode> findContentCategoryTreeNodeList(Long parentId) {
		//创建List<TreeNode>
		ArrayList<TreeNode> tList = new ArrayList<TreeNode>();
		//创建一个example对象
		TbContentCategoryExample example = new TbContentCategoryExample();
		//创建criteria对象
		Criteria createCriteria = example.createCriteria();
		//设置查询参数：根据父id查询节点下面的子节点
		createCriteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		//循环分类集合数据，把分类数据封装到treeNodeList
		for (TbContentCategory tbContentCategory : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(tbContentCategory.getId());
			treeNode.setText(tbContentCategory.getName());
			treeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			//节点对象放入节点集合
			tList.add(treeNode);
		}
		
		return tList;
	}

	/**
	 * 需求：添加子节点
	 * 参数：Long parentId, String name
	 * 返回值：E3mallResult
	 */
	@Override
	public E3mallResult createNode(Long parentId, String name) {
		//创建一个节点对象
		TbContentCategory contentCategory = new TbContentCategory();
		//封装已知参数
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		//封装未知参数
		contentCategory.setIsParent(false);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		Date data = new Date();
		contentCategory.setCreated(data);
		contentCategory.setUpdated(data);
		//执行插入语句
		tbContentCategoryMapper.insert(contentCategory);
		
		//查询父节点 判断是不是子节点 如果是 修改节点状态
		TbContentCategory pContentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if(!pContentCategory.getIsParent()){
			//修改节点状态
			pContentCategory.setIsParent(true);
			//执行语句
			tbContentCategoryMapper.updateByPrimaryKeySelective(pContentCategory);
		}
		
		return E3mallResult.ok(contentCategory);
	}

}
