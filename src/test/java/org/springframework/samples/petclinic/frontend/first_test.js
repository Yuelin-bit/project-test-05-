

const webdriver = require('selenium-webdriver'),
    By = webdriver.By,
    until = webdriver.until;
const driver = new webdriver.Builder()
    .forBrowser('chrome')
    .build();
driver.get('http://localhost:8080/owners/find').then(function(){
/*driver.findElement(webdriver.By.className('form-control')).sendKeys('Build App\n').then(function(){
    driver.getPageSource().then(source=>{
        // console.log(source)
        console.log(source);
        if(source.includes("Build App")){
            console.log("Test Passed!")
        }else{
            console.log("Test Failed!")
        }
    })
  });*/
  driver.findElement(webdriver.By.className("dscac")).click();
});