# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla käyttäjä voi tehdä seurantaa omasta ajankäytöstään. Käyttäjä luo omalle tililleen yhden tai useamman projektin. Hän päättää itselleen sopivan ajanjakson, esimerkiksi kaksi viikkoa, jonka aikana hän kirjaa ylös projektiin käyttämäänsä aikaa. Jakson alussa käyttäjä voi määritellä, kuinka monta tuntia hän aikoo käyttää aikaa eri projekteihin. Jakson kuluessa käyttäjä voi käydä merkitsemässä toteutuneita tunteja omalle tililleen, ja tehdä yhteenvedon milloin vain. Kun käyttäjän määrittelemä jakso on päättynyt, hän voi nollata tunnit tai poistaa projektin/projektit.


## Käyttäjät

Sovelluksella on yksi käyttäjäryhmä, normikäyttäjät. Sovellusta on mahdollista helposti laajentaa siten, että jollekin käyttäjälle anetaan suuremmat oikeudet.

## Perusversion tarjoama toiminnallisuus

Ennen kirjautumista

Käyttäjä voi luoda ohjelmaan yhden tai useamman käyttäjätunnuksen.  Usean tunnuksen luonti on tehty mahdolliseksi, jotta käyttäjä voi halutessaan säilyttää esimerkiksi työ- ja opiskeluprojektinsa eri käyttäjätilien alla. 

Käyttäjä voi kirjautua järjestelmään. Kirjautuminen tapahtuu syöttämällä olemassa oleva käyttäjätunnus kirjautumislomakkeelle. Jos käyttäjänimeä ei löydy tietokannasta, järjestelmä antaa tästä ilmoituksen. 

Kirjautumisen jälkeen 

Käyttäjä voi:
* Käyttäjä voi lisätä omaan projektilistaansa uuden projektin, mikäli hänellä itsellään ei ole jo samannimistä projektia. Ohjelma antaa ilmoituksen, mikäli käyttäjä yrittää lisätä itselleen toistamiseen saman projektin. Muilla käyttäjillä voi olla samannimisiä projekteja.

*Käyttäjä voi allokoida valitsemalleen projektille haluamansa määrän tunteja, eli suunnitella, kuinka monta tuntia aikoo käyttää kyseessä olevaan projektiin valitsemallaan aikajaksolla. Kohdistettavien tuntien määrää ei ole rajoitettu, jotta käyttäjä voi valita haluamansa mittaisen tarkastelujakson. Käyttäjän täytyy kuitenkin syöttää jokin positiivinen kokonaisluku. Ohjelma antaa virheilmoituksen, mikäli käyttäjä yrittää syöttää negatiivisia lukuja, desimaalilukuja tai nollaa. Se myös ilmoittaa virheellisestä syötteestä, mikäli käyttäjä syöttää vahingossa jonkin muun merkin kun numeron. 

*Käyttäjä voi merkitä tehtyjä tunteja kohdistamalla ne valitsemalleen projektille. Ohjelma laskee kuhunkin projektiin kohdistuneita tunteja yhteen niin kauan, kun käyttäjä ilmoittaa tarkastelujakson päättyneen.

*Käyttäjä voi käyttää sovellusta pelkästään toteutuneiden tuntien seuraamiseen. Käyttäjän ei tarvitse määrittää suunniteltuja tunteja ennakkoon, mikäli ei halua. 

*Käyttäjä voi tarkastella kunkin projektin kohdalla yhteenvetoa sen hetkisestä tilastosta: kuinka paljon tunteja hän suunnitteli käyttävänsä, ja kuinka monta tuntia on käytetty tähän mennessä.

*Käyttäjä voi aloittaa uuden tarkastelujakson nollaamalla projektin tunnit ilman, että projekti katoaa.

* Käyttäjä voi poistaa listaltaan projektin. Tämän jälkeen projekti ja sitä koskevat tiedot poistetaan tietokannasta. Huom! Mikäli projekti on käyttäjän ainoa projekti, projektin nimi näkyy listassa kunnes, käyttäjä lisää jonkin uuden projektin listaansa tai kirjautuu ohjelmaan uudelleen.

*Käyttäjä voi kirjautua ulos järjestelmästä
## Jatkokehitysideoita

* Lisätään kentät, joilla voidaan sanallisesti tarkentaa ajankäytön suunnitelmaa ja/tai toteumaa.
* Käyttäjän yhteyteen salasana, joka vaaditaan kirjautuessa. 
* Mahdollisuus lisätä projekteja, jotka näkyvät usealle käyttäjälle. 
* Mahdollisuus tehdä projektikohtaisia usean käyttäjän ajankäyttöön liittyviä yhteenvetoja. 
* Erilaisten yhteenvetojen tekeminen

