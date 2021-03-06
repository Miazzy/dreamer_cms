package cn.itechyou.blog.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itechyou.blog.common.SearchEntity;
import cn.itechyou.blog.dao.CategoryMapper;
import cn.itechyou.blog.entity.Category;
import cn.itechyou.blog.entity.CategoryWithBLOBs;
import cn.itechyou.blog.service.CategoryService;
import cn.itechyou.blog.utils.LoggerUtils;
/**
 *      栏目管理业务类
 * @author LIGW
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	/**
	 *     日志输出
	 */
	private static final Logger logger = LoggerUtils.getLogger(CategoryServiceImpl.class);
	@Autowired
	private CategoryMapper categoryMapper;
	
	/**
	 * 添加
	 */
	@Override
	public void save(CategoryWithBLOBs category) {
		try {
			categoryMapper.insertSelective(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  列表
	 */
	@Override
	public PageInfo<CategoryWithBLOBs> queryListByPage(SearchEntity params) {
		if(params.getPageNum() == null || params.getPageNum() == 0) {
			params.setPageNum(1);
		}
		if(params.getPageSize() == null || params.getPageSize() == 0) {
			params.setPageSize(10);
		}
		PageHelper.startPage(params.getPageNum(), params.getPageSize());
		List<CategoryWithBLOBs> list = categoryMapper.queryListByPage(params.getEntity());
		PageInfo<CategoryWithBLOBs> page = new PageInfo<CategoryWithBLOBs>(list);
		return page;
	}

	/**
	 *  编辑回显
	 */
	@Override
	public CategoryWithBLOBs selectById(String id) {
		return categoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 查看子栏目
	 */
	@Override
	public List<CategoryWithBLOBs> selectByParentId(String id) {
		return categoryMapper.selectByParentId(id) ;
	}

	/** 
	 * 修改
	 */
	@Override
	public int update(CategoryWithBLOBs category) {
		return categoryMapper.updateByPrimaryKeySelective(category);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(String id) {
		return categoryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public CategoryWithBLOBs queryCategoryByCode(String code) {
		return this.categoryMapper.queryCategoryByCode(code);
	}

	@Override
	public void updateSort(List<Category> list) {
		for(int i = 0;i < list.size();i++) {
			CategoryWithBLOBs category = new CategoryWithBLOBs();
			category.setId(list.get(i).getId());
			category.setSort(list.get(i).getSort());
			this.categoryMapper.updateByPrimaryKeySelective(category);
		}
	}
	
}
