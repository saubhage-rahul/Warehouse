$(document).ready(function() {
	//1. Hide Error Variable
	$("#uomTypeError").hide();
	$("#uomModelError").hide();
	$("#uomDescError").hide();

	//2. Define Error Variable
	var uomTypeError = false;
	var uomModelError = false;
	var uomDescError = false;

	//3. Define Validate Function

	function validate_uomType() {
		// Read Value
		var val = $("#uomType").val();
		if (val == '') {
			$("#uomTypeError").show();
			$("#uomTypeError").html("<b>*Select Anyone</b>");
			$("#uomTypeError").css('color', 'red');
			uomTypeError = false;
		} else {
			$("#uomTypeError").hide();
			uomTypeError = true;
		}
		return uomTypeError;
	}

	function validate_uomModel() {
		//Read Value
		var val = $("#uomModel").val();
		var exp = /^[A-Z\-\s]{4,12}$/;
		if (val == '') {
			$("#uomModelError").show();
			$("#uomModelError").html("<b>*Enter Model</b>");
			$("#uomModelError").css('color', 'red');
			uomModelError = false;
		}

		else if (!exp.test(val)) {
			$("#uomModelError").show();
			$("#uomModelError").html("<b>Only 4-12 chars allowed</b>");
			$("#uomModelError").css('color', 'red');
			uomModelError = false;
		}

		else {
			var id = 0; //for register
			if ($("#uid").val() !== undefined) {
				id = $("#uid").val(); //edit
			}
			$.ajax({
				url: 'validate',
				data: { "model": val, "id": id },
				success: function(resTxt) {
					if (resTxt != "") { //duplicate exist
						$("#uomModelError").show();
						$("#uomModelError").html(resTxt);
						$("#uomModelError").css('color', 'red');
						uomModelError = false;
					} else {
						$("#uomModelError").hide();
						uomModelError = true;
					}
				}
			});
		}
		return uomModelError;
	}

	function validate_uomDesc() {
		var val = $("#uomDesc").val();
		if (val == '') {
			$("#uomDescError").show();
			$("#uomDescError").html("<b>*Enter Desc</b>");
			$("#uomDescError").css('color', 'red');
			uomDescError = false;

		} else {
			$("#uomDescError").hide();
			uomDescError = true;
		}
		return uomDescError;
	}

	//4. Define Action Event

	$("#uomType").change(function() {
		validate_uomType();
	});

	$("#uomModel").keyup(function() {
		$(this).val($(this).val().toUpperCase());
		validate_uomModel();
	});

	$("#uomDesc").keyup(function() {
		validate_uomDesc();
	});

	//5. On Click Submit
	$("#uomRegister").submit(function() {
		validate_uomType();
		validate_uomModel();
		validate_uomDesc();

		if (uomTypeError && uomModelError && uomDescError)
			return true;
		return false;
	});

});