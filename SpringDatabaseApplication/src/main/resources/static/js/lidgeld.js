let lidgeldId = 0;

async function max_lidgeld_start() {
	reset_grid();
	await Refrech_HTML('/lidgeld/maxlidgeld', '.main_section_A');
};

async function lidgeld_tabel_start() {
	reset_grid();
	await Refrech_HTML('/lidgeld/maxlidgeld', '.main_section_A');
};

function lidgeldselect(id) {
	lidgeldId = id;
	$('tr.active').removeClass('active');
	$('#lidgeld_tabel_body #' + id).addClass('active');
};

