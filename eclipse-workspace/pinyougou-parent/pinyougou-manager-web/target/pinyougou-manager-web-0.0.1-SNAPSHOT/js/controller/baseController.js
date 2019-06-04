app.controller('baseController',function($scope){
	
	//分页控件配置currentPage:当前页 totalItems:总记录 itemsPerPage:每页记录数 perPageOptions:分页选项 onChange：当前页码变更后自动触发的方法
	$scope.paginationConf = {
		currentPage : 1,
		totalItems : 10,
		itemsPerPage : 10,
		perPageOptions : [ 10, 20, 30, 40, 50 ],
		onChange : function() {
			$scope.reloadList();//重新加载	
		}
	};
	
	//经常调用所有，就直接封装到方法中，以后直接调用就可以了。 刷新方法
	$scope.reloadList = function() {
		//调用findPage方法
		$scope.search($scope.paginationConf.currentPage,
				$scope.paginationConf.itemsPerPage);

	}
	
	$scope.selectIds=[]; //用户勾选的id集合
	//需要下一个方法
	$scope.updateSelection = function($event, id){
		if($event.target.checked){
			$scope.selectIds.push(id);//push向集合中添加元素
		}else{
			//勾选取消的话，就把集合中对应元素删除
			var index = $scope.selectIds.indexOf(id);// 查找值的位置
			$scope.selectIds.splice(index,1);  // 参数1：移除的位置 参数2：移除的个数
		}
	}
	
	$scope.jsonToString =function(jsonString,key){
		var json= JSON.parse(jsonString);
		var value="";
		
		for(var i=0;i<json.length;i++){
			if(i>0){
				value+=",";
			}
			value += json[i][key];
		}
		return value;
	}
});