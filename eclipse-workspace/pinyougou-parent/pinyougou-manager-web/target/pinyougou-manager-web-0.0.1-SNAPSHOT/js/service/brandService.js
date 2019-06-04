app.service("brandService",function($http){
		
		this.findAll=function(){
			return $http.get('../brand/findAll.do');
		}
		
		this.findPage=function(page, size){
			return $http.get('../brand/findPage.do?page=' + page + '&size=' + size);
		}
		
		this.findOne=function(id){
			return $http.get('../brand/findId.do?id=' + id);
		}
		//添加
		this.add=function(entity){
			return $http.post('../brand/add.do', entity);
		}
		//修改
		this.update=function(entity){
			return $http.post('../brand/update.do',entity)
		}
		//删除
		this.dele=function(ids){
			return $http.get('../brand/delete.do?ids='+ids);
		}
		//保存
		this.search=function(page,size,searchEntity){
			return $http.post('../brand/search.do?page=' + page + '&size=' + size ,searchEntity);
		}
		//下拉列表展示
		this.selectOptionList=function(){ 
			return $http.get("../brand/selectOptionList.do");
		}
		
	});