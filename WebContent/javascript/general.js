$(document).ready(function() {

	$(window).scroll(function() {
		if ($(this).scrollTop() > 20) {
			$('.scrollToTop').fadeIn();
		} else {
			$('.scrollToTop').fadeOut();
		}
	});

	// Click event to scroll to top
	$('.scrollToTop').click(function() {
		$('html, body').animate({
			scrollTop : 0
		}, 800);
		return false;
	});

	var currentLocation = window.location.pathname;

	if (currentLocation.indexOf("/view/CheckStatus.seam") !== -1) {
		// fixRowWidthForStatus();
	}

	// Stop user to press enter in textbox
	$("input:text").keypress(function(event) {
		if (event.keyCode == 13) {
			event.preventDefault();
			event.returnValue = false;
		}
	});

	$("input:radio").keypress(function(event) {
		if (event.keyCode == 13) {
			event.preventDefault();
			event.returnValue = false;
		}
	});

	$("input:file").keypress(function(event) {
		if (event.keyCode == 13) {
			event.preventDefault();
			event.returnValue = false;
		}
	});

	$("input:checkbox").keypress(function(event) {
		if (event.keyCode == 13) {
			event.preventDefault();
			event.returnValue = false;
		}
	});

	$('.YM-picker, .japan-YM-picker').on('click focusin', function() {
		this.value = '';
	});

});

// after page load (used in check status)
$(window).bind(
		"load",
		function() {
			if ($(PrimeFaces.escapeClientId('checkStatusform:statusProcess'))
					.val() == "success") {
				$(PrimeFaces.escapeClientId('checkStatusform:downloadLink'))
						.click();
			}

		});

function fixRowWidthForStatus() {
	$(PrimeFaces.escapeClientId('checkStatusform:statusResultList_head'))
			.children().children().eq(0).attr("style",
					"width:160px !important;");
	$(PrimeFaces.escapeClientId('checkStatusform:statusResultList_head'))
			.children().children().eq(1).attr("style",
					"width:160px !important;");
	$(PrimeFaces.escapeClientId('checkStatusform:statusResultList_head'))
			.children().children().eq(2).attr("style",
					"width:120px !important;");
	$(PrimeFaces.escapeClientId('checkStatusform:statusResultList_head'))
			.children().children().eq(3).attr("style",
					"width:160px !important;");
	$(PrimeFaces.escapeClientId('checkStatusform:statusResultList_head'))
			.children().children().eq(4).attr("style",
					"width:120px !important;");
	$(PrimeFaces.escapeClientId('checkStatusform:statusResultList_head'))
			.children().children().eq(5)
			.attr("style", "width:65px !important;");
	$(PrimeFaces.escapeClientId('checkStatusform:statusResultList_head'))
			.children().children().eq(6).attr("style",
					"width:160px !important;");
	$(PrimeFaces.escapeClientId('checkStatusform:statusResultList_head'))
			.children().children().eq(7).attr("style",
					"width:110px !important;");
	$(PrimeFaces.escapeClientId('checkStatusform:statusResultList_head'))
			.children().children().eq(8).attr("style",
					"width:140px !important;");
	$(PrimeFaces.escapeClientId('checkStatusform:statusResultList_head'))
			.children().children().eq(9).attr("style",
					"width:150px !important;");
}

// USR001
function fixRowWidthForUser() {
	for (var col = 0; col < 5; col++) {
		var td_width = $(PrimeFaces.escapeClientId('usr001form:Users_head'))
				.children().children().children().children().children().eq(col)
				.width();

		$(PrimeFaces.escapeClientId('usr001form:Users_data')).children()
				.children().children().children().children().eq(col).attr(
						"style", "width:" + td_width + "% !important;");
	}
}

function prj004Layout() {
	// change div height in PRJ002
	$("#buttonDiv").css("margin-top", $("#topDiv").height() + 2 + "px")
}

// disable browser back button process
if ($.browser.msie || $.browser.chrome || $.browser.mozilla) {
	window.history.pushState(null, null, window.location.href);
	window.addEventListener('popstate', function() {
		window.history.pushState(null, null, window.location.href);
	});
}

function plBtnNameChange() {
	$(".ui-picklist-button-add").children().remove();
	$(".ui-picklist-button-add").append(
			'<span class="ui-button-icon-left picklist-button">選択 ></span>');

	$(".ui-picklist-button-add-all").children().remove();
	$(".ui-picklist-button-add-all").append(
			'<span class="ui-button-icon-left picklist-button">全て選択>></span>');

	$(".ui-picklist-button-remove").children().remove();
	$(".ui-picklist-button-remove").append(
			'<span class="ui-button-icon-left picklist-button">< 戻す</span>');

	$(".ui-picklist-button-remove-all").children().remove();
	$(".ui-picklist-button-remove-all").append(
			'<span class="ui-button-icon-left picklist-button"><<全て戻す</span>');

}

function changeUpdate(val) {
	var temp = new Array();
	temp = val.id.split(":");
	console.log(temp);

	var temp1 = temp[0] + ":" + temp[1] + ":" + temp[2] + ":changeProcess";
	$(PrimeFaces.escapeClientId(temp1)).val("0");
}
