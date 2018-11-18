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
