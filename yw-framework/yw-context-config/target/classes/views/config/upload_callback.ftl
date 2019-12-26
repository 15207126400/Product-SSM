<script type="text/javascript">
	var resNo = "${error_no?default("")}";
	var resInfo = "${error_info?default("")}";
	
	if(resNo == 1){
		devShowDialog("导入成功");
		window.parent.uploadSuccess(resNo,resInfo);
	}else{
		devShowDialog(resInfo);	
	}
	
</script>
