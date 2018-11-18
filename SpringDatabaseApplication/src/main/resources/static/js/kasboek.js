let kasboekId = 0;
let selectedId = 0;
let selectedJaar = 0;
let selectedRubriek = 0;
let Kasboek_gegevens = {
		id : 0,
		jaar : 0,
		rubriek : {
			id : 0,
			rubriek : ''
		},
		onschrijving : '',
		datum : 0,
		uitgaven : 0,
		inkomsten : 0
};
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
	kasboek_main_laden();
	kasboek_menu_listener();
}

function kasboekSelect(id) {
	kasboekId = $(id).attr('id');
	kasboek_gegevens_refrech();
	//Kasboek_gegevens.id = kasboekId;
	
	$('tr.active').removeClass('active');
	$(id).closest('tr').addClass('active');
};

async function kasboek_gegevens_refrech() {
	let response = await fetch('/kasboek/restcontroller/kasboekbyid/' + kasboekId);
	Kasboek_gegevens = await response.json();
};

function kasboek_menu_listener() {
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

function kasboek_main_laden() {
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
	kasboek_data = data;
	
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