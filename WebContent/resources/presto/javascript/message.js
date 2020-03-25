function customCommonLoad() {
	changeErrorFieldColor();
}

function changeErrorFieldColor() {

	var infoElementId = new Array();
	var warnElementId = new Array();
	var errElementId = new Array();

	var fields = document.getElementsByTagName("input");
	var textareaFields = document.getElementsByTagName("textarea");
	var selectFields = document.getElementsByTagName("select");
	var labelFields = document.getElementsByTagName("label");
	var divFields = document.getElementsByTagName("div");// use for picklist
	var tableFields = document.getElementsByTagName("table");// use for radio

	var strCheck;
	var strField;
	var chkLength;
	var reg;

	for (var i = 0; i < fields.length; i++) {
		if (fields[i].type == "hidden") {
			if (fields[i].name == "errColumnName") {
				errElementId.push(fields[i].value);
			} else if (fields[i].name == "warnColumnName") {
				warnElementId.push(fields[i].value);
			} else if (fields[i].name == "infoColumnName") {
				infoElementId.push(fields[i].value);
			}
		}
	}

	for (var i = 0; i < fields.length; i++) {
		if (fields[i].type == "text" || fields[i].type == "password"
				|| fields[i].type == "file") {
			if ("" != fields[i].id
					&& document.getElementById(fields[i].id).disabled != true) {
				for (var j = 0; j < infoElementId.length; j++) {
					strCheck = new String(infoElementId[j]);
					strField = fields[i].id;
					chkLength = strCheck.length;
					if (chkLength > 0) {
						if (strCheck.indexOf("_") > 0) {
							var checkStrArray = strCheck.split("_");
							lastIndexStr = checkStrArray[checkStrArray.length - 1];
							strCheck = strCheck.replace("_" + lastIndexStr, "");
							reg = new RegExp(strCheck + "(-[^\-]+)?" + "_"
									+ lastIndexStr + "$");
						} else {
							reg = new RegExp(strCheck + "(-[^\-_]+)?$");
						}
						if (strField.match(reg)) {
							setElementClassById(fields[i].id,
									".info_message_backcolor");
						}
					}
				}
				for (j = 0; j < warnElementId.length; j++) {
					strCheck = new String(warnElementId[j]);
					strField = fields[i].id;
					chkLength = strCheck.length;
					if (chkLength > 0) {
						if (strCheck.indexOf("_") > 0) {
							var checkStrArray = strCheck.split("_");
							lastIndexStr = checkStrArray[checkStrArray.length - 1];
							strCheck = strCheck.replace("_" + lastIndexStr, "");
							reg = new RegExp(strCheck + "(-[^\-]+)?" + "_"
									+ lastIndexStr + "$");
						} else {
							reg = new RegExp(strCheck + "(-[^\-_]+)?$");
						}
						if (strField.match(reg)) {
							setElementClassById(fields[i].id,
									".warn_message_backcolor");
						}
					}
				}
				for (j = 0; j < errElementId.length; j++) {
					strCheck = new String(errElementId[j]);
					strField = fields[i].id;
					chkLength = strCheck.length;
					if (chkLength > 0) {
						if (strCheck.indexOf("_") > 0) {
							var checkStrArray = strCheck.split("_");
							lastIndexStr = checkStrArray[checkStrArray.length - 1];
							strCheck = strCheck.replace("_" + lastIndexStr, "");
							reg = new RegExp(strCheck + "(-[^\-]+)?" + "_"
									+ lastIndexStr + "$");
						} else {
							reg = new RegExp(strCheck + "(-[^\-_]+)?$");
						}
						if (strField.match(reg)) {
							setElementClassById(fields[i].id,
									".error_message_backcolor");
						}
					}
				}
			}
		}
	}

	errorfieldBackgroundColor(textareaFields, infoElementId, warnElementId,
			errElementId);
	errorfieldBackgroundColor(selectFields, infoElementId, warnElementId,
			errElementId);
	errorfieldBackgroundColor(labelFields, infoElementId, warnElementId,
			errElementId);
	errorfieldBackgroundColor(divFields, infoElementId, warnElementId,
			errElementId);
	errorfieldBackgroundColor(tableFields, infoElementId, warnElementId,
			errElementId);

}

function errorfieldBackgroundColor(fields, infoElementId, warnElementId,
		errElementId) {

	var strCheck;
	var strField;
	var chkLength;
	var reg;
	var lastIndexStr;

	for (var i = 0; i < fields.length; i++) {
		if ("" != fields[i].id
				&& document.getElementById(fields[i].id).disabled != true) {
			strField = fields[i].id;
			for (var j = 0; j < infoElementId.length; j++) {
				strCheck = new String(infoElementId[j]);
				chkLength = strCheck.length;
				if (chkLength > 0) {
					if (strCheck.indexOf("_") > 0) {
						var checkStrArray = strCheck.split("_");
						lastIndexStr = checkStrArray[checkStrArray.length - 1];
						strCheck = strCheck.replace("_" + lastIndexStr, "");
						reg = new RegExp(strCheck + "(-[^\-]+)?" + "_"
								+ lastIndexStr + "$");
					} else {
						reg = new RegExp(strCheck + "(-[^\-_]+)?$");
					}
					if (strField.match(reg)) {
						setElementClassById(fields[i].id,
								".info_message_backcolor");
					}
				}
			}
			for (j = 0; j < warnElementId.length; j++) {
				strCheck = new String(warnElementId[j]);
				chkLength = strCheck.length;
				if (chkLength > 0) {
					if (strCheck.indexOf("_") > 0) {
						var checkStrArray = strCheck.split("_");
						lastIndexStr = checkStrArray[checkStrArray.length - 1];
						strCheck = strCheck.replace("_" + lastIndexStr, "");
						reg = new RegExp(strCheck + "(-[^\-]+)?" + "_"
								+ lastIndexStr + "$");
					} else {
						reg = new RegExp(strCheck + "(-[^\-_]+)?$");
					}
					if (strField.match(reg)) {
						setElementClassById(fields[i].id,
								".warn_message_backcolor");
					}
				}
			}
			for (j = 0; j < errElementId.length; j++) {
				strCheck = new String(errElementId[j]);
				chkLength = strCheck.length;
				if (chkLength > 0) {
					if (strCheck.indexOf("_") > 0) {
						var checkStrArray = strCheck.split("_");
						lastIndexStr = checkStrArray[checkStrArray.length - 1];
						strCheck = strCheck.replace("_" + lastIndexStr, "");
						reg = new RegExp(strCheck + "(-[^\-]+)?" + "_"
								+ lastIndexStr + "$");
					} else {
						reg = new RegExp(strCheck + "(-[^\-_]+)?$");
					}
					if (strField.match(reg)) {
						setElementClassById(fields[i].id,
								".error_message_backcolor");
					}
				}
			}
		}
	}
}

function setElementClassById(name, cssName) {

	if (name.substr(11) == "pickList") {
		$(PrimeFaces.escapeClientId(name)).children().eq(2).children().eq(1)
				.toggleClass(cssName.substr(1));

	} else if (name == "mas003Form:select" || name == "mas003Form:all") {
		$(PrimeFaces.escapeClientId(name)).children().eq(1).toggleClass(
				cssName.substr(1));
	} else {
		var cssClassNames = document.getElementById(name).getAttribute("class");
		cssClassNames += " " + cssName.substring(1);
		document.getElementById(name).setAttribute("class", cssClassNames);
		document.getElementById(name).setAttribute("className", cssClassNames);
	}
}