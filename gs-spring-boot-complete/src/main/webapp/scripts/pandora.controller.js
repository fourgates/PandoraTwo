(function() {
	'use strict';

	function MainController($resource) {
		console.log('init');
		var vm = this;

		vm.title = 'AngularJS Tutorial Example';
		vm.searchInput = '';

		var service = {};

		service.Pandora = $resource('/music/pandora/:action', {
			section : '@section'
		}, {});

		vm.getSongs = function() {
			service.Pandora.query().$promise.then(function(songs) {
				console.log(songs);
				vm.songs = songs;
			});

			

		}
		
		service.Pandora2 = $resource(
				'http://tuner.pandora.com/services/json/?method=test.checkLicensing',
				{
					section : '@section'
				}, {});
		
		console.log('test');
		service.Pandora2.get().then(function(results) {
			console.log('results', results);
		})
		// http://tuner.pandora.com/services/json/?method=auth.userLogin&partner_id=123
	}

	angular.module('app').controller('MainController',
			[ '$resource', MainController ])
})();