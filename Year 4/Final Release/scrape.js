/**************************************
 TITLE: jQuery		scrape.js	<----- Update the file name
 AUTHOR: Marcellus Hunt			<----- *Your* name should be here
 CREATE DATE: 5 December 2019
 PURPOSE: To use practice use of functions		<----- Also update the purpose of the file
 LAST MODIFIED ON: 5 December 2019
 LAST MODIFIED BY: Marcellus Hunt
 MODIFICATION HISTORY:
 5 December: 
 5 December: Add comments 
***************************************/

// The $ is the jQuery object
// "document" is the document object
// ready is a method of the jQuery object
// function creates an anonymous function to contain the code that should run
// In English, when the DOM has finished loading, execute the code in the function.
// See pages 312-313 of the text for details.

$(document).ready(function(){
	
//$( "#accordion" ).accordion();



var availableTags = [
	"John",
	"Gerald",
	"Robin",
	"Bruce",
	"Reginald",
	"Kent",
	"Ruth",
	"Beth"	
];
$( "#autocomplete" ).autocomplete({
	source: availableTags
});



$( "#button" ).button();
$( "#button-icon" ).button({
	icon: "ui-icon-gear",
	showLabel: false
});



$( "#radioset" ).buttonset();



$( "#controlgroup" ).controlgroup();



$( "#tabs" ).tabs();



$( "#dialog" ).dialog({
	autoOpen: false,
	width: 400,
	buttons: [
		{
			text: "Ok",
			click: function() {
				$( this ).dialog( "close" );
			}
		},
		{
			text: "Cancel",
			click: function() {
				$( this ).dialog( "close" );
			}
		}
	]
});

// Link to open the dialog
$( "#dialog-link" ).click(function( event ) {
	$( "#dialog" ).dialog( "open" );
	event.preventDefault();
});



$( "#datepicker" ).datepicker({
	inline: true
});



$( "#spinner" ).spinner();



$( "#menu" ).menu();



$( "#tooltip" ).tooltip();



$( "#selectmenu" ).selectmenu();


// Hover states on the static widgets
$( "#dialog-link, #icons li" ).hover(
	function() {
		$( this ).addClass( "ui-state-hover" );
	},
	function() {
		$( this ).removeClass( "ui-state-hover" );
	}
);
//end of ui library


$.validator.setDefaults({
	/*
Name: submit handler

PURPOSE:
	Scrapes data from the input and outputs it.

PARAMETERS: 
	noy any
	
RETURN VALUE: 
	not any
*/
	submitHandler: function() {
		
		alert("Invoking Submit");
		//first name value
		var firstName = new String($('#firstName').val());
		
		//middle name value
		var midName = new String($('#autocomplete').val());
		
		//last name value
		var lastName = new String($('#lastName').val());
		
		//phone value
		var phone = new String($('#telephone').val());
		
		//email value
		var email = new String($('#emailAutocomplete').val());
		
		//password value
		var password = new String($('#password').val());

		//value of checked radiobox
		var strRadioBox = $('input[name="radio"]:checked').val();

		//scrape check boxes
		var strCheckedBoxes = " ";
		
		//loops through the checkbox for checked items
		$('input[name="sport"]:checked').each(function() 
		{
			strCheckedBoxes += $(this).val() + " "; 
		
		});
		
		//value of datepicker
		var birthday = new String($('#datepicker').val());

		//value of spinner
		var rate = new String ($('#spinner').val());

		// strContnent echoes all the input values
		var strContent = new String ("My name is " + firstName +  " " + midName + "<br>" + lastName + ". <br>" +
		"My phone number is " + phone + ". <br>" + "My birthday is " + birthday + ". <br>" + "My email is " + email + ". <br>" + "My password is " +
		password + ". <br>" + "The type of account I have is a " + strRadioBox + ". <br>" + "My favorite sports are " + strCheckedBoxes + ". <br>" + "Customer Rating is " + rate
		);

		//strContent = "Hello hi";
		$("#output").html("Your account has been registered! Thanks for being a part of Shield Athletics <br>"  + strContent);
		
	}//end submit handler
	
});//end validator

//
$("#formId").validate({
	
	rules: {
	firstName:{ //first name is required and must be at least 2 characters
		required:true,
		minlength:2
	},
	lastName:{ //last name is required and must be at least 2 characters
		required:true,
		minlength:2
	},
	phone:{ // phone is required and must be at most 10 digits
		required: true, 
		digits: true, 
		maxlength: 10
	},
	email: { //email is required and must be an email
		required:true,
		email:true
	},
	password:{ // password is required and must be at least 8 characters
		required:true,
		minlength:8
	},
	confirm_password: { // confirm_password is required and needs to be the same as password entered above
		required: true,
		minlength: 8,
		equalTo: "#password"
	}
	
	}, //end rules
	messages: { // displays messages for the given rules above.
		firstName:{
			required: "Please enter your first name",
			minlength: "Must contain at least 2 characters. "
		},
		lastName:{
			required: "Please enter your last name",
			minlength: "Must contain at least 2 characters. "
		},
		phone: {
			required: "Must provide phone number. ",
			digits: "Must contain only digits. ",
			maxlength: "Can't have more than 10 digits. "
		},
		email: {
			required: "Please enter an email address. ",
			email: "Enter valid email. "
		},
		password:{
			required: "Need to provide a password. ",
			minlength: "Password not long enough, must be 8 characters. "
		},
		confirm_password: {
			required: "Need to retype password. ",
			minlength: "Must be 8 characters long. ",
			equalTo: "Password must be the same as the one above. "
		}
	}
	
}); //end .validate


	
}); // end of $(document).ready()
