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