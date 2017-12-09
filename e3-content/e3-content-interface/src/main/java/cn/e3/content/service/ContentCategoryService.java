package cn.e3.content.service;

import java.util.List;

import cn.e3.utils.TreeNode;

public interface ContentCategoryService {

	/**
	 * 需求：根据父id查询节点下面的子节点
	 * 参数：Long parentId
	 * 返回值：List<treeNode>
	 */
	public List<TreeNode> findContentCategoryTreeNodeList(Long parentId);
}