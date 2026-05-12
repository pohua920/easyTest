// 将select 标签 ul化 ，解决ie8下长内容不能完全显示的问题
// $.prop 需要 jquery version l.6+
jQuery.fn.extend({
	seltoul : function(w,h){
		var $div = $("#seldiv");
		if ($div.length == 0) {
			$div = $("<div/>");
			$div.prop({
				id : "seldiv",
				align : "left"
			});
			$div.appendTo($(this).offsetParent());
		}
		$(this).each(function(i,s){
			var $input = $("<input/>");
			$input.prop({
				type : "text",
				readonly : true
			}).on("focus click" , function(){
				var $in = $(this);
				var $sl = $(this).prev();
				if ($in.prev().is(":disabled")) {//
					return;
				}
				var $option = $sl.children("option");
				if ($option.length > 0) {
					var tinp = $in.data("t");
					var tdiv = $div.data("t");
					if (tinp && tdiv && tinp === tdiv) {
						// 判断最后展示的div ul是否对应本input下拉select , 是则只需直接显示即可
					} else {
						var t = $.now() + "" + Math.floor(1000 * Math.random());
						$in.data("t", t);
						$div.data("t", t);
						// 否则div ul 需要重构
						var $ul = $("<ul/>");
						$option.each(function(){
							var $li = $("<li/>");
							$li.text($(this).text());
							$li.attr("value", $(this).prop("value") || "");
							$li.on("mouseover", function(){
								$(this).addClass("hover");
							}).on("mouseout", function(){
								$(this).removeClass("hover");
							}).on("click", function(){
								if($sl.val() != ($(this).attr("value") || "")){
									$sl.val($(this).attr("value") || "");
									$sl.triggerHandler("change");
								}
								$in.data("scrollTop", $ul.scrollTop());
								$div.hide();
							});
							$ul.append($li);
						});
						$ul.on("mouseenter", function(){
							$in.data("onul", true);
						}).on("mouseleave", function(){
							$in.removeData("onul");
							if (!$in.data("oninput")) {// 失去焦点，若焦点也不在input内，则关闭
								$div.hide();
							}
						});
						if (w && w > 0) {
							$ul.css({
								width : w
							});// 设置ul宽
						}
						if (h && h > 0) {
							$ul.css({
								height : h
							});// 设置ul高
						}
						$div.empty().append($ul);
					}
					$div.css({
						display : "block"
					});
					$div.find("li").removeClass("select").filter("[value='" + $sl.val() + "']").addClass("select");// 选中项加样式
					var offset = $in.offset();
					//计算元素相对input的偏移量
					var ofTop = offset.top < $(window).height() / 2 ? $in.height() + 5 : -($div.height() + 8);
					var ofLeft = offset.left < $(window).width() / 2 ? 0 : - ($div.width() + 5);
					$div.offset({
						top : offset.top + ofTop,
						left : offset.left + ofLeft
					});
					// ul的滚动维持前一次选择时的位置
					if ($in.data("scrollTop")) {
						$div.find("ul").scrollTop($in.data("scrollTop"));
					}
				}
				$in.data("oninput", true);// 设置 焦点 状态
			}).on("blur", function(){
				$(this).removeData("oninput");
				if (!$(this).data("onul")) {// 失去焦点，若焦点也不在下拉域内，则关闭
					$div.hide();
				}
			}).css({
				width : function(){
					return $(s).width() == 0 ? "95%" : $(s).width();
				}
			});
			// select隐藏，后面追加创建的input
			$(s).on("change" , function(){
				var text = $(this).children("option:selected").text();
				$(this).next().prop({
					value : text,
					title : text
				});
			}).hide().after($input[0]).delay(500).triggerHandler("change");
		});
		return this;
	}
});