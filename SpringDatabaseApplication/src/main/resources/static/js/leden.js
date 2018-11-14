let selectedLidId = 0;
let selectedSoortId = 0;
let aantal_leden = 0;
let change_soort = false;
let change_naam = false;
let leden_gegevens = "";

/**
 * 
 * 
 * Leden 
 * 
 * 
 */

async function leden_start() {
	
	console.log('------------------------------------------------------------');
	console.log('Menu leden onclick');
	reset_grid();
	menu_height(3);
	selectedSoortId = 1;
	selectedLidId = 0;
	await Promise.all([
		Refrech_HTML('/leden/leden_menu/1', '.menu_header'),
		Refrech_HTML('/leden/namenlijst/1/0', '.menu_main')
	]);
	leden_change_soort();
	leden_namenlijst_geladen();
	if (aantal_leden == 0){
		$('#lid_main').html('<h1>Geen leden gevonden!</h1>');
	} else {
		leden_namenlijst_onclick()
		await Refrech_HTML('/leden/leden_main/' + selectedLidId, '.main_section_A');
		leden_gegevens_refrech();
	};	
	console.log("END Leden onclick - Soort ID = " + selectedSoortId + " - ID = " + selectedLidId);
	console.log('------------------------------------------------------------');
};

function leden_menu_geladen(soortId, lidid) {
	selectedSoortId = soortId;
	selectedLidId = lidId;
	console.log("Selected soort & lid: " + selectedSoortId + " - " + selectedLidId);
	leden_namenlijst(selectedSoortId, selectedLidId);
};

async function leden_namenlijst(soortId, lidId) {
	selectedSoortId = soortId;
	selectedLidId = lidId;
	await Refrech_HTML('/leden/namenlijst/' + soortId + '/' + lidId , '.menu_main');
	//await leden_namenlijst_refrech();
	leden_namenlijst_geladen();
	if (aantal_leden == 0){
		$('#leden_main #geen_leden_gevonden').css("display", "block");
		$('#leden_main #leden_gevonden').css("display", "none");
	} else {
		$('#leden_main #geen_leden_gevonden').css("display", "none");
		$('#leden_main #leden_gevonden').css("display", "block");
		leden_namenlijst_onclick();
		await leden_gegevens_refrech();
	};
};

async function leden_gegevens_new() {
	await Refrech_HTML('/leden/leden_main/' + selectedLidId, '#lid_main');
};

async function leden_namenlijst_refrech() {
	console.log('Leden namenlijst refrech: ' + selectedSoortId);
	let response = await fetch('/leden/restcontroller/ledennamenlijstbyid/' + selectedSoortId);
	let data = await response.json();
	leden_namenlijst_laden(data);
};

async function leden_gegevens_refrech() {
	let response = await fetch('/leden/restcontroller/ledenbyid/' + selectedLidId);
	leden_gegevens = await response.json();
	await leden_gegevens_laden(leden_gegevens);
	await leden_lidgeld_laden(leden_gegevens.lidgelden);
};

async function leden_lidgeld_refrech() {
	console.log('Lidgeld refrech: ');
	let response = await fetch('/lidgeld/restcontroller/lidgeld/' + selectedLidId);
	let data = await response.json();
	leden_gegevens.lidgelden = data;
	leden_lidgeld_laden(data);
};

function leden_namenlijst_laden(data) {
	let html = ``;
	data.forEach((namenlijst, index) => {
		html += `<li id="${namenlijst.id}" class="list-group-item namenlijst ${namenlijst.id == selectedLidId ? `active` : ``}">${index + 1} - ${namenlijst.naam}</li>`;
	});
	$('#namenlijst_click').html(html);
};

