<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles.css" />
<script type="text/javascript" src="scripts.js"></script>
<title>HTML5</title>
</head>
<body>

<!-- campo dos objetos draggable -->
<div id="boxA" class="caixa" ondragover="dragOver(event)" ondrop="dragDrop">
	<div id="caixaDeTexto" class="word" draggable="true" ondragstart="return dragDefine(event)"
		ondragend="dragEnd(event)" ondrag="mov(Event, 'caixaDeTexto')">caixa de texto</div>
	<div id="selecao" class="word" draggable="true" ondragstart="return dragDefine(event)" 
		ondragend="dragEnd(event)" ondrag="mov(Event, 'selecao')">seleÃ§Ã£o</div>
	<div id="radio" class="word" draggable="true" ondragstart="return dragDefine(event)" 
		ondragend="dragEnd(event)" ondrag="mov(Event, 'radio')">radio button</div>
	<div id="options" class="word" draggable="true" ondragstart="return dragDefine(event)" 
		ondragend="dragEnd(event)" ondrag="mov(Event, 'options')">options</div>
	<div id="titulo" class="word" draggable="true" ondragstart="return dragDefine(event)" 
		ondragend="dragEnd(event)" ondrag="mov(Event, 'titulo')">tÃ­tulo</div>
</div>

<!-- campo onde os objetos serÃ£o arrastados -->
<div id="target" class="target" ondragenter="enter(event)" ondragleave="leave(event)"
		ondrop="drop(event)" ondragover="dragOver(event)">
	<form name="form" id="form">
	</form>

</div>

<div id="textMovimento" class=textMovimento> nenhum movimento </div>

<div id="coordenadas" class="coordenadas"></div>

</body>
</html>