$(document).ready(
	function() {

		// 1. Hide Error Variable
		var val = $("#userTypeError").hide();
		var val = $("#userCodeError").hide();
		/* var val = $("#userForError").hide(); */
		var val = $("#userEmailError").hide();
		var val = $("#userContactError").hide();
		var val = $("#userIdTypeError").hide();
		var val = $("#ifOtherError").hide();
		var val = $("#userIdNumError").hide();
		var val = $("#ifOtherSec").hide();

		// 2. Define Error Variable
		var userTypeError = false;
		var userCodeError = false;
		/* var userForError = false; */
		var userEmailError = false;
		var userContactError = false;
		var userIdTypeError = false;
		var ifOtherError = false;
		var userIdNumError = false;

		// 3. Declare Validate Function
		function validate_userType() {
			var length = $('[name="userType"]:checked').length;
			if (length == 0) {
				$("#userTypeError").show();
				$("#userTypeError").html("<b>* Choose Anyone</b>")
				$("#userTypeError").css('color', 'red');
				userTypeError = false;
			} else {
				$("#userTypeError").hide();
				userTypeError = true;
			}
			return userTypeError;
		}

		function validate_userCode() {
			var val = $("#userCode").val();
			var exp = /^[A-Z\-\s]{4,8}$/;
			if (val == '') {
				$("#userCodeError").show();
				$("#userCodeError").html("<b>*Enter Code</b>");
				$("#userCodeError").css('color', 'red');
				userCodeError = false;
			}


			else if (!exp.test(val)) {
				$("#userCodeError").show();
				$("#userCodeError").html("*<b>Code must be 4-8 uppercase letters</b>");
				$("#userCodeError").css('color', 'red');
				userCodeError = false;
			}
			else {
				var id = 0; //for register
				if ($("#id").val() != undefined) { //for edit
					id = $("#id").val(); //if present
				}
				//ajax call start    
				$.ajax({
					url: 'validate',
					data: { "code": val, "id": id },
					success: function(resTxt) {
						if (resTxt != "") { //duplicate exist
							$("#userCodeError").show();
							$("#userCodeError").html(resTxt);
							$("#userCodeError").css('color', 'red');
							userCodeError = false;
						} else {
							$("#userCodeError").hide();
							userCodeError = true;
						}
					}
				});
			}

			return userCodeError;
		}

		/* function validate_userFor() {
			var val = $("#userFor").val();
			if (val == '') {
				$("#userForError").show();
				$("#userForError").html("<b>*Select User For</b>");
				$("#userForError").css('color', 'red');
				userForError = false;
			} else {
				$("#userForError").hide();
				userForError = true;
			}
			return userForError;
		} */

		function validate_userEmail() {
			var val = $("#userEmail").val();
			if (val == '') {
				$("#userEmailError").show();
				$("#userEmailError").html("<b>*Enter Email</b>");
				$("#userEmailError").css('color', 'red');
				userEmailError = false;
			} else {
				var id = 0; //for register
				if ($("#id").val() != undefined) { //for edit
					id = $("#id").val(); //if present
				}
				//ajax call start    
				$.ajax({
					url: 'validateEmail',
					data: { "email": val, "id": id },
					success: function(resTxt) {
						if (resTxt != "") { //error, duplicate exist
							$("#userEmailError").show();
							$("#userEmailError").html(resTxt);
							$("#userEmailError").css('color', 'red');
							userEmailError = false;
						} else { //valid, no duplicate
							$("#userEmailError").hide();
							userEmailError = true;
						}
					}
				});
				//ajax call end    
			}
			return userEmailError;
		}

		function validate_userContact() {
			var val = $("#userContact").val();
			if (val == '') {
				$("#userContactError").show();
				$("#userContactError")
					.html("<b>*Enter Contact</b>");
				$("#userContactError").css('color', 'red');
				userContactError = false;
			} else {
				$("#userContactError").hide();
				userContactError = true;
			}
			return userContactError;
		}

		function validate_userIdType() {
			var val = $("#userIdType").val();
			if (val == '') {
				$("#userIdTypeError").show();
				$("#userIdTypeError")
					.html("<b>*Select User ID</b>");
				$("#userIdTypeError").css('color', 'red');
				userIdTypeError = false;
			} else {
				$("#userIdTypeError").hide();
				userIdTypeError = true;
			}
			return userIdTypeError;
		}

		/* function validate_ifOther() {
			var val = $("#ifOther").val();
			if (val == '') {
				$("#ifOtherError").show();
				$("#ifOtherError").html("<b>*Other Id Proof</b>");
				$("#ifOtherError").css('color', 'red');
				ifOtherError = false;
			} else {
				$("#ifOtherError").hide();
				ifOtherError = true;
			}
			return ifOtherError;
		}  */

		function validate_userIdNum() {
			var val = $("#userIdNum").val();
			var exp = /^[A-Z0-9\-\s\.\:\@\,]{8,14}$/;
			if (val == '') {
				$("#userIdNumError").show();
				$("#userIdNumError")
					.html("<b>*Enter ID Number</b>");
				$("#userIdNumError").css('color', 'red');
				userIdNumError = false;
			}


			else if (!exp.test(val)) {
				$("#userIdNumError").show();
				$("#userIdNumError").html(" Only 8-14 chars allowed");
				$("#userIdNumError").css('color', 'red');
				userIdNumError = false;
			}

			else {
				var id = 0; //for register
				if ($("#id").val() != undefined) { //for edit
					id = $("#id").val(); //if present
				}
				//ajax call start    
				$.ajax({
					url: 'validateIdNumber',
					data: { "number": val, "id": id },
					success: function(resTxt) {
						if (resTxt != '') { //error, duplicate exist
							$("#userIdNumError").show();
							$("#userIdNumError").html(resTxt);
							$("#userIdNumError").css('color', 'red');
							userCodeError = false;
						} else { //valid, no duplicate
							$("#userIdNumError").hide();
							userIdNumError = true;
						}
					}
				});
				//ajax call end    
			}
			return userIdNumError;
		}

		/*function validate_userIdNum() {
			var val = $("#userIdNum").val();
			if (val == '') {
				$("#userIdNumError").show();
				$("#userIdNumError")
					.html("<b>*Enter ID Number</b>");
				$("#userIdNumError").css('color', 'red');
				userIdNumError = false;
			}
			else { //valid, no duplicate
				$("#userIdNumError").hide();
				userIdNumError = true;
			}

			return userIdNumError;
		}*/

		// 4. Link Action Event
		$('[name="userType"]').click(function() {
			var val = $('[name="userType"]:checked').val();
			if (val == "Vendor")
				$('#userFor').val("Purchase");
			else if (val == "Customer")
				$('#userFor').val("Sale");
			validate_userType();
		})

		$("#userCode").keyup(function() {
			$(this).val($(this).val().toUpperCase());
			validate_userCode();
		})

		/* $("#userFor").keyup(function() {
			validate_userFor();
		}) */

		$("#userEmail").keyup(function() {
			validate_userEmail();
		})

		$("#userContact").keyup(function() {
			validate_userContact();
		})

		$("#userIdType").change(function() {
			if ($(this).val() == "Other") {
				$("#ifOtherSec").show();
			} else {
				$("#ifOtherSec").hide();
			}
			validate_userIdType();
		})

		/* $("#ifOther").keyup(function() {
			validate_ifOther();
		}) */

		$("#userIdNum").keyup(function() {
			validate_userIdNum();
		})

		// 5. On Click Submit
		$("#MyWhUserForm").submit(
			function() {
				validate_userType();
				validate_userCode();
				/* validate_userFor(); */
				validate_userEmail();
				validate_userContact();
				validate_userIdType();
				/* validate_ifOther(); */
				validate_userIdNum();

				if (userTypeError && userCodeError
					&& userEmailError && userContactError
					&& userIdTypeError && userIdNumError)
					return true;
				else
					return false;
			})
	});