function leden_gegevens_laden(data) {
	$('#main_id').text('Lid: ' + data.id);
	$('#id').text(data.id);
	$('#naam').text(data.voornaam + ' ' + data.familienaam);
	$('#adres').text(data.straat + ' ' + data.nr);
	$('#gemeente').text(data.postnr + ' ' + data.gemeente);
	$('#telefoon').text(data.telefoonnummer);
	$('#gsm').text(data.gsmnummer);
	$('#email').html('<a href="mailto:' + data.emailadres + '">' + data.emailadres + '</a>');
	$('#website').html('<a href="' + data.webadres + '" target="_blank">' + data.webadres + '</a>');
	$('#inschrijving').text(getFormattedDate(data.datumlidgeld));
	$('#soort').text(data.soortenleden.soortenleden);
	$('#ontvangmail').attr('checked', data.ontvangMail);
	$('#mailvlag').attr('checked', data.mailVlag);
	$('#leden_newlid').attr('onclick', 'newLid();');
	$('#leden_updatelid').attr('onclick', 'updateLid(' + data.id + ');');
	$('#leden_deletelid').attr('onclick', 'deleteLid(' + data.id + ');');
	$('#lidgeld_newlidgeld').attr('onclick', 'newLidgeld(' + data.id + ');');
	$('#lidgeld_updatelidgeld').attr('onclick', 'updateLidgeld(' + data.id + ');');
	$('#lidgeld_deletelidgeld').attr('onclick', 'deleteLidgeld(' + data.id + ');');
};

function leden_lidgeld_laden(data) {
	let html = ``;
	$('#lidgeld_tabel_body').empty();
	data.forEach((lidgeld, index) => {
		html += `<tr class="test" onclick="lidgeldselect(${lidgeld.id})" id="${lidgeld.id}">
			<td style="width: 50px" class="right">${lidgeld.id}</td>
			<td class="right">${getFormattedDate(lidgeld.datum)}</td>
			<td class="right">${getFormattedEuro(lidgeld.bedrag)}</td>
			</tr>`;
	});
	$('#lidgeld_tabel_body').html(html);
};

function leden_namenlijst_geladen() {
	aantal_leden = $('#namenlijst_click li').length;
	if (aantal_leden == 0){
		return;
	};
	if($('#namenlijst_click #' + selectedLidId).length == 0){
		selectedLidId = 0;
	};
	if(selectedLidId != 0) {
		$('#namenlijst_click li').closest('#' + selectedLidId).addClass("active");
		let lijst = document.getElementsByClassName('menu_main')[0];
		console.log('lijst offset ' + lijst.offsetTop + ' - ' + lijst.scrollTopMax + ' - ' + lijst.offsetHeight  )
		let lijst_item = document.getElementsByClassName('active')[0];
		console.log('lijst item offsetTop ' + lijst_item.offsetTop)
		lijst.scrollTop = (lijst_item.offsetTop - 100);
	} else {
		$('#namenlijst_click li').first().addClass("active");
		selectedLidId = $('#namenlijst_click li').first().attr('id');
	};
};

function leden_namenlijst_onclick() {
	$("#namenlijst_click li").on('click', function() {
		$('ol li.active').removeClass('active');
		$(this).closest('li').addClass('active');
		selectedLidId = $(this).attr("id");
		console.log("Namenlijst select - Selected id: " + selectedLidId);
		leden_gegevens_refrech();
	});
};

function leden_change_soort() {
	$("#select_leden").on('change', function(){
		selectedSoortId = $('#select_leden option:selected').val();
		console.log('Change soort leden ' + selectedSoortId);
		//selectedLidId = 0;
		leden_namenlijst(selectedSoortId, selectedLidId);
	});
};


/**
 * 
 * 
 * Leden tabel
 * 
 * 
 */

async function leden_tabel_start() {
	reset_grid();
	section_height(30);
	await Refrech_HTML('/leden/leden_tabel', '.main_section_B');
	await Refrech_HTML('/leden/leden_tabel_ledenlijst/1', '.main_section_A');
	await Refrech_HTML('/leden/leden_tabel_ById/4', '.main_section_B');
};

function leden_tabel_change_soort() {
	$("#select_leden").on('change', function(){
		selectedSoortId = $('#select_leden option:selected').val();
		console.log("Select soort leden: " + selectedSoortId);
		leden_tabel_ledenlijst(selectedSoortId, 0);
	});
};

function leden_tabel_menu(soortid) {
	console.log("Leden tabel menu laden: " + soortid);
	$.ajax({
		url : "/leden/leden_tabel_menu/" + soortid,
		success : function(data) {
			$("#menu").html(data);
		}
	})
	$(document).contextmenu(function(event){
		event.preventDefault();
	});
};

