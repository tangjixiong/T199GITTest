package com.t199.util;

import java.util.List;

/**
 * 分页类
 * @author TJX
 *
 */
public class Page {
	//每页数据条数
	private Integer pageSize=5;
	//当前页码
	private Integer curPage=1;
	//总页数
	private Integer totalPage;
	//数据总条数
	private Integer totalCount;
	//每页的数据
	private List list;
	//数据起始位置
	private Integer startrow;

	public Integer getStartrow() {
		return startrow;
	}

	public void setStartrow() {
		this.startrow=(this.getCurPage()-1)*this.getPageSize();
	}

	public Page(Object curPageStr,Object pageSizeStr,Integer totalCount){
		if(pageSizeStr==null || "".equals(pageSizeStr)){
			pageSizeStr="3";
		}
		if(curPageStr==null || "".equals(curPageStr)){
			curPageStr="1";
		}
		Integer pageSize=Integer.valueOf(pageSizeStr.toString());
		Integer curPage=Integer.valueOf(curPageStr.toString());
		this.setPageSize(pageSize);//设置每页数量
		this.setTotalCount(totalCount);//设置数据总条数，并且计算总页数
		this.setCurPage(curPage);//设置当前页
		this.setStartrow();//设置数据起始位置
	}

	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		if(pageSize!=null && pageSize>=1){
			this.pageSize = pageSize;
		}else{
			this.pageSize=5;
		}
	}
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		if(curPage!=null && curPage>=totalPage && totalPage!=0){
			this.curPage=totalPage;
		}else if(curPage!=null &&curPage>=1 ){
			this.curPage = curPage;
		}else{
			this.curPage = 1;
		}
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		if(totalCount!=null && totalCount>=0){  
			this.totalCount = totalCount;
			//计算总页数
			totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
		}else{
			totalCount=0;
			totalPage=0;
		}
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}


}
