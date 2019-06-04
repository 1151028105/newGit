package com.pinyougou.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.pinyougou.pojo.TbBrand;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import entity.PageResult;

/**
 * 品牌接口
 * @param <TbBrand>
 */



public interface BrandService {
	
	public List<TbBrand> findAll();
	/**
	 * 品牌分页
	 * @param pageNum当前页
	 * @param pageSize总记录数
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	/**
	 * 条件查询
	 * @param brand 条件
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageResult findPage(TbBrand brand, int pageNum, int pageSize);
	/**
	 * 添加数据
	 * @param brand
	 */
	public void add(TbBrand brand);
	
	//根据你id查询
	public TbBrand findId(Long id);
	//根据id修改
	public void update(TbBrand brand);
	
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(Long [] ids);
	
	/**
	 * 返回下拉列表数据
	 * @return
	 */
	public List<Map> selectOptionList();
	
}
