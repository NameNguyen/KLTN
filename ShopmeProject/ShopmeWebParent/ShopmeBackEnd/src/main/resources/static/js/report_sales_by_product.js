// Report Sales by Product
$(document).ready(function(){
	$(".btn-sales-by-product").on("click", function(e) {
		
		$(".btn-sales-by-product").each(function(e) {
			$(this).removeClass('btn-primary').addClass('btn-light');
		});
		
		$(this).removeClass('btn-light').addClass('btn-primary');
		
		period = $(this).attr('period');		
		loadReportSalesByProduct(period);
	});
});


function loadReportSalesByProduct(period) {
	days = getDays(period);
	url = contextPath + "baocao/product/" + period;

	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Sản phẩm');
	data.addColumn('number', 'Số lượng');
	data.addColumn('number', 'Tổng doanh thu');
	data.addColumn('number', 'Mạng lưới bán hàng');
	
	totalGrossSales = 0.0;
	totalNetSales = 0.0;
	totalProducts = 0;
	
	$.get(url, function(reportJson) {

		$.each(reportJson, function(index, reportItem) {
			data.addRows([[reportItem.identifier, reportItem.productsCount, reportItem.grossSales, reportItem.netSales]]);
			totalGrossSales += parseFloat(reportItem.grossSales);
			totalNetSales += parseFloat(reportItem.netSales);
			totalProducts += parseInt(reportItem.productsCount);
		});		
		
		var options = {
				title: getChartTitle(period),
				'height': 360,
				showRowNumber: true,
				page: 'enable',
				legend: {position: 'right'},
				sortColumn: 2,
				sortAscending: false,
				sortAscending: false,
				series: {
					0: {targetAxisIndex: 0},
					1: {targetAxisIndex: 0},
					2: {targetAxisIndex: 1}
				},
				  vAxes: {
					// Adds titles to each axis.
					0: {title: 'Sản lượng bán ra', format: 'currency'},
					1: {title: 'Số lượng đơn đặt hàng'}
				}		
		};
		
	    var formatter = new google.visualization.NumberFormat({
	    	prefix: '$'
	    });
	    formatter.format(data, 2);
	    formatter.format(data, 3);		
		
		var salesChart = new google.visualization.Table(document.getElementById('chart_sales_by_product'));
		salesChart.draw(data, options);	
		
		$("#textTotalGrossSales3").text("$" + $.number(totalGrossSales, 2));
		$("#textTotalNetSales3").text("$" + $.number(totalNetSales, 2));
		
		$("#textAvgGrossSales3").text("$" + $.number(totalGrossSales / days, 2));
		$("#textAvgNetSales3").text("$" + $.number(totalNetSales / days, 2));
		
		$("#textTotalProducts2").text(totalProducts);		
		
		
	});
	
	
}