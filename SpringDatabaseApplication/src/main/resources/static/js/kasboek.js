let kasboekId = 0;
let selectedId = 0;
let selectedJaar = 0;
let selectedRubriek = 0;
let kasboek_gegevens = "";
let change_jaar = false;
let change_rubriek = false;
let jaar = 0;
let rubriek = 0;


async function kasboek_start() {
	console.log('------------------------------------------------------------');
	console.log('Menu kasboek onclick');
	reset_grid();
	menu_height(3);
	
	await Refrech_HTML('/kasboek/', '.menu_main');
	kasboeklijst_geladen();
	kasboeklijst();
}

/**
 * 
 * 
 * New kasboek
 * 
 * 
 */

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
	change_jaar = false;
	change_rubriek = false;
	$('#modal-titel').html('New kasboek!');
	$('#editKasboekModal #modal-titel').removeClass('text-danger');
	$('#editKasboek_save').prop('disabled', false);
	$('#editKasboek_save').show();
	$('#editKasboek_save').text('Save kasboek');
	$('#editKasboekModal').one('shown.bs.modal', listener_newKasboek_focus);
	$('#editKasboekModal').one('hidden.bs.modal', listener_newkasboek_hidden);
	$('#editKasboekModalForm').one('submit', listener_newKasboek_submit);
	$('#editKasboekModalForm #jaartal').one('input', listener_change_jaar);
	$('#editKasboekModalForm #rubriek').one('input', listener_change_rubriek);
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
	let data = post_Form('/kasboek/save_newKasboek/' + selectedJaar + '/' + selectedRubriek, form)
	.then((data) => {
		$("#editKasboekModal").modal('toggle');
		kasboek_tabel_refrech(data)
		console.log('New Kasboek');
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};


function listener_change_jaar() {
	change_jaar = true;
};

function listener_change_rubriek() {
	change_rubriek = true;
};

/**
 * 
 * 
 * Update kasboek
 * 
 * 
 */

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
	if (kasboek_gegevens == "") {
		$('#editKasboekModal #modal-titel').html('Je hebt geen selectie gemaakt!');
		$('#editKasboekModal #modal-titel').addClass('text-danger');
		$('#editKasboek_save').prop('disabled', true);
		$('#editKasboek_save').hide();
		
	} else {
		change_jaar = false;
		change_rubriek = false;
		$('#editKasboekModal #modal-titel').html('Update kasboek!');
		$('#editKasboekModal #modal-titel').removeClass('text-danger');
		$('#editKasboek_save').text('Save kasboek');
		$('#editKasboek_save').prop('disabled', false);
		$('#editKasboek_save').show();
		$('#editKasboekModalForm').one('submit', listener_updateKasboek_submit);
		$('#editKasboekModalForm #jaartal').one('input', listener_change_jaar);
		$('#editKasboekModalForm #rubriek').one('input', listener_change_rubriek);
		
		$('#editKasboekModalForm #id').val(kasboek_gegevens.id);
		$('#editKasboekModalForm #jaartal').val(kasboek_gegevens.jaartal);
		$('#editKasboekModalForm #rubriek').val(kasboek_gegevens.rubriekId);
		$('#editKasboekModalForm #omschrijving').val(kasboek_gegevens.omschrijving);
		$('#editKasboekModalForm #datum').val(kasboek_gegevens.datum);
		$('#editKasboekModalForm #inkomsten').val(kasboek_gegevens.inkomsten);
		$('#editKasboekModalForm #uitgaven').val(kasboek_gegevens.uitgaven);
	}
	$('#editKasboekModal').one('shown.bs.modal', listener_updateKasboek_focus);
	$('#editKasboekModal').one('hidden.bs.modal', listener_updatekasboek_hidden);
	$("#editKasboekModal").modal("show");
};

function listener_updateKasboek_focus() {
	$("input[name='jaartal']").focus();
};

function listener_updatekasboek_hidden() {
	$('#editKasboekModalForm').unbind();
	$('#editKasboekModalForm').trigger('reset');
};

