package cn.com.sinosoft.saa.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的树形结构
 * 
 * @author zhhd
 * 
 * @param <T>
 * 
 */
public class TreeNode<T> {
	private T value;
	private TreeNode<T> parent;
	private List<TreeNode<T>> children;

	public TreeNode(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	/**
	 * ����ӽڵ�
	 * 
	 * @param node
	 */
	public void addChild(TreeNode<T> node) {
		if (children == null)
			children = new ArrayList<TreeNode<T>>();

		children.add(node);
	}

	/**
	 * �õ�ֱ���¼��Ľڵ��б�
	 * 
	 * @return
	 */
	public List<TreeNode<T>> getChildren() {
		return children;
	}

	/**
	 * �õ����е��ӽڵ��б?�ݹ����ϣ�
	 * 
	 * @return
	 */
	public List<TreeNode<T>> getAllChildren() {
		if (isLeaf())
			return null;

		List<TreeNode<T>> list = new ArrayList<TreeNode<T>>();
		list.addAll(children);
		for (TreeNode<T> node : children) {
			if (!node.isLeaf())
				list.addAll(node.getAllChildren());
		}
		return list;
	}

	/**
	 * �õ����еĸ��ڵ��б?�ݹ����ϣ�
	 * 
	 * @return
	 */
	public List<TreeNode<T>> getAllParent() {
		List<TreeNode<T>> list = new ArrayList<TreeNode<T>>();
		if (parent != null && parent != this) {
			list.add(parent);
			list.addAll(parent.getAllParent());
		}
		return list;
	}
	/**
	 * ������Ŀ�ȣ�ÿ��ڵ�ռһ���ȵ�λ
	 * 
	 * @return
	 */
	public int getTreeWidth() {
		if (isLeaf()) {
			return 1;
		}
		int width = 0;
		for (TreeNode<T> node : children) {
			width += node.getTreeWidth();
		}
		return width;
	}

	/**
	 * ����������
	 * 
	 * @return
	 */
	public int getTreeDepth() {
		if (isLeaf())
			return 1;
		int maxDepth = 0;
		for (TreeNode<T> node : children) {
			int depth = node.getTreeDepth();
			if (depth > maxDepth)
				maxDepth = depth;
		}
		maxDepth = maxDepth + 1;
		return maxDepth;
	}

	/**
	 * �Ƿ�����Ҷ��û���ӽڵ��Ϊ��Ҷ
	 * 
	 * @return
	 */
	public boolean isLeaf() {
		return children == null || children.size() == 0;
	}
}
