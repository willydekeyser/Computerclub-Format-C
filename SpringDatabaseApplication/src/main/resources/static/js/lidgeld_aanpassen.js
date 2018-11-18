/**
 *  Lidgeld aanpassen.
 */
/**
 * 
 * 
 * Nieuw lidgeld
 * 
 * 
 */

function newLidgeld(id){
	console.log("New lidgeld: " + id);
	if($("#editLidgeldModal").length == 0){
		let data = load_HTML('/leden/editLidgeld')
		.then((data) => {
			$("#editLidgeldModalHolder").html(data);
			setup_newLidgeldModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_newLidgeldModal();
	};
};

function setup_newLidgeldModal() {
	$('#editLidgeldModal #modal-titel').html('New lidgeld!');
	$('#editLidgeldModal #modal-titel').removeClass('text-danger');
	$('#editLidgeld_save').prop('disabled', false);
	$('#editLidgeld_save').show();
	$('#editLidgeld_save').text('Save lidgeld');
	$('#editLidgeldModal').one('shown.bs.modal', listener_newLidgeld_focus);
	$('#editLidgeldModal').one('hidden.bs.modal', listener_newLidgeld_hidden);
	$('#editLidgeldModalForm').one('submit', listener_newLidgeld_submit);
	$('#editLidgeldModalForm #naam').val(leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam);
	$('#editLidgeldModalForm #naam_id').val(leden_gegevens.id);
	$("#editLidgeldModal").modal("show");
};

function listener_newLidgeld_focus() {
	$("input[name='datum']").focus();
};

function listener_newLidgeld_hidden() {
	$('#editLidgeldModalForm').unbind();
	$('#editLidgeldModalForm').trigger('reset');
};

function listener_newLidgeld_submit() {
	let form =$(this);
	let data = post_Form('/lidgeld/save_newLidgeld', form)
	.then((data) => {
		$("#editLidgeldModal").modal('toggle');
		leden_lidgeld_laden(data)
		leden_gegevens.lidgelden = data;
		console.log('New lidgeld: ' + data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};


/**
 * 
 * 
 * Update lidgeld
 * 
 * 
 */

function updateLidgeld(id){
	console.log("Update lidgeld: " + id + " - " + lidgeldId);
	if($("#editLidgeldModal").length == 0){
		let data = load_HTML('/leden/editLidgeld')
		.then((data) => {
			$("#editLidgeldModalHolder").html(data);
			setup_updateLidgeldModal()
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_updateLidgeldModal()
	};
};

function setup_updateLidgeldModal() {
	var actief_row = $('tr.active');
	if (actief_row === undefined || actief_row.length == 0) {
		$('#editLidgeldModal #modal-titel').html('Je hebt geen selectie gemaakt!');
		$('#editLidgeldModal #modal-titel').addClass('text-danger');
		$('#editLidgeld_save').prop('disabled', true);
		$('#editLidgeld_save').hide();
	} else {
		$('#editLidgeldModal #modal-titel').html('Update lidgeld!');
		$('#editLidgeldModal #modal-titel').removeClass('text-danger');
		$('#editLidgeld_save').prop('disabled', false);
		$('#editLidgeld_save').show();
		$('#editLidgeld_save').text('Update lidgeld');
		let index = actief_row[0].rowIndex;
		let id = actief_row[0].id;
		let datum = leden_gegevens.lidgelden[index - 1].datum;
		let bedrag = leden_gegevens.lidgelden[index - 1].bedrag;
		$('#editLidgeldModalForm #id').val(id);
		$('#editLidgeldModalForm #datum').val(datum);
		$('#editLidgeldModalForm #bedrag').val(bedrag);
	};
	$('#editLidgeldModal').one('shown.bs.modal', listener_updateLidgeld_focus);
	$('#editLidgeldModal').one('hidden.bs.modal', listener_updateLidgeld_hidden);
	$('#editLidgeldModalForm').one('submit', listener_updateLidgeld_submit);
	$('#editLidgeldModalForm #naam').val(leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam);
	$('#editLidgeldModalForm #naam_id').val(leden_gegevens.id);
	$("#editLidgeldModal").modal("show");
};

function listener_updateLidgeld_focus() {
	$("input[name='datum']").focus();
};

function listener_updateLidgeld_hidden() {
	$('#editLidgeldModalForm').unbind();
	$('#editLidgeldModalForm').trigger('reset');
};

function listener_updateLidgeld_submit() {
	let form =$(this);
	let data = post_Form('/lidgeld/save_updateLidgeld', form)
	.then((data) => {
		$("#editLidgeldModal").modal('toggle');
		leden_lidgeld_laden(data);
		leden_gegevens.lidgelden = data;
		console.log('Update lidgeld: ' + data.id);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};


/**
 * 
 * 
 * Delete lidgeld
 * 
 * 
 */

function deleteLidgeld(id){
	console.log("Delete lidgeld: " + id + " - " + lidgeldId);
	if($("#editLidgeldModal").length == 0){
		let data = load_HTML('/leden/editLidgeld')
		.then((data) => {
			$("#editLidgeldModalHolder").html(data);
			setup_deleteLidgeldModal()
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_deleteLidgeldModal()
	};
};

function setup_deleteLidgeldModal() {
	var actief_row = $('tr.active');
	if (actief_row === undefined || actief_row.length == 0) {
		$('#editLidgeldModal #modal-titel').html('Je hebt geen selectie gemaakt!');
		$('#editLidgeld_save').prop('disabled', true);
		$('#editLidgeld_save').hide();
	} else {
		$('#editLidgeldModal #modal-titel').html('Delete lidgeld!');
		$('#editLidgeld_save').prop('disabled', false);
		$('#editLidgeld_save').show();
		$('#editLidgeld_save').text('Delete lidgeld');
		let index = actief_row[0].rowIndex;
		let id = actief_row[0].id;
		let datum = leden_gegevens.lidgelden[index - 1].datum;
		let bedrag = leden_gegevens.lidgelden[index - 1].bedrag;
		$('#editLidgeldModalForm #id').val(id);
		$('#editLidgeldModalForm #datum').val(datum);
		$('#editLidgeldModalForm #bedrag').val(bedrag);
	};
	$('#editLidgeldModal #modal-titel').addClass('text-danger');
	$('#editLidgeldModal').one('shown.bs.modal', listener_deleteLidgeld_focus);
	$('#editLidgeldModal').one('hidden.bs.modal', listener_deleteLidgeld_hidden);
	$('#editLidgeldModalForm').one('submit', listener_deleteLidgeld_submit);
	$('#editLidgeldModalForm #naam').val(leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam);
	$('#editLidgeldModalForm #naam_id').val(leden_gegevens.id);
	$("#editLidgeldModal").modal("show");
};

function listener_deleteLidgeld_focus() {
	$("#editLidgeldModalForm #sluiten").focus();
};

function listener_deleteLidgeld_hidden() {
	$('#editLidgeldModalForm').unbind();
	$('#editLidgeldModalForm').trigger('reset');
};

function listener_deleteLidgeld_submit() {
	let form =$(this);
	let data = post_Form('/lidgeld/save_deleteLidgeld', form)
	.then((data) => {
		$("#editLidgeldModal").modal('toggle');
		leden_lidgeld_laden(data);
		leden_gegevens.lidgelden = data;
		console.log('Delete lidgeld: ' + data.id);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};
