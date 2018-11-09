/**
 * Fetch functions
 */

function reset_grid() {
	section_height(85);
	document.getElementsByClassName('main_section_A')[0].innerHTML = ""; 
	document.getElementsByClassName('main_section_B')[0].innerHTML = "";
	document.getElementsByClassName('menu_header')[0].innerHTML = ""; 
	document.getElementsByClassName('menu_main')[0].innerHTML = "";
};

function section_height(height) {
	document.getElementsByClassName('main_section_A')[0].style.flexBasis = height + '%';
	document.getElementsByClassName('main_section_B')[0].style.flexBasis = 100 - height + '%';
}

function menu_height(height) {
	document.getElementsByClassName('menu_header')[0].style.flexBasis = height + 'rem';
	document.getElementsByClassName('menu_main')[0].style.flexBasis = 100 - height + 'rem';
}

async function Refrech_HTML(url, div) {
	console.log('Refrech HTML: ' + url);
	let response = await fetch(url, {
		headers: {
			"X-Requested-With" : "XMLHttpRequest"
		}
	});
	if (!response.ok || !response.status == 200 || response.redirected) {
		console.log('error: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
		window.open('/', '_self');
		throw Error(response.status);
	}
	let tekst = await response.text();
	$(div).html(tekst);
	console.log('ok: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
	console.log(div + ' geladen - ' + url);
	return 'OK';
};

async function existRecord(url) {
	let response = await fetch(url);
	if (!response.ok || !response.status == 200 || response.redirected) {
		console.log('error: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
		window.open('/', '_self');
		throw Error(response.status);
	};
	return await response.text();
}

async function load_HTML(url) {
	console.log('Load HTML: ' + url);
	let response = await fetch(url);
	if (!response.ok || !response.status == 200 || response.redirected) {
		console.log('error: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
		window.open('/', '_self');
		throw Error(response.status);
	};
	console.log('ok: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
	return await response.text();
};

async function Load_JSON(url, div) {
	console.log('Load JSON: ' + url);
	let response = await fetch(url);
	if (!response.ok || !response.status == 200 || response.redirected) {
		console.log('error: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
		window.open('/', '_self');
		throw Error(response.status);
	};
	let data = await response.text();
	let myJson = await JSON.stringify(data);
	console.log(div + ' - ' + data);
	console.log(myJson);
	document.getElementById(div).innerHTML = data; 
	return 'OK';
};

async function fetch_JSON(url) {
	let response = await fetch(url);
	let data = await response.json();
	console.log('Data: ' + data);
	return data;
}

async function post_Form(url, form) {
	console.log('Post Form: ' + url + ' - ' + form);
	let response = await fetch(url, {
		method: "POST",
		body: form.serialize(),
		headers: {
	        "Content-Type": "application/x-www-form-urlencoded",
	    },
	});
	if (!response.ok || response.error) {
		window.open('/', '_self');
		throw Error(response.status);
	}
	return await response.json();
};

function delay(time) {
	return new Promise(resolve => {
		setTimeout(() => {
			resolve('Delay: ' + time);
		}, time * 1000);
	});
};

function getFormattedDate(datum) {
	let date = new Date(datum);
	let year = date.getFullYear();
	let month = (1 + date.getMonth()).toString();
	month = month.length > 1 ? month : '0' + month;

	let day = date.getDate().toString();
	day = day.length > 1 ? day : '0' + day;
	  
	return day + '/' + month + '/' + year;
};

function FormDataToJSON(FormElement){    
    var formData = new FormData(FormElement);
    var ConvertedJSON= {};
    for (const [key, value]  of formData.entries())
    {
        ConvertedJSON[key] = value;
    }
    return ConvertedJSON
};