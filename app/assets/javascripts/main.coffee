requirejs ["config"], ->
	requirejs ["jquery", "dynatable"], ($, dynatable) ->
		$("#publicationsTable").dynatable()