let SoortenLeden_gegevens = {
		id: 0,
		soortleden: ""
};

async function soortenleden_tabel_start() {
	reset_grid();
	await Refrech_HTML('/soortenleden/', '.main_section_A');
};

function soortenledenSelect(row) {
	SoortenLeden_gegevens.id = row.getAttribute('id');
	SoortenLeden_gegevens.soortleden = row.getAttribute('soortenleden');
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

