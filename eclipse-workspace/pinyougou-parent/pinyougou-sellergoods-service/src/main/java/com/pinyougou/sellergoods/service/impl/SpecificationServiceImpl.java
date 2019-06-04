package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import com.pinyougou.sellergoods.service.SpecificationService;

import entity.PageResult;
import sun.print.resources.serviceui;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	
	@Autowired
	private TbSpecificationOptionMapper specificationOptionmapper;
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		//获取规格实体
		TbSpecification tbSpecification = specification.getSpecification();
		specificationMapper.insert(tbSpecification);		
		
		List<TbSpecificationOption> specificationOption = specification.getSpecificationOptionList();
		for(TbSpecificationOption option:specificationOption) {
			//设置规格id
			option.setSpecId(tbSpecification.getId());
			// 新增规格
			specificationOptionmapper.insert(option);
			}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){

		//获取规格实体
		TbSpecification tbSpecification = specification.getSpecification();
		specificationMapper.updateByPrimaryKey(tbSpecification);	
		
		//先删除在添加，删除原来的数据，在重新添加
		TbSpecificationOptionExample example =new TbSpecificationOptionExample();
		com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(tbSpecification.getId());
		specificationOptionmapper.deleteByExample(example);
		
		List<TbSpecificationOption> specificationOption = specification.getSpecificationOptionList();
		for(TbSpecificationOption option:specificationOption) {
			//设置规格id
			option.setSpecId(tbSpecification.getId());
			// 新增规格
			specificationOptionmapper.insert(option);
					}
		
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
		Specification specification = new Specification();
		// 获取规格实体存入
		specification.setSpecification(specificationMapper.selectByPrimaryKey(id));
		
		//获取规格选项列表
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();  //创建条件类对象
		com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria(); //获取静态内部类
		criteria.andSpecIdEqualTo(id);// 把条件赋值，因为查询的规格选项表id就是参数id
		
		List<TbSpecificationOption> setSpecificationOptionList = specificationOptionmapper.selectByExample(example);
		
		specification.setSpecificationOptionList(setSpecificationOptionList);
		
		return specification;//组合实体类
	}

	/**
	 * 批量删除  
	 * 要  格  外  注意需要删除关联的外键表数据
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			specificationMapper.deleteByPrimaryKey(id);
			
			//删除关联表
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);
			specificationOptionmapper.deleteByExample(example);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override
		public List<Map> selectOptionList() {
			
			return specificationMapper.selectOptionList();
		}
		
		
	
}
