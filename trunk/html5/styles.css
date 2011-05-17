function dragDefine(ev) {
	ev.dataTransfer.effectAllowed = 'move';
	ev.dataTransfer.setData("text/plain", ev.target.getAttribute('id'));
	ev.dataTransfer.setDragImage(ev.target, 0, 0);
	return true;
};

//  funções do elemento dragable

function dragEnd(ev) {
	document.getElementById("textMovimento").innerHTML = "nenhum movimento";
};

function mov(ev, id) {
	//document.getElementById("campo").innerHTML = "o "+id+" está se movimentando";
};


//  funções do elemento dropTarget

function enter(ev) {
	document.getElementById("textMovimento").innerHTML = "o elemento entrou no campo";
};

function leave(ev) {
	document.getElementById("textMovimento").innerHTML = "o elemento saiu do campo";
	document.getElementById("coordenadas").innerHTML = "";
};

function drop(ev) {
	idDrag = ev.dataTransfer.getData("Text");
	var oElem;
	var targ = document.getElementById("target");
	var form = document.getElementById("form");
	//ev.target.appendChild(document.getElementById(idDrag));
	//document.getElementById("target").innerHTML += "o elemento "+idDrag+" foi solto no campo<br>";
	switch(idDrag) {
		case "caixaDeTexto":
			oElem = document.createElement();
			oElem.type = 
			targ.innerHTML += "o elemento "+idDrag+" foi solto no campo<br>";
			break;
		case "selecao":
			oElem = document.createElement("SELECT");
			opt1 = document.createElement("OPTION");
			texto = document.createElement("text");
			texto.value="digite o novo endereço";
			opt1.value="novo";
			opt1.label="label do novo";
			oElem.appendChild(opt1);
			targ.appendChild(texto);
			form.appendChild(oElem);
			break;
	}
	ev.preventDefault();
};

function dragOver(ev) {
	document.getElementById("coordenadas").innerHTML = "("+ev.offsetX+" , "+ev.offsetY+")";	
	ev.preventDefault();
};