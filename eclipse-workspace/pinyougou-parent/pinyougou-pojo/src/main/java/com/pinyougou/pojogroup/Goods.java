package com.pinyougou.pojogroup;

import java.io.Serializable;
/**
 * 商品组合实体类
 * @author 86189
 *
 */
import java.util.List;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
public class Goods implements Serializable {

	private TbGoods goods;//商品spu基本信息
	private TbGoodsDesc goodsDesc;//商品spu扩展信息
	
	private List<TbItem> itemList;//商品sku列表信息

	
	public Goods() {
		super();
		
	}



	public Goods(TbGoods goods, TbGoodsDesc goodsDesc, List<TbItem> itemList) {
		super();
		this.goods = goods;
		this.goodsDesc = goodsDesc;
		this.itemList = itemList;
	}



	public TbGoods getGoods() {
		return goods;
	}



	public void setGoods(TbGoods goods) {
		this.goods = goods;
	}



	public TbGoodsDesc getGoodsDesc() {
		return goodsDesc;
	}



	public void setGoodsDesc(TbGoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}



	public List<TbItem> getItemList() {
		return itemList;
	}



	public void setItemList(List<TbItem> itemList) {
		this.itemList = itemList;
	}
	
}
