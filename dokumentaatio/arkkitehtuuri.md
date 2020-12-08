# Arkkitehtuurikuvaus

## Rakenne

Sovelluksen rakenne koostuu kolmesta kerroksesta, käyttöliittymän rakentavasta timemanagement.ui-paketista, sovelluslogiigan sisältävästä timemanagement.domain-paketista sekä tietojen pysyväistallettamisesta vastaavasta timemanagement.dao-paketista. 

![packages](https://github.com/mateppon/ot-harjoitustyo/blob/master/pictures/packages.png)

## Sovelluslogiikka
Sovelluslogiikasta vastaa domain-paketissa oleva TimeManagementService-luokka. Sen avulla tieto siirtyy eri pakettien välillä. Luokat  SQLUserDao ja SQLProjectsDao vastaavat tietojen pysyväistalletuksesta. Näihin tietokantaluokkiin TimeManagementService on yhteydessä ainoastaan rajapintojen kautta. 

![packages_and_classes](https://github.com/mateppon/ot-harjoitustyo/blob/master/pictures/packages%20and%20classes2.png)

### Päätoiminnallisuudet

### Uuden käyttäjän luominen
![sekvenssikaavio](https://github.com/mateppon/ot-harjoitustyo/blob/master/pictures/sekvenssikaavio.png)

Tapahtumakäsittelijä kutsuu sovelluslogiikan metodia createNewUser, ja antaa samalla parametriksi uuden käyttäjän tiedot. Sovelluslogikka selvittää UserDaon avulla, onko käyttäjätunnus jo olemassa. Jos käyttäjätunnus ei ole käytössä, sovelluslogiikka luo User-olion ja tallentaa sen SQL-tietokantaan.
### Käyttäjä kirjautuu tililleen
![](https://github.com/mateppon/ot-harjoitustyo/blob/master/pictures/userLogIn.png)

Tapahtumakäsittelijä kutsuu sovelluslogiikan metodia findUser parametrinaan käyttäjän ilmoittama kyttäjänimi. Sovelluslogikka selvittää, UserDaon avulla, löytyykö vastaava käyttäjänimi tietokannnasta. Mikäli se löytyy, käyttäjänimeä vastaava id asetetaan User-luokan yksityiseen muuttujaan. Tämän jälkeen sovelluslogiikka hakee listan kaikista käyttäjän projekteista ProjectsDaon avulla ja palauttaa käyttöliittymälle.  
