require.config
		paths:
			html5shiv: "../lib/html5shiv/html5shiv"
			jquery: "../lib/jquery/jquery"
			bootstrap: "../lib/bootstrap/js/bootstrap"
			handlebars: "../lib/handlebars/handlebars"
			bootstrapaccessibilityplugin: "../lib/bootstrapaccessibilityplugin/plugins/js/bootstrap-accessibility"
		shim:
			jquery:
				exports: ["$", "jQuery"]
			bootstrap:
				deps: ["jquery"]
			botstrapaccessibilityplugin:
				deps: ["jquery", "bootstrap"]

requirejs ["jquery", "bootstrap", "app"], ($) ->
	requirejs ["bootstrapaccessibilityplugin"], ->
				;
