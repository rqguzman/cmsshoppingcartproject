/*
	This code was kept for educational purposes.
	The course instructor implemented his app with this.
	I've used the "onclick" functionality instead.
*/

$(function() {

	$("a.confirmDeletion").click(function() {
		if (!confirm("Confirm deletion")) return false;
	});
});

/*
	Image Preview
*/
function readURL(input, idNum) {
	if(input.files && input.files[0]) {
	
		let reader = new FileReader();
		
		reader.onload = function (e) {
		
			$("#imgPreview" + idNum).attr("src", e.target.result).width(100).height(100);
		
		}
		
		reader.readAsDataURL(input.files[0]);
	
	}

}