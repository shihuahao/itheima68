package cn.e3.content.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import cn.e3.mapper.TbContentCategoryMapper;
import cn.e3.pojo.TbContentCategory;
import cn.e3.pojo.TbContentCategoryExample;
import cn.e3.pojo.TbContentCategoryExample.Criteria;
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

}
