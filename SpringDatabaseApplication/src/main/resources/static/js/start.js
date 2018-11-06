/**
 * 
 */

function start_main() {
	console.log("Start main");
	initSessionMonitor();
	$('#div_script').empty();
	$(document).on('contextmenu', (event) => {
		console.log("Context menu is uitgeschakeld");
		event.preventDefault();
	});
	section_height(80);
	let promises = [Refrech_HTML('/start_menu_logo', '.header_logo'), 
						Refrech_HTML('/start_menu_menu', '.header_menu'), 
						Refrech_HTML('/start_main', '.main_section_A')];
	Promise.all(promises)
	.then(() => {
		start_menu();
	});	
	console.log("END main");
};

function start_menu() {
	$('#header #leden').on('click', () => {
		leden_start()
		.then(() => {
			console.log('Leden geladen');
		})
		.catch((error) => {
			console.error('Error in leden laden: ' + error);
		});
		return false;
	});
	$('#header #leden_tabel').on('click', () => {
		leden_tabel_start()
		.then(() => {
			console.log('Leden_tabel geladen');
		})
		.catch((error) => {
			console.error('Error in leden_tabel laden: ' + error);
		});
		return false;
	});
	$('#header #lidgeld').on('click', () => {
		max_lidgeld_start()
		.then(() => {
			console.log('Max_Lidgeld geladen');
		})
		.catch((error) => {
			console.error('Error in leden_tabel laden: ' + error);
		});
		return false;
	});
	$('#header #lidgeld_tabel').on('click', () => {
		max_lidgeld_start()
		.then(() => {
			console.log('Lidgeld_tabel geladen');
		})
		.catch((error) => {
			console.error('Error in leden_tabel laden: ' + error);
		});
		return false;
	});
	$('#header #kasboek').on('click', () => {
		max_lidgeld_start()
		.then(() => {
			console.log('Kasboek geladen');
		})
		.catch((error) => {
			console.error('Error in leden_tabel laden: ' + error);
		});
		return false;
	});
	$('#header #kasboek_tabel').on('click', () => {
		max_lidgeld_start()
		.then(() => {
			console.log('Kasboek_tabel geladen');
		})
		.catch((error) => {
			console.error('Error in leden_tabel laden: ' + error);
		});
		return false;
	});
	$('#header #agenda').on('click', () => {
		agenda_start()
		.then(() => {
			console.log('agenda geladen');
		})
		.catch((error) => {
			console.error('Error in leden_tabel laden: ' + error);
		});
		return false;
	});
	$('#header #soortenleden_tabel').on('click', () => {
		soortenleden_tabel_start()
		.then(() => {
			console.log('Soortenleden_tabel geladen');
		})
		.catch((error) => {
			console.error('Error in leden_tabel laden: ' + error);
		});
		return false;
	});
	$('#header #rubriek_tabel').on('click', () => {
		rubriek_tabel_start()
		.then(() => {
			console.log('Rubriek_tabel geladen');
		})
		.catch((error) => {
			console.error('Error in leden_tabel laden: ' + error);
		});
		return false;
	});
	$('#header #restcontroller').on('click', () => {
		restcontroller_start()
		.then(() => {
			console.log('Restcontroller geladen');
		})
		.catch((error) => {
			console.error('Error in leden_tabel laden: ' + error);
		});
		return false;
	});
	console.log('Menu header onclick geladen');
};

function start_login() {
	console.log("Login laden");
	Refrech_HTML('/login_main','.main_section_A');
	return false;
	
};

function start_logout() {
	console.log("Logout laden");
	Refrech_HTML('/login_main?logout','.main_section_A');
	return false;
	
};

function initSessionMonitor() {
    $(document).bind('keypress.session', function (ed, e) {
        //console.log('Key press!');
    });
    $(document).bind('mousedown keydown', function (ed, e) {
    	//console.log('Mouse move!');
    });
    
}
