
async function restcontroller_start() {
	reset_grid();
	let data = await load_HTML('/restcontroller');
	document.getElementsByClassName('main_section_A')[0].innerHTML = data; 
}