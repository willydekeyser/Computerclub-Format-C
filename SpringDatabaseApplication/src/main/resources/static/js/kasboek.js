var kasboekId = 0;

function newKasboek() {
	$.ajax({
		url: "/kasboek/newKasboek",
		success: function(data) {
			$("#newKasboekModalHolder").html(data);
			$("#newKasboekModal").modal("show");
		}
	})
};

function updateKasboek() {
	$.ajax({
		url: "/kasboek/updateKasboek?id=" + kasboekId,
		success: function(data) {
			$("#updateKasboekModalHolder").html(data);
			$("#updateKasboekModal").modal("show");
		}
	})
};

function deleteKasboek() {
	$.ajax({
		url: "/kasboek/deleteKasboek?id=" + kasboekId,
		success: function(data) {
			$("#deleteKasboekModalHolder").html(data);
			$("#deleteKasboekModal").modal("show");
		}
	})
};

function kasboekSelect(id) {
	kasboekId = $(id).attr('id');
	$('tr.active').removeClass('active');
	$(id).closest('tr').addClass('active');
};

kasboeklijst = function() {
	$('.kasboeklijst_click').on('click', function() {
		$('a.active').removeClass('active');
		$(this).addClass('active');
		selectedJaar = $(this).attr("jaar");
		selectedRubriek = $(this).attr("rubriek");
		$.ajax({
			url: "../kasboek/kasboekJaarRubriek?jaar=" + selectedJaar + "&rubriek=" + selectedRubriek,
			success: function(data) {
				$("#lijst").html(data);
			}	
		});
	});
};

function kasboeklijst_geladen() {
	$('#kasboek').addClass('active');
	$.ajax({
		url: "../kasboek/kasboekJaarRubriek?jaar=0&rubriek=0",
		success: function(data) {
			$("#lijst").html(data);
		}	
	});
	$(document).contextmenu(function(event){
		event.preventDefault();
	});
};