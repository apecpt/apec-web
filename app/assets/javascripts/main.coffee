require.config
	paths:
		html5shiv: "../lib/html5shiv/html5shiv"
		jquery: "../lib/jquery/jquery"
		bootstrap: "../lib/bootstrap/js/bootstrap"
	
	shim:
		jquery:
			exports: "$"
		bootstrap:
			deps: ["jquery"]

requirejs(["app"])
