let soortenLeden = {
		id: 0,
		soortleden: ""
};

async function soortenleden_tabel_start() {
	reset_grid();
	await Refrech_HTML('/soortenleden/', '.main_section_A');
};

function soortenledenSelect(row) {
	soortenLeden.id = row.getAttribute('id');
	soortenLeden.soortleden = row.getAttribute('soortenleden');
	$('tr.active').removeClass('active');
	$(row).closest('tr').addClass('active');
};

function soortenleden_tabel_laden(data) {
	let html =``;
	data.forEach((soort, index) => {
		$('#soortenleden_tabel_body').empty();
		html += `<tr class="test" onclick="soortenledenSelect(this)" id="${soort.id}" soortenleden="${soort.soortenleden}">
			<td id="soortenleden_id" style="width: 50px" class="test">${soort.id}</td>
			<td id="soortenleden_soortenleden" class="test">${soort.soortenleden}</td>
		</tr>`;
		
	});
	$('#soortenleden_tabel_body').html(html);
	$('#aantal_soortenleden').html(data.length);
};


/**
 * 
 * 
 * Nieuwe soortenleden
 * 
 * 
 */

function newSoortenleden() {
	console.log("New Soortenleden");
	if($("#editSoortenledenModal").length == 0){
		let data = load_HTML('/soortenleden/editSoortenleden')
		.then((data) => {
			$("#editSoortenledenModalHolder").html(data);
			setup_newSoortenledenModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_newSoortenledenModal();
	};
};

function setup_newSoortenledenModal() {
	$('#modal-titel').html('New soorten!');
	$('#editSoortenledenModal #modal-titel').removeClass('text-danger');
	$('#editSoortenleden_save').show();
	$('#form_body_error').hide();
	$('#form_body').show();
	$('#editSoortenledenModal').one('shown.bs.modal', listener_newSoortenleden_focus);
	$('#editSoortenledenModal').one('hidden.bs.modal', listener_newSoortenleden_hidden);
	$('#editSoortenledenModalForm').one('submit', listener_newSoortenleden_submit);
	$('#editSoortenledenModalForm #soortenleden').prop('readonly', false);
	$("#editSoortenledenModal").modal("show");
};

function listener_newSoortenleden_focus() {
	$("input[name='soortenleden']").focus();
};

function listener_newSoortenleden_hidden() {
	$('#editSoortenledenkModalForm').unbind();
	$('#editSoortenledenModalForm').trigger('reset');
};

function listener_newSoortenleden_submit() {
	let form = $(this);
	let data = post_Form('/soortenleden/save_newSoortenleden', form)
	.then((data) => {
		$("#editSoortenledenModal").modal('toggle');
		soortenleden_tabel_laden(data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};

/**
 * 
 * 
 * Update soortenleden
 * 
 * 
 */


function updateSoortenleden() {
	console.log("Update soortenleden");
	if($("#editSoortenledenModal").length == 0){
		let data = load_HTML('/soortenleden/editSoortenleden')
		.then((data) => {
			$("#editSoortenledenModalHolder").html(data);
			setup_updateSoortenledenModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_updateSoortenledenModal();
		return false;
	};
};

function setup_updateSoortenledenModal() {
	var actief_row = $('tr.active');
	if (actief_row === undefined || actief_row.length == 0) {
		$('#editSoortenledenModal #modal-titel').html('Je hebt geen selectie gemaakt!');
		$('#editSoortenledenModal #modal-titel').addClass('text-danger');
		$('#editSoortenleden_save').hide();
		$('#editSoortenledenModalForm #soortenleden').prop('readonly', true);
	} else {
		$('#editSoortenledenModal #modal-titel').html('Update soort!');
		$('#editSoortenledenModal #modal-titel').removeClass('text-danger');
		$('#editSoortenleden_save').show();
		$('#editSoortenledenModalForm #id').val(soortenLeden.id);
		$('#editSoortenledenModalForm #soortenleden').prop('readonly', false);
		$('#editSoortenledenModalForm #soortenleden').val(soortenLeden.soortleden);
	};
	$('#form_body_error').hide();
	$('#form_body').show();
	$('#editSoortenledenModal').one('shown.bs.modal', listener_updateSoortenleden_focus);
	$('#editSoortenledenModal').one('hidden.bs.modal', listener_updateSoortenleden_hidden);
	$('#editSoortenledenModalForm').one('submit', listener_updateSoortenleden_submit);
	$("#editSoortenledenModal").modal("show");
};

function listener_updateSoortenleden_focus() {
	$("input[name='soortenleden']").focus();
};

function listener_updateSoortenleden_hidden() {
	$('#editSoortenledenModalForm').unbind();
	$('#editSoortenledenModalForm').trigger('reset');
};

function listener_updateSoortenleden_submit() {
	let form = $(this);
	let data = post_Form('/soortenleden/save_updateSoortenleden', form)
	.then((data) => {
		$("#editSoortenledenModal").modal('toggle');
		soortenleden_tabel_laden(data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};

/**
 * 
 * 
 * Delete soortenleden
 * 
 * 
 */

function deleteSoortenleden() {
	console.log("Delete soortenleden");
	if($("#editSoortenledenModal").length == 0){
		let data = load_HTML('/soortenleden/editSoortenleden')
		.then((data) => {
			$("#editSoortenledenModalHolder").html(data);
			setup_deleteSoortenledenModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_deleteSoortenledenModal();
	};
};

async function setup_deleteSoortenledenModal() {
	var actief_row = $('tr.active');
	if (actief_row === undefined || actief_row.length == 0) {
		$('#editSoortenledenModal #modal-titel').html('Je hebt geen selectie gemaakt!');
		$('#editSoortenleden_save').prop('disabled', true);
		$('#editSoortenleden_save').hide();
	} else {
		let exist = await existRecord('/soortenleden/existLedenBySoortenledenId/' + soortenLeden.id);
		if(exist == 'true') {
			$('#editSoortenleden_save').hide();
			$('#form_body_error').show();
			$('#form_body').hide();
			$('#form_body_error_naam').html(soortenLeden.soortleden);
		}else {
			$('#editSoortenleden_save').show();
			$('#form_body_error').hide();
			$('#form_body').show();
		};
		$('#editSoortenledenModal #modal-titel').html('Delete rubriek!');
		$('#editSoortenleden_save').text('Delete rubriek');
		$('#editSoortenledenModalForm #id').val(soortenLeden.id);
		$('#editSoortenledenModalForm #soortenleden').val(soortenLeden.soortleden);
		$('#editSoortenledenModalForm #soortenleden').prop('readonly', true);
	};
	$('#editSoortenledenModal #modal-titel').addClass('text-danger');
	$('#editSoortenledenModal').one('shown.bs.modal', listener_deleteSoortenleden_focus);
	$('#editSoortenledenModal').one('hidden.bs.modal', listener_deleteSoortenleden_hidden);
	$('#editSoortenledenModalForm').one('submit', listener_deleteSoortenleden_submit);
	$("#editSoortenledenModal").modal("show");
};

function listener_deleteSoortenleden_focus() {
	$("input[name='soortenleden']").focus();
};

function listener_deleteSoortenleden_hidden() {
	$('#editSoortenledenModalForm').unbind();
	$('#editSoortenledenModalForm').trigger('reset');
};

function listener_deleteSoortenleden_submit() {
	let form = $(this);
	let data = post_Form('/soortenleden/save_deleteSoortenleden', form)
	.then((data) => {
		$("#editSoortenledenModal").modal('toggle');
		soortenleden_tabel_laden(data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};
