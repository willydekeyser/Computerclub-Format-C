var Rubriek_gegevens = {
		id : 0,
		rubriek : ''
};

async function rubriek_tabel_start() {
	reset_grid();
	await Refrech_HTML('/rubriek/', '.main_section_A');
};

function rubriekSelect(row) {
	Rubriek_gegevens.id = row.getAttribute('id');
	Rubriek_gegevens.rubriek = row.getAttribute('rubriek');
	console.log("rubriekSelect: " + rubriek.id);
	$('tr.active').removeClass('active');
	$(row).closest('tr').addClass('active');
};

function rubriek_tabel_laden(data) {
	let html =``;
	data.forEach((rubriek, index) => {
		$('#rubriek_tabel_body').empty();
		html += `<tr class="test" onclick="rubriekSelect(this)" id="${rubriek.id}" rubriek="${rubriek.rubriek}">
			<td id="rubriek_id" style="width: 50px" class="test">${rubriek.id}</td>
			<td id="rubriek_rubriek" class="test">${rubriek.rubriek}</td>
		</tr>`;
		
	});
	$('#rubriek_tabel_body').html(html);
	$('#aantal_rubrieken').html(data.length);
};


/**
 * 
 * 
 * Nieuwe rubrieken
 * 
 * 
 */

function newRubriek() {
	console.log("New rubriek");
	if($("#editRubriekModal").length == 0){
		let data = load_HTML('/rubriek/editRubriek')
		.then((data) => {
			$("#editRubriekModalHolder").html(data);
			setup_newRubriekModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_newRubriekModal();
	};
};

function setup_newRubriekModal() {
	$('#modal-titel').html('New rubriek!');
	$('#editRubriekModal #modal-titel').removeClass('text-danger');
	$('#editRubriek_save').show();
	$('#form_body_error').hide();
	$('#form_body').show();
	$('#editRubriekModal').one('shown.bs.modal', listener_newRubriek_focus);
	$('#editRubriekModal').one('hidden.bs.modal', listener_newRubriek_hidden);
	$('#editRubriekModalForm').one('submit', listener_newRubriek_submit);
	$('#editRubriekModalForm #rubriek').prop('readonly', false);
	$("#editRubriekModal").modal("show");
};

function listener_newRubriek_focus() {
	$("input[name='rubriek']").focus();
};

function listener_newRubriek_hidden() {
	$('#editRubriekModalForm').unbind();
	$('#editRubriekModalForm').trigger('reset');
};

function listener_newRubriek_submit() {
	let form = $(this);
	let data = post_Form('/rubriek/save_newRubriek', form)
	.then((data) => {
		$("#editRubriekModal").modal('toggle');
		rubriek_tabel_laden(data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};

/**
 * 
 * 
 * Update rubrieken
 * 
 * 
 */

function updateRubriek() {
	console.log("Update rubriek");
	if($("#editRubriekModal").length == 0){
		let data = load_HTML('/rubriek/editRubriek')
		.then((data) => {
			$("#editRubriekModalHolder").html(data);
			setup_updateRubriekModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_updateRubriekModal();
		return false;
	};
};

function setup_updateRubriekModal() {
	let actief_row = $('tr.active');
	if (actief_row === undefined || actief_row.length == 0) {
		$('#editRubriekModal #modal-titel').html('Je hebt geen selectie gemaakt!');
		$('#editRubriekModal #modal-titel').addClass('text-danger');
		$('#editRubriek_save').hide();
		$('#editRubriekModalForm #rubriek').prop('readonly', true);
	} else {
		$('#editRubriekModal #modal-titel').html('Update rubriek!');
		$('#editRubriekModal #modal-titel').removeClass('text-danger');
		$('#editRubriek_save').show();
		$('#editRubriekModalForm #id').val(Rubriek_gegevens.id);
		$('#editRubriekModalForm #rubriek').prop('readonly', false);
		$('#editRubriekModalForm #rubriek').val(Rubriek_gegevens.rubriek);
	};
	$('#form_body_error').hide();
	$('#form_body').show();
	$('#editRubriekModal').one('shown.bs.modal', listener_updateRubriek_focus);
	$('#editRubriekModal').one('hidden.bs.modal', listener_updateRubriek_hidden);
	$('#editRubriekModalForm').one('submit', listener_updateRubriek_submit);
	$("#editRubriekModal").modal("show");
};

function listener_updateRubriek_focus() {
	$("input[name='rubriek']").focus();
};

function listener_updateRubriek_hidden() {
	$('#editRubriekModalForm').unbind();
	$('#editRubriekModalForm').trigger('reset');
};

function listener_updateRubriek_submit() {
	let form = $(this);
	let data = post_Form('/rubriek/save_updateRubriek', form)
	.then((data) => {
		$("#editRubriekModal").modal('toggle');
		rubriek_tabel_laden(data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};

/**
 * 
 * 
 * Delete rubrieken
 * 
 * 
 */

function deleteRubriek() {
	console.log("Delete rubriek");
	if($("#editRubriekModal").length == 0){
		let data = load_HTML('/rubriek/editRubriek')
		.then((data) => {
			$("#editRubriekModalHolder").html(data);
			setup_deleteRubriekModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_deleteRubriekModal();
	};
};

async function setup_deleteRubriekModal() {
	var actief_row = $('tr.active');
	if (actief_row === undefined || actief_row.length == 0) {
		$('#editRubriekModal #modal-titel').html('Je hebt geen selectie gemaakt!');
		$('#editRubriek_save').prop('disabled', true);
		$('#editRubriek_save').hide();
	} else {
		let exist = await existRecord('/rubriek/existKasboekByRubriekId/' + Rubriek_gegevens.id);
		if(exist == 'true') {
			$('#editRubriek_save').hide();
			$('#form_body_error').show();
			$('#form_body').hide();
			$('#form_body_error_naam').html(Rubriek_gegevens.rubriek);
		}else {
			$('#editRubriek_save').show();
			$('#form_body_error').hide();
			$('#form_body').show();
		};
		$('#editRubriekModal #modal-titel').html('Delete rubriek!');
		$('#editRubriek_save').text('Delete rubriek');
		$('#editRubriekModalForm #id').val(Rubriek_gegevens.id);
		$('#editRubriekModalForm #rubriek').val(Rubriek_gegevens.rubriek);
		$('#editRubriekModalForm #rubriek').prop('readonly', true);
	};
	$('#editRubriekModal #modal-titel').addClass('text-danger');
	$('#editRubriekModal').one('shown.bs.modal', listener_deleteRubriek_focus);
	$('#editRubriekModal').one('hidden.bs.modal', listener_deleteRubriek_hidden);
	$('#editRubriekModalForm').one('submit', listener_deleteRubriek_submit);
	$("#editRubriekModal").modal("show");
};

function listener_deleteRubriek_focus() {
	$("input[name='rubriek']").focus();
};

function listener_deleteRubriek_hidden() {
	$('#editRubriekModalForm').unbind();
	$('#editRubriekModalForm').trigger('reset');
};

function listener_deleteRubriek_submit() {
	let form = $(this);
	let data = post_Form('/rubriek/save_deleteRubriek', form)
	.then((data) => {
		$("#editRubriekModal").modal('toggle');
		rubriek_tabel_laden(data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};