function leden_tabel_menu_geladen(id) {
	console.log("Leden tabel lijst laden: " + id);
	$.ajax({
		url : "/leden/leden_tabel_ledenlijst/" + id,
		success : function(data) {
			$("#leden_tabel_ledenlijst").html(data);
		}
	})
};

function leden_tabel_ledenlijst(soortId, lidId) {
	selectedSoortId = soortId;
	selectedLidId = lidId;
	console.log("Leden ledenlijst laden: " + selectedSoortId + " - " + selectedLidId);
	$.ajax({
		url : "/leden/leden_tabel_ledenlijst/" + selectedSoortId,
		success : function(data) {
			$("#leden_tabel_ledenlijst").html(data);
		}
	})
};

function leden_tabel_ledenlijst_geladen() {
	leden_tabel_change_soort();
	
	var id = $("#leden_tabel_ledenlijst").find("td:first").attr('id')
	ledenbyid(id);
	console.log("Leden menu & ledenlijst geladen: " + id);
}

function ledenbyid(id) {
	console.log("Leden detail: " + id);
	Refrech_HTML('/leden/leden_tabel_ById/' + id, '.main_section_B');
};

/**
 * 
 * 
 * Nieuwe leden
 * 
 * 
 */

function newLid() {
	console.log("New lid");
	if($("#editLidModal").length == 0){
		let data = load_HTML('/leden/editLid')
		.then((data) => {
			$("#editLidModalHolder").html(data);
			setup_newLidModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_newLidModal();
	};
	
};

function setup_newLidModal() {
	$('#modal-titel').html('New lid!');
	$('#editLidModal').one('shown.bs.modal', listener_newLid_focus);
	$('#editLidModal').one('hidden.bs.modal', listener_newLid_hidden);
	$('#editLidModalForm').one('submit', listener_newLid_submit);
	$("#editLidModal").modal("show");
};

function listener_newLid_focus() {
	$("input[name='voornaam']").focus();
};

function listener_newLid_hidden() {
	$('#editLidModalForm').unbind();
	$('#editLidModalForm').trigger('reset');
};

function listener_newLid_submit() {
	let form = $(this);
	let data = post_Form('/leden/save_newLid', form)
	.then((data) => {
		selectedLidId = data.id;
		selectedSoortId = data.soortenleden.id;
		$("#editLidModal").modal('toggle');
		$('#select_leden').val(selectedSoortId);
		$('#select_leden').trigger('change');
		console.log('New Lid: ' + selectedSoortId + ' - ' + selectedLidId + ' ' + data.voornaam + ' ' + data.familienaam);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};

/**
 * 
 * 
 * Update leden
 * 
 * 
 */

function updateLid(id) {
	console.log("Update lid: " + id);
	if($("#editLidModal").length == 0){
		let data = load_HTML('/leden/editLid')
		.then((data) => {
			$("#editLidModalHolder").html(data);
			setup_updateLidModal();
			updateLid_Formvullen();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_updateLidModal();
		updateLid_Formvullen();
	};
};

function setup_updateLidModal() {
	change_naam = false;
	change_soort = false;
	$('#modal-titel').html('Update lid!');
	$('#editLidModal').one('shown.bs.modal', listener_newLid_focus);
	$('#editLidModal').one('hidden.bs.modal', listener_newLid_hidden);
	$('#editLidModal #voornaam').one('input', listener_change_naam);
	$('#editLidModal #familienaam').one('input', listener_change_naam);
	$('#editLidModal #soortlid').one('change', listener_change_soortlid);
	$('#editLidModalForm').one('submit', listener_updateLid_submit);
	$("#editLidModal").modal("show");
};

function updateLid_Formvullen(){
	console.log('ID: ' + leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam);
	$('#editLidModalForm #id').val(leden_gegevens.id);
	$('#editLidModalForm #voornaam').val(leden_gegevens.voornaam);
	$('#editLidModalForm #familienaam').val(leden_gegevens.familienaam);
	$('#editLidModalForm #straat').val(leden_gegevens.straat);
	$('#editLidModalForm #nr').val(leden_gegevens.nr);
	$('#editLidModalForm #postnr').val(leden_gegevens.postnr);
	$('#editLidModalForm #gemeente').val(leden_gegevens.gemeente);
	$('#editLidModalForm #emailadres').val(leden_gegevens.emailadres);
	$('#editLidModalForm #webadres').val(leden_gegevens.webadres);
	$('#editLidModalForm #telefoonnummer').val(leden_gegevens.telefoonnummer);
	$('#editLidModalForm #gsmnummer').val(leden_gegevens.gsmnummer);
	$('#editLidModalForm #datumlidgeld').val(leden_gegevens.datumlidgeld);
	$('#editLidModalForm #soortlid').val(leden_gegevens.soortenleden.id);
	$('#editLidModalForm #ontvangmail').prop('checked', leden_gegevens.ontvangMail);
	$('#editLidModalForm #mailvlag').prop('checked', leden_gegevens.mailVlag);
};

function listener_change_naam() {
	change_naam = true;
};

function listener_change_soortlid() {
	change_soort = true;
};

function listener_updateLid_submit() {
	let form = $(this);
	let data = post_Form('/leden/save_updateLid', form, change_soort, change_naam)
	.then((data) => {
		selectedLidId = data.id;
		selectedSoortId = data.soortenleden.id;
		$("#editLidModal").modal('toggle');
		if(change_soort) {
			$('#select_leden').val(selectedSoortId);
			$('#select_leden').trigger('change');
		} else if(change_naam) {
			leden_namenlijst_refrech();
			leden_gegevens_laden(data);
			leden_gegevens = data;
		} else {
			leden_gegevens_laden(data);
			leden_gegevens = data;
		}
		console.log('Update Lid: ' + selectedSoortId + ' - ' + selectedLidId + ' ' + data.voornaam + ' ' + data.familienaam);
	})
	.catch((error) => {
	console.log('FOUT: ' + error);
	});
	return false;
};

/**
 * 
 * 
 * Delete leden
 * 
 * 
 */

function deleteLid(id) {
	console.log("Delete lid: " + id);
	if($("#deleteLidModal").length == 0){
		let data = load_HTML('/leden/deleteLid')
		.then((data) => {
			$("#deleteLidModalHolder").html(data);
			setup_deleteLidModal();
		})
		.catch((error) => {
			console.log('ERROR: ' + error);
		});
	} else {
		setup_deleteLidModal();
	};
};

function setup_deleteLidModal() {
	if(leden_gegevens.lidgelden.length == 0){
		document.getElementById('delete_modal_footer').hidden = false;
		document.getElementById('delete_modal_footer_error').hidden = true;
		document.getElementById('delete_form_body').hidden = false;
		document.getElementById('delete_form_body_error').hidden = true;
		document.getElementById('delete_id').value = leden_gegevens.id;
		document.getElementById('delete_naam').value = leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam;
		$('#deleteLidModal').one('shown.bs.modal', listener_deleteLid_focus);
		$('#deleteLidModalForm').one('submit', listener_deleteLid_submit);
	} else {
		document.getElementById('delete_modal_footer').hidden = true;
		document.getElementById('delete_modal_footer_error').hidden = false;
		document.getElementById('delete_form_body').hidden = true;
		document.getElementById('delete_form_body_error').hidden = false;
		document.getElementById('delete_form_body_error_naam').innerHTML = leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam;
	}
	document.getElementById('delete-modal-titel').innerHTML = 'Verwijder lid!';
	$("#deleteLidModal").modal("show");
};

function listener_deleteLid_focus() {
	$("#deleteLidModal #sluiten").focus();
};

function listener_deleteLid_submit() {
	var form = $(this);
	let data = post_Form('/leden/save_deleteLid', form)
	.then((data) => {
		selectedLidId = data;
		$("#deleteLidModal").modal('toggle');
		$('#select_leden').val(selectedSoortId);
		$('#select_leden').trigger('change');
		console.log('Delete Lid: ' + selectedSoortId + ' - ' + selectedLidId);
	})
	.catch((error) => {
	console.log('FOUT: ' + error);
	});
	return false;
};

/**
 * 
 * 
 * Test
 * 
 * 
 */

function test() {
	$('#select_leden').val(5);
	$('#select_leden').trigger('change');
};




