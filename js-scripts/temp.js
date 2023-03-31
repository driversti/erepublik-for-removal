function temp() {
  const regexp = /^https:\/\/www.erepublik.com\/\w{2}\/military\/battlefield\/\d{6}(\/\d{8})?$/;
  const str = "https://www.erepublik.com/en/military/battlefield/507070/12777678";
  console.log(regexp.test(str))
}

temp();
