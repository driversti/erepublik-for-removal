// ==UserScript==
// @name         erepublik
// @namespace
// @version      0.0.17
// @description  making the gameplay easier!
// @author       driversti
// @updateUrl    /Users/driversti/IdeaProjects/erepublik/js-scripts/erepublik.user.js
// @downloadUrl  /Users/driversti/IdeaProjects/erepublik/js-scripts/erepublik.user.js
// @match
// @icon
// @include      https://www.erepublik.com/*
// @run-at       document-start
// @grant        none
// @require      http://code.jquery.com/jquery-latest.js
// ==/UserScript==

$(document).ready(function () {
  console.log(erepublik);
  Environment && console.log(Environment);
  console.log(SERVER_DATA);
  console.log(ErpkShop);
  console.log(dotNotifications);

  function preferCurrencyForTravelling(useCurrency) {
    console.log('Start preferCurrencyForTravelling: %s', useCurrency)
    let onOrOff = useCurrency ? 'on' : 'off';
    $.ajax({
      url: 'https://www.erepublik.com/en/main/profile-update',
      type: 'post',
      data: `action=options&params=%7B%22optionName%22%3A%22travel_use_currency%22%2C%22optionValue%22%3A%22${onOrOff}%22%7D&_token=${csrfToken}`,
      headers: {
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        cookie: 'erpk=' + erepublik.settings.pomelo.authToken
      },
      dataType: 'json',
      success: function (data, status, jqXHR) {
        console.log(data);
      }
    })
  }

  function temp() {
    window.location.href
    // language=JSRegexp
    const regexp = /^https:\/\/www.erepublik.com\/\w{2}\/military\/battlefield\/\d{6}((\/\d{8})|(\/0\/fighterStatistics))?$/gm;
    if (regexp.test(window.location.href)) {
      console.log("This is a battlefield");
    } else {
      console.log("This is not a battlefield");
    }
    $(window).bind('popstate', function(e) {
      console.log('ping');
    });

    window.addEventListener('hashchange', function () {
      console.log('Click');
    })

    $('#trigger_fighterStatistics').on('click', function () {
      console.log('It works!');
    })
  }

  //preferCurrencyForTravelling(true);
  temp();
})
