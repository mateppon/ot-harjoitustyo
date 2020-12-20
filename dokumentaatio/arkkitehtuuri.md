# Arkkitehtuurikuvaus

## Rakenne

Sovelluksen rakenne koostuu kolmesta kerroksesta, käyttöliittymän rakentavasta timemanagement.ui-paketista, sovelluslogiigan sisältävästä timemanagement.domain-paketista sekä tietojen pysyväistallettamisesta vastaavasta timemanagement.dao-paketista. 

![packages](https://github.com/mateppon/ot-harjoitustyo/blob/master/pictures/packages.png)

## Sovelluslogiikka
Sovelluslogiikasta vastaa domain-paketissa oleva TimeManagementService-luokka. Sen avulla tieto siirtyy eri pakettien välillä.  Luokat  SQLUserDao ja SQLProjectsDao vastaavat tietojen pysyväistalletuksesta. Näihin tietokantaluokkiin TimeManagementService on yhteydessä ainoastaan rajapintojen kautta. 

![packages_and_classes](https://github.com/mateppon/ot-harjoitustyo/blob/master/pictures/packages%20and%20classes2.png)
## Käyttöliittymä
Käyttöliittymässä on viisi eri näkymää:
* Aloitusnäkymä
* Luo uusi käyttäjä -näkymä
* Sisäänkirjautuneen käyttäjän näkymä
* Näkymä, jossa projektille varataan tunteja
* Näkymä, jossa projektille kirjataan toteutuneita tunteja 

Käyttöliittymä on erotettu sovelluslogiikasta. 

## Tietojen tallennus

Ohjelma tallentaa tiedot SQLite-tietokantaan luokkien SQLUserDao ja SQLProjectsDao avulla. 
Luokat noudattavat Data Access Object-suunnittelumallia, joten ne voidaan tarvittaessa korvata erilaisilla toteutuksilla, esimerkiksi verkkoon tehtävällä tallennuksella. 
### Päätoiminnallisuudet

### Uuden käyttäjän luominen
![sekvenssikaavio](https://github.com/mateppon/ot-harjoitustyo/blob/master/pictures/sekvenssikaavio.png)

VAIHDA KUVA

Tapahtumakäsittelijä kutsuu sovelluslogiikan metodia createNewUser(), ja antaa samalla parametriksi uuden käyttäjän tiedot. Sovelluslogikka selvittää UserDaon avulla, onko käyttäjätunnus jo olemassa. Jos käyttäjätunnus ei ole käytössä, uusi käyttäjä lisätään tietokantaan UserDaon avulla. Lisäksi sovelluslogiikka luo User-olion, ja asettaa tietokannasta saamansa id:n sen kenttään.
### Käyttäjä kirjautuu tililleen
KUVA LOGin

Tapahtumakäsittelijä kutsuu sovelluslogiikan metodia findIfUserExists() parametrinaan käyttäjän ilmoittama käyttäjänimi. Sovelluslogikka selvittää, UserDaon avulla, löytyykö vastaava käyttäjänimi tietokannnasta. Mikäli nimeä ei löydy, luodaan uusi User-olio, ja asetetaan sen kenttään edellisessä metodissa palautunut käyttäjätunnus, userId. Tämän jälkeen sovelluslogiikka hakee listan kaikista käyttäjän projekteista ProjectsDaon avulla ja palauttaa listan käyttöliittymälle.  

### Käyttäjä luo uuden projektin

TÄHÄN UUSI KUVA

Käyttöliittymä kutsuu sovelluslogikka-luokan createProject() -metodia. Sovelluslogiikka hakee käyttäjän id:n User-luokan kentästä ja tekee tietokantakyselyn siitä, onko henkilöllä jo samannimistä projektia. Jos henkilöllä ei ole jo sen nimistä projektia, sovelluslogiikka luo uuden. Lisäksi se alustaa projektille Time-taulun. 


### Ohjelman rakenteeseen jääneitä heikkouksia

Ohjelman käyttöliittymän sisältävä koodi on jossain määrin toisteista. Luokan rakenteen ja nimeämiskäytäntöjen parantaminen tekisivät koodista selkeämpää. Lisäksi käyttöliittymä on toteutettu pääosin start()-metodin avulla.

