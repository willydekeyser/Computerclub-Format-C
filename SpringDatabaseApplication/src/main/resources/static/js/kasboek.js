let kasboekId = 0;
let selectedId = 0;
let selectedJaar = 0;
let selectedRubriek = 0;

async function kasboek_start() {
	console.log('------------------------------------------------------------');
	console.log('Menu kasboek onclick');
	reset_grid();
	menu_height(3);
	
	await Refrech_HTML('/kasboek/', '.menu_main');
	kasboeklijst_geladen();
	kasboeklijst();
}

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

function kasboeklijst() {
	$('.kasboeklijst_click').on('click', function() {
		$('.menuopen').css("display", "none");
		$('.menuopen').removeClass('menuopen');
		$('ol li.active').removeClass('active');
		$(this).addClass('active');
		let select = document.getElementById("jaartal" + this.id);
		if (select) {
			select.style.display = "block";
			$(select).addClass('menuopen');
		}
		selectedJaar = $(this).attr("jaar");
		selectedRubriek = $(this).attr("rubriek");
		laad_Totalen();
		Refrech_HTML('/kasboek/kasboekJaarRubriek/' + selectedJaar + '/' + selectedRubriek, '.main_section_A');
		
	});
	
	$('.sub_kasboeklijst_click').on('click', function() {
		$('ol li.active').removeClass('active');
		$(this).addClass('active');
		selectedJaar = $(this).attr("jaar");
		selectedRubriek = $(this).attr("rubriek");
		laad_Totalen();
		Refrech_HTML('/kasboek/kasboekJaarRubriek/' + selectedJaar + '/' + selectedRubriek, '.main_section_A');
		return false;
	});
};

function kasboeklijst_geladen() {
	$('#namenlijst_click #kasboek').addClass('active');
	laad_Totalen();

	Refrech_HTML('/kasboek/kasboekJaarRubriek/0/0', '.main_section_A')
	$(document).contextmenu(function(event){
		event.preventDefault();
	});
};

async function laad_Totalen() {
	let data = await fetch_JSON('/kasboek/restcontroller/kasboekTotalen/' + selectedJaar + '/' + selectedRubriek);
	let html = ``;
	console.log('Totalen: ' + data.Jaar);
	$('.main_section_B').html(`<p>Jaar: ${data.Jaar} Rubriek: ${data.Rubriek}</p> 
	Inkomsten: €${data.Totalen[0].Inkomsten} Uitgaven: €${data.Totalen[0].Uitgaven} Totaal: €${data.Totalen[0].Totaal}</br>
	Inkomsten: €${data.Totalen[1].Inkomsten} Uitgaven: €${data.Totalen[1].Uitgaven} Totaal: €${data.Totalen[1].Totaal}`);
}
