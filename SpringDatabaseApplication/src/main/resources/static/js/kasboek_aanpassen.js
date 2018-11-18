/**
 * Kasboep aanpassen.
 */
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
	let actief_row = $('tr.active');
	if (actief_row === undefined || actief_row.length == 0) {
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
		
		$('#editKasboekModalForm #id').val(Kasboek_gegevens.id);
		$('#editKasboekModalForm #jaartal').val(Kasboek_gegevens.jaartal);
		$('#editKasboekModalForm #rubriek').val(Kasboek_gegevens.rubriekId);
		$('#editKasboekModalForm #omschrijving').val(Kasboek_gegevens.omschrijving);
		$('#editKasboekModalForm #datum').val(Kasboek_gegevens.datum);
		$('#editKasboekModalForm #inkomsten').val(Kasboek_gegevens.inkomsten);
		$('#editKasboekModalForm #uitgaven').val(Kasboek_gegevens.uitgaven);
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
	let actief_row = $('tr.active');
	if (actief_row === undefined || actief_row.length == 0) {
		$('#editKasboekModal #modal-titel').html('Je hebt geen selectie gemaakt!');
		$('#editKasboek_save').prop('disabled', true);
		$('#editKasboek_save').hide();
		
	} else {
		$('#editKasboekModal #modal-titel').html('Delete kasboek!');
		$('#editKasboek_save').text('Delete kasboek');
		$('#editKasboek_save').prop('disabled', false);
		$('#editKasboek_save').show();
		$('#editKasboekModalForm').one('submit', listener_deleteKasboek_submit);
		$('#editKasboekModalForm #id').val(Kasboek_gegevens.id);
		$('#editKasboekModalForm #jaartal').val(Kasboek_gegevens.jaartal);
		$('#editKasboekModalForm #rubriek').val(Kasboek_gegevens.rubriekId);
		$('#editKasboekModalForm #omschrijving').val(Kasboek_gegevens.omschrijving);
		$('#editKasboekModalForm #datum').val(Kasboek_gegevens.datum);
		$('#editKasboekModalForm #inkomsten').val(Kasboek_gegevens.inkomsten);
		$('#editKasboekModalForm #uitgaven').val(Kasboek_gegevens.uitgaven);
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