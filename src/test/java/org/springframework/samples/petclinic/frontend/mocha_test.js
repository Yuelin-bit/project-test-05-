const { doesNotMatch } = require('assert');
var assert = require('assert');
const webdriver = require('selenium-webdriver');
const URL = "http://localhost:8080";
const URL_add_owners = "http://localhost:8080/owners/new";
const URL_find_owners = "http://localhost:8080/owners/find";

const m = "";
describe('Main', function() {
  var driver;

  before(function() {
    driver = new webdriver.Builder()
    .forBrowser('chrome')
    .build();
  });
  
  it('Jump to Find page', function(done) {
    driver.get(URL);
    driver.findElement(webdriver.By.className("glyphicon  glyphicon-search")).click().then(()=>{
      driver.getPageSource().then(function(scource){
        assert.equal(scource.includes("lastNameGroup"), true);
        done();
      }).catch(message=>{
        console.log(message);
      });
    });
  });

  it('Creat an owner ', function(done) {
    driver.get(URL_add_owners);
    driver.findElement(webdriver.By.id('firstName')).sendKeys('Bokun').then(()=>{
      driver.findElement(webdriver.By.id('lastName')).sendKeys('Zhao').then(()=>{
        driver.findElement(webdriver.By.id('address')).sendKeys('3435 Drummond').then(()=>{
          driver.findElement(webdriver.By.id('city')).sendKeys('Montreal').then(()=>{
            driver.findElement(webdriver.By.id('telephone')).sendKeys('4383839202\n').then(()=>{
              driver.getPageSource().then(function(scource){
                assert.equal(scource.includes("Owner Information"), true); // Load the info page
                done();
              }).catch(message=>{
                console.log(message);
              });
            });
          });
        });
      });
    });
  });

  it('Creat an different owner ', function(done) {
    driver.get(URL_add_owners);
    driver.findElement(webdriver.By.id('firstName')).sendKeys('Yuelin').then(()=>{
      driver.findElement(webdriver.By.id('lastName')).sendKeys('Liu').then(()=>{
        driver.findElement(webdriver.By.id('address')).sendKeys('1100 Penfield').then(()=>{
          driver.findElement(webdriver.By.id('city')).sendKeys('Montreal').then(()=>{
            driver.findElement(webdriver.By.id('telephone')).sendKeys('4383839202\n').then(()=>{
              driver.getPageSource().then(function(scource){
                assert.equal(scource.includes("Owner Information"), true); // Load the info page
                done();
              }).catch(message=>{
                console.log(message);
              });
            });
          });
        });
      });
    });
  });

  it('Creat and find an owner', function(done) {
    driver.get(URL_add_owners);
    driver.findElement(webdriver.By.id('firstName')).sendKeys('Yuelin').then(()=>{
      driver.findElement(webdriver.By.id('lastName')).sendKeys('Liu').then(()=>{
        driver.findElement(webdriver.By.id('address')).sendKeys('1100 Penfield').then(()=>{
          driver.findElement(webdriver.By.id('city')).sendKeys('Montreal').then(()=>{
            driver.findElement(webdriver.By.id('telephone')).sendKeys('4389279373\n').then(()=>{
              driver.get(URL_find_owners).then(()=>{
                driver.findElement(webdriver.By.id('lastName')).sendKeys('Liu\n').then(()=>{
                  driver.getPageSource().then(function(scource){
                    assert.equal(scource.includes("Yuelin Liu"), true); // Load the info page
                    done();
                  }).catch(message=>{
                    console.log(message);
                  });
                });
              });
            });
          });
        });
      });
    });
  });

  it('Modify attributes of an owner', function(done) {
  driver.get(URL_find_owners);
    driver.findElement(webdriver.By.id('lastName')).sendKeys('Liu\n').then(()=>{
      driver.findElement(webdriver.By.linkText("Yuelin Liu")).click().then(()=>{
        driver.findElement(webdriver.By.linkText("Edit Owner")).click().then(()=>{
          driver.findElement(webdriver.By.id('telephone')).sendKeys('598342521\n').then(()=>{
            driver.getPageSource().then(function(scource){
              assert.equal(scource.includes("598342521"), true); // Load the info page
              done();
            }).catch(message=>{
              console.log(message);
            });
          })
        })
      })  
    })    
  });

  it('Add new Pet', function(done) {
    driver.get(URL_find_owners);
    driver.findElement(webdriver.By.id('lastName')).sendKeys('Zhao\n').then(()=>{
      driver.getPageSource().then(source => {
        if(source.includes("Pets and Visits")){
          driver.findElement(webdriver.By.linkText("Add New Pet")).click().then(()=>{
            driver.findElement(webdriver.By.id('type')).sendKeys('dog').then(()=>{
              driver.findElement(webdriver.By.id('birthDate')).sendKeys('2015-01-01').then(()=>{
                driver.findElement(webdriver.By.id('name')).sendKeys('Tom\n');
              });
            });
          });
        }else{
          driver.findElement(webdriver.By.linkText("Bokun Zhao")).click().then(()=>{
            driver.findElement(webdriver.By.linkText("Add New Pet")).click().then(()=>{
              driver.findElement(webdriver.By.id('type')).sendKeys('dog').then(()=>{
                driver.findElement(webdriver.By.id('birthDate')).sendKeys('2015-01-01').then(()=>{
                  driver.findElement(webdriver.By.id('name')).sendKeys('Tom\n');
                });
              });
            });
          });
        }
      }).then(()=>{
          driver.getPageSource().then(function(source2){
          assert.equal(source2.includes("Bokun Zhao"), true); 
          //assert.equal(scource.includes("Tom"), true); 
          done();
        }).catch(message=>{
          console.log(message);
        });
      });
    });
  });

  it('Add second pet', function(done) {
    driver.get(URL_find_owners);
    driver.findElement(webdriver.By.id('lastName')).sendKeys('Liu\n').then(()=>{
      driver.findElement(webdriver.By.linkText("Yuelin Liu")).click().then(()=>{
        driver.findElement(webdriver.By.linkText("Add New Pet")).click().then(()=>{
          driver.findElement(webdriver.By.id('type')).sendKeys('cat').then(()=>{
            driver.findElement(webdriver.By.id('birthDate')).sendKeys('2017-01-01').then(()=>{
              driver.findElement(webdriver.By.id('name')).sendKeys('Sam\n').then(()=>{
                done();
              });
            });
          });
        });
      }).catch(message=>{
        console.log(message);
      });
    });
  });

  it('Update Pet', function(done) {
    driver.get(URL_find_owners);
    driver.findElement(webdriver.By.id('lastName')).sendKeys('Liu\n').then(()=>{
      driver.findElement(webdriver.By.linkText("Yuelin Liu")).click().then(()=>{
        driver.findElement(webdriver.By.linkText("Edit Pet")).click().then(()=>{
          driver.findElement(webdriver.By.id('name')).clear().then(()=>{
            driver.findElement(webdriver.By.id('name')).sendKeys('BigSam\n').then(()=>{
              driver.getPageSource().then(function(scource){
                assert.equal(scource.includes("BigSam"), true); 
                done();
              }).catch(message=>{
                console.log(message);
              });
            });
          });
        });
      }).catch(message=>{
        console.log(message);
      });
    });
  });

  it('Update Pet Again', function(done) {
    driver.get(URL_find_owners);
    driver.findElement(webdriver.By.id('lastName')).sendKeys('Liu\n').then(()=>{
      driver.findElement(webdriver.By.linkText("Yuelin Liu")).click().then(()=>{
        driver.findElement(webdriver.By.linkText("Edit Pet")).click().then(()=>{
          driver.findElement(webdriver.By.id('name')).clear().then(()=>{
            driver.findElement(webdriver.By.id('name')).sendKeys('Sam\n').then(()=>{
              driver.getPageSource().then(function(scource){
                assert.equal(scource.includes("BigSam"), false); 
                assert.equal(scource.includes("Sam"), true); 
                done();
              }).catch(message=>{
                console.log(message);
              });
            });
          });
        });
      }).catch(message=>{
        console.log(message);
      });
    });
  });

  
  it('Add Visit', function(done) {
    driver.get(URL_find_owners);
    driver.findElement(webdriver.By.id('lastName')).sendKeys('Liu\n').then(()=>{
      driver.findElement(webdriver.By.linkText("Yuelin Liu")).click().then(()=>{
      driver.findElement(webdriver.By.linkText("Add Visit")).click().then(()=>{
        driver.findElement(webdriver.By.id('date')).clear().then(()=>{
          driver.findElement(webdriver.By.id('date')).sendKeys('2021-07-07').then(()=>{
            driver.findElement(webdriver.By.id('description')).sendKeys('The pet has flu.\n').then(()=>{
              driver.getPageSource().then(function(scource){
                assert.equal(scource.includes("flu"), true); 
                done();
              }).catch(message=>{
                console.log(message);
              });
            });
          });
        });
      });
    });
  });
  });

  it('Add Second Visit', function(done) {
    driver.get(URL_find_owners);
    driver.findElement(webdriver.By.id('lastName')).sendKeys('Liu\n').then(()=>{
      driver.findElement(webdriver.By.linkText("Yuelin Liu")).click().then(()=>{
      driver.findElement(webdriver.By.linkText("Add Visit")).click().then(()=>{
        driver.findElement(webdriver.By.id('date')).clear().then(()=>{
          driver.findElement(webdriver.By.id('date')).sendKeys('2021-09-09').then(()=>{
            driver.findElement(webdriver.By.id('description')).sendKeys('The pet is in good health.\n').then(()=>{
              driver.getPageSource().then(function(scource){
                assert.equal(scource.includes("health"), true); 
                done();
              }).catch(message=>{
                console.log(message);
              });
            });
          });
        });
      });
    });
  });
  });

 
  after(function() { driver.quit(); });
});