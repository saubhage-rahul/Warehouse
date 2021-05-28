$(document)
	.ready(
		function() {
			//1. Hide Error Variable

			$("#shipModeError").hide();
			$("#shipCodeError").hide();
			$("#enbleShipError").hide();
			$("#shipGradeError").hide();
			$("#shipDescError").hide();

			//2. Define Error Variable

			var shipModeError = false;
			var shipCodeError = false;
			var enbleShipError = false;
			var shipGradeError = false;
			var shipDescError = false;

			//3. Define Validate Function
			// 1st Function
			function validate_shipMode() {
				var val = $("#shipMode").val();
				if (val == '') {
					$("#shipModeError").show();
					$("#shipModeError").html(
						"<b>*Select Shipment Mode</b>");
					$("#shipModeError").css('color', 'red');
					shipModeError = false;
				} else {
					$("#shipModeError").hide();
					shipModeError = true;
				}
				return shipModeError;
			}
			// 2nd Function
			function validate_shipCode() {
				var val = $("#shipCode").val();
				var exp = /^[A-Z\-\s]{4,8}$/;
				if (val == '') {
					$("#shipCodeError").show();
					$("#shipCodeError").html("<b>*Please Enter Ship Code</b>");
					$("#shipCodeError").css('color', 'red');
					shipCodeError = false;
				} else if (!exp.test(val)) {
					$("#shipCodeError").show();
					$("#shipCodeError").html("*<b>Code</b> must be 4-8 uppercase letters");
					$("#shipCodeError").css('color', 'red');
					shipCodeError = false;
				} else {
					var id = 0; //for register
					if ($("#shipId").val() != undefined) { //for edit
						id = $("#shipId").val(); //if present
					}
					//ajax call start    
					$.ajax({
						url: 'validate',
						data: { "code": val, "id": id },
						success: function(resTxt) {
							if (resTxt != '') { //error, duplicate exist
								$("#shipCodeError").show();
								$("#shipCodeError").html(resTxt);
								$("#shipCodeError").css('color', 'red');
								shipCodeError = false;
							} else { //valid, no duplicate
								$("#shipCodeError").hide();
								shipCodeError = true;
							}
						}
					});
					//ajax call end    
				}
				return shipCodeError;
			}
			// 3rd Function
			function validate_enbleShip() {
				var len = $('[name="enbleShip"]:checked').length;
				if (len == 0) {
					$("#enbleShipError").show();
					$("#enbleShipError").html(
						"<b>*Choose Anyone</b>");
					$("#enbleShipError").css('color', 'red');
					enbleShipError = false;

				} else {
					$("#enbleShipError").hide();
					enbleShipError = true;
				}
				return enbleShipError;
			}
			// 4th Function
			function validate_shipGrade() {
				var len = $('[name="shipGrade"]:checked').length;
				if (len == 0) {
					$("#shipGradeError").show();
					$("#shipGradeError").html(
						"<b>*Choose Anyone</b>");
					$("#shipGradeError").css('color', 'red');
					shipGradeError = false;
				} else {
					$("#shipGradeError").hide();
					shipGradeError = true;
				}
				return shipGradeError;
			}
			// 5th Function
			function validate_shipDesc() {
				var val = $("#shipDesc").val();
				if (val == '') {
					$("#shipDescError").show();
					$("#shipDescError").html(
						"<b>*Enter Desc</b>");
					$("#shipDescError").css('color', 'red');
					shipDescError = false;
				} else {
					$("#shipDescError").hide();
					shipDescError = true;
				}
				return shipDescError;
			}

			//4. Link Action Event
			$("#shipMode").change(function() {
				validate_shipMode();
			});

			$("#shipCode").keyup(function() {
				$(this).val($(this).val().toUpperCase());
				validate_shipCode();
			});

			$('[name="enbleShip"]').change(function() {
				validate_enbleShip();
			});

			$('[name="shipGrade"]').change(function() {
				validate_shipGrade();
			});

			$("#shipDesc").keyup(function() {
				validate_shipDesc();
			});

			//5. On Submit Form
			$("#ShipmentTypeRegister").submit(
				function() {
					validate_shipMode();
					validate_shipCode();
					validate_enbleShip();
					validate_shipGrade();
					validate_shipDesc();

					if (shipModeError && shipCodeError
						&& enbleShipError
						&& shipGradeError
						&& shipDescError)
						return true;
					else
						return false;
				});
		});