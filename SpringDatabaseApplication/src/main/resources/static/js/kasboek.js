let kasboekId = 0;
let selectedId = 0;
let selectedJaar = 0;
let selectedRubriek = 0;
let kasboek_gegevens = "";

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
	console.log("New kasboek");
	if($("#editKasboekModal").length == 0){
		let data = load_HTML('/kasboek/editKasboek')
		.then((data) => {
			$("#editKasboekModalHolder").html(data);
			setup_newKasboekModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_newKasboekModal();
	};
};

function setup_newKasboekModal() {
	$('#modal-titel').html('New kasboek!');
	$('#editKasboekModal').one('shown.bs.modal', listener_newKasboek_focus);
	$('#editKasboekModal').one('hidden.bs.modal', listener_newkasboek_hidden);
	$('#editKasboekModalForm').one('submit', listener_newKasboek_submit);
	if (selectedJaar != 0) {
		$('#editKasboekModalForm #jaartal').val(selectedJaar);
	};
	if (selectedRubriek != 0) {
		$('#editKasboekModalForm #rubriek').val(selectedRubriek);
	};
	$("#editKasboekModal").modal("show");
};

function listener_newKasboek_focus() {
	if (selectedJaar == 0) {
		$("input[name='jaartal']").focus();
		return;
	};
	if (selectedRubriek == 0) {
		$("input[name='rubriek.id']").focus();
		return;
	};
	$("input[name='omschrijving']").focus();
};

function listener_newkasboek_hidden() {
	$('#editKasboekModalForm').unbind();
	$('#editKasboekModalForm').trigger('reset');
};

function listener_newKasboek_submit() {
	let form = $(this);
	let data = post_Form('/kasboek/save_newKasboek', form)
	.then((data) => {
		selectedId = data.id;
		$("#editKasboekModal").modal('toggle');
		console.log('New Kasboek: ');
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};

function updateKasboek() {
	console.log("Edit kasboek");
	if($("#editKasboekModal").length == 0){
		let data = load_HTML('/kasboek/editKasboek')
		.then((data) => {
			$("#editKasboekModalHolder").html(data);
			setup_updateKasboekModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_updateKasboekModal();
	};
};

function setup_updateKasboekModal() {
	$('#modal-titel').html('Update kasboek!');
	$('#editKasboekModal').one('shown.bs.modal', listener_newKasboek_focus);
	$('#editKasboekModal').one('hidden.bs.modal', listener_newkasboek_hidden);
	$('#editKasboekModalForm').one('submit', listener_newKasboek_submit);
	$('#editKasboekModalForm #id').val(kasboek_gegevens.id);
	$('#editKasboekModalForm #jaartal').val(kasboek_gegevens.jaartal);
	$('#editKasboekModalForm #rubriek').val(kasboek_gegevens.rubriekId);
	$('#editKasboekModalForm #omschrijving').val(kasboek_gegevens.omschrijving);
	$('#editKasboekModalForm #datum').val(kasboek_gegevens.datum);
	$('#editKasboekModalForm #inkomsten').val(kasboek_gegevens.inkomsten);
	$('#editKasboekModalForm #uitgaven').val(kasboek_gegevens.uitgaven);
	$("#editKasboekModal").modal("show");
};

function listener_updateKasboek_focus() {
	$("input[name='voornaam']").focus();
};

function listener_updatekasboek_hidden() {
	$('#editKasboekModalForm').unbind();
	$('#editKasboekModalForm').trigger('reset');
};

function listener_updateKasboek_submit() {
	
};

function deleteKasboek() {
	console.log("Delete kasboek");
	if($("#editKasboekModal").length == 0){
		let data = load_HTML('/kasboek/editKasboek')
		.then((data) => {
			$("#editKasboekModalHolder").html(data);
			setup_deleteKasboekModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_deleteKasboekModal();
	};
};

function setup_deleteKasboekModal() {
	$('#modal-titel').html('Delete kasboek!');
	$("#editKasboekModal").modal("show");
};

function listener_deleteKasboek_focus() {
	$("input[name='voornaam']").focus();
};

function listener_deletekasboek_hidden() {
	$('#editKasboekModalForm').unbind();
	$('#editKasboekModalForm').trigger('reset');
};

function listener_delteKasboek_submit() {
	
};

function kasboekSelect(id) {
	kasboekId = $(id).attr('id');
	kasboek_gegevens_refrech();
	$('tr.active').removeClass('active');
	$(id).closest('tr').addClass('active');
};

async function kasboek_gegevens_refrech() {
	let response = await fetch('/kasboek/restcontroller/kasboekbyid/' + kasboekId);
	kasboek_gegevens = await response.json();
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
		Refrech_HTML('/kasboek/kasboekJaarRubriek/' + selectedJaar + '/' + selectedRubriek, '.main_section_A');
		laad_Totalen();
		return false;
	});
	
	$('.sub_kasboeklijst_click').on('click', function() {
		$('ol li.active').removeClass('active');
		$(this).addClass('active');
		selectedJaar = $(this).attr("jaar");
		selectedRubriek = $(this).attr("rubriek");
		Refrech_HTML('/kasboek/kasboekJaarRubriek/' + selectedJaar + '/' + selectedRubriek, '.main_section_A');
		laad_Totalen();
		return false;
	});
};

function kasboeklijst_geladen() {
	$('#namenlijst_click #kasboek').addClass('active');
	Refrech_HTML('/kasboek/kasboekJaarRubriek/0/0', '.main_section_A')
	laad_Totalen();
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
