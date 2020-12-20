## Testausdokumentti

Ohjelmaa on testattu JUnitilla automatisoitujen yksikkö- ja integraatiotestien avulla. 
Lisäksi ohjelmaa on testattu manuaalisesti järjestelmätasolla.

### Yksikkö- ja integraatiotestaus

Ohjelman sovelluslogiikka löytyy domain-paketista, erityisesti TimeManagementService-luokasta. Sovelluslogiikan testausta varten on luotu luokat FakeUserDao ja FakeProjectsDao, jotka imitoivat varsinaisessa ohjelmassa olevia SQLUserDao- ja SQLProjectsDao -luokkia. 

Sovelluslogiikkakerrosta testataan UserServiceTestin ja ProjectsServiceTestin integraatiotesteillä, jotka . Ne jäljittelevät tilanteita, joita ohjelmalle tulee vastaan. 

Lisäksi sovelluslogiikkakerroksen luokkia User ja Projects testataan muutamilla yksittäisillä testeillä. 

UserDao- ja ProjectsDao -luokkien toiminta on testattu erillisen tietokannan (TimeManagementTests.db) avulla. Tietokantaa käytetään myös niissä integraatiotesteissä, jotka ulottuvat tiedon tallentamiseen saakka. 

### Testikattavuus

Testien rivikattavuus on 97% ja haarautumakattavuus 100%. Käyttöliittymäkerros on jätetty testtauksen ulkopuolelle.    


KUVA 

### Järjestelmätestaus

Ohjelman järjestelmätestaus on suoritettu manuaalisesti.

### Asennus ja konfigurointi

Ohjelma on ladattu ja asennettu Linux-ympäristöön.  

### Toiminnallisuudet

Ohjelman toiminnallisuuksia on testattu kokeilemalla antaa erilaisia syötteitä ja niiden yhdistelmiä. 
Ohjelmaa on testattu tilanteissa, joissa tietokanta luodaan ohjelman käynnistämisen yhteydessä ja joissa tietokanta on jo olemassa. Testaus onnistuu molemmissa tilanteissa. Sen sijaan Jacoco antaa virheellisen reportin, mikäli ensin ei ajeta mvn test komentoa.  

