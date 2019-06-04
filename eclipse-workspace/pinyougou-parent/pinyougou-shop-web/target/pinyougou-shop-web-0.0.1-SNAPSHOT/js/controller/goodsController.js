 //控制层 
app.controller('goodsController' ,function($scope,$controller,$location,goodsService,uploadService,itemCatService,typeTemplateService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(){	

		var id = $location.search()['id'];
		
		if(id==null){
			return;
		}
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;	
				
			editor.html($scope.entity.goodsDesc.introduction);//商品介绍
			
			//商品图片
			$scope.entity.goodsDesc.itemImages = JSON.parse($scope.entity.goodsDesc.itemImages);
			//扩展属性
			$scope.entity.goodsDesc.customAttributeItems =JSON.parse($scope.entity.goodsDesc.customAttributeItems);
			//规格选项集合
			$scope.entity.goodsDesc.specificationItems = JSON.parse($scope.entity.goodsDesc.specificationItems);
			//转换sku列表中的规格对象。
			for(var i=0;i<$scope.entity.itemList.length;i++){
				$scope.entity.itemList[i].spec=JSON.parse($scope.entity.itemList[i].spec);
			}
			
			}
		);		
	}
	
	//保存 
	$scope.save=function(){		
		$scope.entity.goodsDesc.introduction=editor.html();
		
		var serviceObject;//服务层对象  				
		if($scope.entity.goods.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					alert("保存成功");
				location.href="goods.html";
				
				}else{
					alert(response.message);
				}
			}		
		);				
	}

	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    //上传图片
	$scope.uploadFile=function(){
		uploadService.uploadFile().success(
			function(response){
				if(response.success){
					$scope.image_entity.url=response.message;
				}else{
					alert(response.message);
				}
			}
		);
		
	}
	
	$scope.entity={goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};
	//将当前上传的图片实体上传到图片列表
	$scope.add_img_entity=function(){
			
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
		
	}
	//删除图片
	$scope.dele_img_entity=function(index){
		
		$scope.entity.goodsDesc.itemImages.splice(index,1);
	}
		
	//查询一级分类数据列表
	$scope.selectItemCat1List=function(){
		itemCatService.findByParentId('0').success(
			function(response){
				$scope.itemCat1List=response;
			}
		);
	}
	
	//使用监控变量方式，查询二级分类数据列表
	$scope.$watch("entity.goods.category1Id",function(newValue,oldValue){
		
		itemCatService.findByParentId(newValue).success(
				function(response){
					$scope.itemCat2List=response;
				}
			);
	});
	//使用监控变量方式，查询三级分类数据列表
	$scope.$watch("entity.goods.category2Id",function(newValue,oldValue){
		
		itemCatService.findByParentId(newValue).success(
				function(response){
					$scope.itemCat3List=response;
				}
			);
	});
	
	//使用监控变量方式，查询模板
	$scope.$watch("entity.goods.category3Id",function(newValue,oldValue){
		
		itemCatService.findOne(newValue).success(
			function(response){
				$scope.entity.goods.typeTemplateId=response.typeId;//更新模板ID 
				
			}
		);
	});
	
	$scope.empty=function(){
		$scope.itemCat2List={};
		$scope.itemCat3List={}
		
	}
	
	
		//使用监控变量方式，查询品牌
		$scope.$watch("entity.goods.typeTemplateId",function(newValue,oldValue){
			
			//监控模板的id  就是得到品牌表中主键id   可以根据id 查询品牌
			typeTemplateService.findOne(newValue).success(
				function(response){
					$scope.typeTemplate = response; //将查询到的数据 赋值给变量
					
					//变量中属性brandIds的值是字符串 需要转换成json格式
					$scope.typeTemplate.brandIds= JSON.parse($scope.typeTemplate.brandIds);//类型转换\
					
					//扩展属性  会和数据回显冲突这里需要判断一下  
					if($location.search()['id']==null){ //为null说明是添加属性
						$scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.typeTemplate.customAttributeItems);
						
					}
					
					
				}
			);
			
			//查询规格列表
			typeTemplateService.findSpecList(newValue).success(
				function(response){
					$scope.specList=response;
				}
			);
		});
	
		$scope.updateSpecAttribute=function($event,name,value){
			
			var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,'attributeName',name)
			
			if(object!=null){//说明attributeName 有值 只需要添加value
				if($event.target.checked){//判断复选框的选中状态
					object.attributeValue.push(value);
				}else{//取消勾选
					object.attributeValue.splice(object.attributeValue.indexOf(value),1);//删除value的值
					//可以通过判断value的集合长度是否为0，如果0 就把整个集合都删除
					if(object.attributeValue.length==0){
						$scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specificationItems.indexOf(object),1);	
					}
				}
				
				
			}else{
				
				$scope.entity.goodsDesc.specificationItems.push({'attributeName':name,'attributeValue':[value]});
				
			}
		}
		//创建SKU列表
		$scope.createItemList=function(){
			//先定义一个初始化列表集合,加入空对象spec
			$scope.entity.itemList=[{spec:{},price:0,num:999,status:'0',isDefault:'0'}];
			//定义一个变量 ，方便后面使用   组合类中集合对象
			//[{"attributeName":"网络","attributeValue":["移动3G"]},{"attributeName":"机身内存","attributeValue":["16G"]}]
			var items = $scope.entity.goodsDesc.specificationItems;
			
			for(var i=0;i<items.length;i++){
				$scope.entity.itemList=addColum($scope.entity.itemList,items[i].attributeName,items[i].attributeValue);
			}
			
		}
		//参数1：初始化定义的列表集合， 参数2：就是items.attributeName得值 参数3：items.attributeValue的值
		addColum=function(list,columName,columValues){
			//这里创建新的集合
			var newList=[];
			for(var i=0;i<list.length;i++ ){
				//取出原有数据深克隆   需要深克隆，将原有数据赋值给变量  数据保持一致但互不干扰
				var oldRow=list[i];
				for(var j=0;j<columValues.length;j++){
					//深克隆
					var newRow = JSON.parse(JSON.stringify(oldRow));
					//这里就是给spec中columName属性赋值
					newRow.spec[columName]=columValues[j];
					
					newList.push(newRow);
				}
			}
			return newList;
		}
		//状态
		$scope.status=["未审核",'已审核','审核未通过','已关闭'];
		
		$scope.itemCatList=[];//商品分类集合
		//分类展示
		$scope.findItemCatList=function(){//定一个初始化方法
			
			itemCatService.findAll().success(//查询分类列表
			   function(response){
				   for(var i=0;i<response.length;i++){
					   //将查询出来的数据id  放入集合中，在将id对应的name放入。这样前台就可以通过表达式通过id获取对应的名称
					  $scope.itemCatList[ response[i].id] = response[i].name;
				   }
			   }		
			);
		}
		
		//判断规格与规格选项是否要被勾选
		$scope.checkAttributeValue=function(specName,optionName){
			var items=$scope.entity.goodsDesc.specificationItems;
			var object = $scope.searchObjectByKey(items,'attributeName',specName);
			if(object!=null){
				if(object.attributeValue.indexOf(optionName)>=0){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
			
		}
		
		//更新上下架状态
		$scope.updateMarketable=function(marketable){//页面调用方法传入需要修改成的状态     通过全局$scope 可以获得ids数组
		
			goodsService.updateMarketable($scope.selectIds,marketable).success(
				function(response){
					//判断是否成功
					if(response.success){
						$scope.reloadList();//刷新页面
						$scope.selectIds=[];//清空
					}else{
						//失败就提示
						alert(response.massage);
					}
				}
			);
		}
});	

