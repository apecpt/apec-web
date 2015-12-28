define -> require.config
		paths:
			html5shiv: "../lib/html5shiv/html5shiv"
			jquery: "../lib/jquery/jquery"
			bootstrap: "../lib/bootstrap/js/bootstrap"
			dynatable: "../lib/dynatable/jquery.dynatable"
			handlebars: "../lib/handlebars/handlebars"
		shim:
			jquery:
				exports: "$"
			bootstrap:
				deps: ["jquery"]
			dynatable:
				deps: ["jquery"]
				exports: "jQuery.fn.dynatable"

