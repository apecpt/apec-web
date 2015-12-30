requirejs ["config"], ->
	requirejs [ "dynatable","app"], ($, dynatable) ->
		alert("done")
