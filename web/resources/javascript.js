/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var ajaxRequest;
var el;
/*function for update de top10 musics table*/
function ajaxRequestChanged() {
    if (ajaxRequest.readyState === 4) {
        var texto = ajaxRequest.responseText;
        document.getElementById("listTOP10").innerHTML = texto;
        setTimeout(makeAjaxRequest, 2000);
    }
}
function makeAjaxRequest() {
    ajaxRequest = new XMLHttpRequest();
    ajaxRequest.onreadystatechange = ajaxRequestChanged;
    ajaxRequest.open("GET", "popularservlet?teste=top", true);
    ajaxRequest.send(null);
    return false;
}

/*functions for DropDownBox of users in the navegation bar*/
function DropDown(el) {
    this.dd = el;
    this.initEvents();
}
DropDown.prototype = {
    initEvents: function() {
        var obj = this;

        obj.dd.on('click', function(event) {
            $(this).toggleClass('active');
            event.stopPropagation();
        });
    }
}

$(function() {

    var dd = new DropDown($('#dd'));

    $(document).click(function() {
        // all dropdowns
        $('.wrapper-dropdown-5').removeClass('active');
    });

});


