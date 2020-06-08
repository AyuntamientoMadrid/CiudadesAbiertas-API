(function ($) {
	$(function () {
		var $menu = $('.kss-menu'),
			$childMenu = $('.kss-menu-child'),
			$menuItem = $menu.find('.kss-menu-item'),
			ref = $menu.data('kss-ref');

		// Activate current page item
		$menuItem.eq(ref).addClass('kss-active');

		// Append child menu and attach scrollSpy
		if ( $childMenu.find('li').length ) {
			$childMenu.show().appendTo($menuItem.eq(ref));
		}

		// Syntax hightlignting with Rainbow.js
		$('code.html').attr('data-language', 'html');
		$('code.css').attr('data-language', 'css');
		$('code.less, code.scss').attr('data-language', 'generic');

		// $('pre>code').addClass('prettyprint');
		// prettyPrint();

	});
}(jQuery));