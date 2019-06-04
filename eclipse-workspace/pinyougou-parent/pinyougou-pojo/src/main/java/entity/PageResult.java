package entity;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable{
	/**
	 * 分页结果封装成对象
	 */
	private Long total;//总记录数
	private List rows;//当前页结果
	
	public PageResult(Long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
	
}