function listener_updateKasboek_submit() {
	let form = $(this);
	jaar = $('#editKasboekModalForm #jaartal').val();
	rubriek = $('#editKasboekModalForm #rubriek').val();
	let data = post_Form('/kasboek/save_updateKasboek/' + selectedJaar + '/' + selectedRubriek + '/' + change_jaar + '/' + jaar, form)
	.then((data) => {
		$("#editKasboekModal").modal('toggle');
		kasboek_tabel_refrech(data)
		console.log('Update Kasboek');
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};

/**
 * 
 * 
 * Delete kasboek
 * 
 * 
 */

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
	if (kasboek_gegevens == "") {
		$('#editKasboekModal #modal-titel').html('Je hebt geen selectie gemaakt!');
		$('#editKasboek_save').prop('disabled', true);
		$('#editKasboek_save').hide();
		
	} else {
		$('#editKasboekModal #modal-titel').html('Delete kasboek!');
		$('#editKasboek_save').text('Delete kasboek');
		$('#editKasboek_save').prop('disabled', false);
		$('#editKasboek_save').show();
		$('#editKasboekModalForm').one('submit', listener_deleteKasboek_submit);
		$('#editKasboekModalForm #id').val(kasboek_gegevens.id);
		$('#editKasboekModalForm #jaartal').val(kasboek_gegevens.jaartal);
		$('#editKasboekModalForm #rubriek').val(kasboek_gegevens.rubriekId);
		$('#editKasboekModalForm #omschrijving').val(kasboek_gegevens.omschrijving);
		$('#editKasboekModalForm #datum').val(kasboek_gegevens.datum);
		$('#editKasboekModalForm #inkomsten').val(kasboek_gegevens.inkomsten);
		$('#editKasboekModalForm #uitgaven').val(kasboek_gegevens.uitgaven);
	}
	$('#editKasboekModal #modal-titel').addClass('text-danger');
	$('#editKasboekModal').one('shown.bs.modal', listener_deleteKasboek_focus);
	$('#editKasboekModal').one('hidden.bs.modal', listener_deletekasboek_hidden);
	$("#editKasboekModal").modal("show");
};

function listener_deleteKasboek_focus() {
	$("input[name='jaartal']").focus();
};

function listener_deletekasboek_hidden() {
	$('#editKasboekModalForm').unbind();
	$('#editKasboekModalForm').trigger('reset');
};

function listener_deleteKasboek_submit() {
	let form = $(this);
	let data = post_Form('/kasboek/save_deleteKasboek/' + selectedJaar + '/' + selectedRubriek, form)
	.then((data) => {
		$("#editKasboekModal").modal('toggle');
		kasboek_tabel_refrech(data)
		console.log('Delete Kasboek');
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
	
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
	kasboek_gegevens = "";
	let data = await fetch_JSON('/kasboek/restcontroller/kasboekTotalen/' + selectedJaar + '/' + selectedRubriek);
	let html = ``;
	console.log('Totalen: ' + data.Jaar);
	$('.main_section_B').html(`<p>Jaar: ${data.Jaar} Rubriek: ${data.Rubriek}</p> 
	Inkomsten: ${getFormattedEuro(data.Totalen[0].Inkomsten)} Uitgaven: ${getFormattedEuro(data.Totalen[0].Uitgaven)} Totaal: ${getFormattedEuro(data.Totalen[0].Totaal)}</br>
	Inkomsten: ${getFormattedEuro(data.Totalen[1].Inkomsten)} Uitgaven: ${getFormattedEuro(data.Totalen[1].Uitgaven)} Totaal: ${getFormattedEuro(data.Totalen[1].Totaal)}`);
};

async function kasboek_menu_refrech() {
	let html = ``;
	let data = await fetch_JSON('/kasboek/restcontroller/kasboekmenu');
	html += `<div><ol class="menu_lijst" id="namenlijst_click">
		<li class="menu_lijst_item kasboeklijst_click active" id="kasboek" jaar="0" rubriek="0">
			<span class="menu_lijst_item_tekst">Kasboek</span>
		</li>`;
	data.forEach((jaren, index) => {
		if (jaren.jaartal == jaar) {
			html += `<li class="menu_lijst_item kasboeklijst_click active" id="${index + 1}" jaar="${jaren.jaartal}" rubriek="0">
				<span class="menu_lijst_item_tekst">${jaren.jaartal}</span>
			<ol class="sub_menu_lijst menuopen" id="jaartal${index + 1}" style="display: block;">`;
		} else {
			html += `<li class="menu_lijst_item kasboeklijst_click" id="${index + 1}" jaar="${jaren.jaartal}" rubriek="0">
				<span class="menu_lijst_item_tekst">${jaren.jaartal}</span>
			<ol class="sub_menu_lijst" id="jaartal${index + 1}" style="display: none;">`;
		}
		let rubrieken = jaren.rubriek;
		rubrieken.forEach((rubriek, index) => {
			html += `<li class="sub_menu_lijst_item sub_kasboeklijst_click" id="${rubriek.id}" jaar="${jaren.jaartal}" rubriek="${rubriek.id}">${rubriek.rubriek}</li>`;
		})
		html += `</ol></li>`;
	});
	html += `</ol</div>`;
	$('.menu_main').html(html);
	kasboeklijst();
};

function kasboek_tabel_refrech(data) {
	let html = ``;
	$('#kasboek_tabel_body').empty();
	data.forEach((kasboek, index) => {
		html += `<tr id="${kasboek.id}" class="test" onclick="kasboekSelect(this)">
			<td class="test" style="width: 50px">${index + 1}</td>
			<td class="test" style="width: 50px">${kasboek.id}</td>
			<td class="test" style="width: 50px">${kasboek.jaartal}</td>
			<td class="test" style="width: 200px">${kasboek.rubriek.rubriek}</td>
			<td class="test">${kasboek.omschrijving}</id>
			<td class="test" style="width: 80px">${getFormattedDate(kasboek.datum)}</td>
			<td class="test" style="width: 80px">${getFormattedEuro(kasboek.uitgaven)}</td>
			<td class="test" style="width: 80px">${getFormattedEuro(kasboek.inkomsten)}</td>`;
	});
	$('#kasboek_tabel_body').html(html);
	laad_Totalen();
	if (change_jaar || change_rubriek) {
		kasboek_menu_refrech();
	}
	
}