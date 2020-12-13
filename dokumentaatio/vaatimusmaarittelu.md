# Alustava vaatimusmäärittely

## Sovelluksen tarkoitus
Sovelluksen avulla käyttäjä voi seurata, onko ajankäyttö toteutunut suunnitelman mukaisesti. Käyttäjä voi määritellä eri projekteille viikkotasolla käytettävän ajan etukäteen, kohdistaa toteutuneet tunnit tiettyyn projektiin ja seurata, miten suunnitelma vastaa toteutumaa. 
Sovellusta voi käyttää useampi kuin yksi rekisteröitynyt käyttäjä, joilla kaikilla on omat "tilinsä". 


## Käyttäjät
Ainakin alkuvaiheessa sovelluksella on yksi käyttäjäryhmä, eli normikäyttäjä. Myöhemmin sovellukseen saatetaan lisätä suuremmilla oikeuksilla olevia pääkäyttäjiä. 

## Perusversion tarjoama toiminnallisuus

#### Ennen kirjautumista
* Käyttäjä voi luoda järjestelmään käyttäjätunnuksen. TOTEUTETTU 
* Käyttäjä voi kirjautua järjestelmään. TOTEUTETTU
	* Kirjautuminen tapahtuu syötettäessä olemassa oleva käyttäjätunnus kirjautumislomakkeelle. 
	* Jos käyttäjää ei ole olemassa, järjestelmä ilmoittaa tästä. 

#### Kirjautumisen jälkeen 
* Käyttäjä voi lisätä ja poistaa projekteja. TOTEUTETTU 
* Käyttäjä voi allokoida käytettävät tunnit projektien kesken. TOTEUTETTU
* Käyttäjä voi kirjata toteutuneet tunnit kohdistamalla ne olemassa oleviin projekteihin 
* Käyttäjä voi tarkastella toteumaa: suunnitellut tunnit /toteutuneet tunnit per tarkastelujakso
* Käyttäjä voi kirjautua ulos järjestelmästä. 
* Käyttäjä voi poistaa tunnuksen ja siihen liittyvät projektit.

## Jatkokehitysideoita
Perusversion jälkeen järjestelmää täydennetään käytettävissä olevan ajan puitteissa esim. seuraavilla toiminnallisuuksilla:
* Lisätään kentät, joilla voidaan sanallisesti tarkentaa ajankäytön suunnitelmaa ja/tai toteumaa.
* Käyttäjän yhteyteen salasana, joka vaaditaan kirjautuessa. 
* Mahdollisuus lisätä projekteja, jotka näkyvät usealle käyttäjälle. 
* Mahdollisuus tehdä projektikohtaisia usean käyttäjän ajankäyttöön liittyviä yhteenvetoja. 

