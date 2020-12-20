# Käyttöohje

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla:

```
java -jar TimeManagementApp-1.0-SNAPSHOT.jar 
```

Ohjelma käynnistyy näkymään, jossa käyttäjällä on kaksi vaihtoehtoa: 
* käyttäjä voi  kirjautua olemassa olevalla käyttäjänimellään tai
* käyttäjä voi luoda uuden käyttäjätunnuksen

## Uuden käyttäjän luominen

Mikäli käyttäjä on valinnut, että haluaa luoda uuden käyttäjätunnuksen, avautuu uusi ikkuna, johon käyttäjä voi syöttää nimensä ja valitsemansa käyttäjätunnuksen. Mikäli käyttäjätunnus on vapaana, käyttäjätunnuksen luominen onnistuu ja uusi käyttäjä siirtyy sisäänkirjautuneen käyttäjän näkymään.

## Kirjautuneen käyttäjän näkymä eli login-näkymä

Kirjautuneen käyttäjän näkymässä on vetovalikko, jossa on kaikki käyttäjän projektit.Käyttäjä voi lisätä omaan listaansa projekteja näkymän alalaidassa olevasta kohdasta. 
Käyttäjä valitsee vetovalikosta haluamansa projektin, minkä jälkeen hänellä on seuraavat vaihtoehdot:
* Käyttäjä voi aloittaa tarkastelujakson eli asettaa projektille suunnitellut tunnit. Tämä tapahtuu painamalla Start new sprint and allocate time -nappia. Napin painalluksesta käyttäjä siirtyy Allokoi projektille tunteja -näkymään. 
* Käyttäjä voi kirjata toteutuneet tunnit painamalla Update time spent on the project -nappia.Tämän seurauksena käyttäjä siirtyy Toteutuneet tunnit -näkymään.
* Käyttäjä voi nollata projektiin kohdistetut tunnit painamalla Reset project time tracking-nappia. 
* Käyttäjä voi poistaa projektin kokonaan painamalla Delete project -nappia.
* Käyttäjä voi tarkastella projektin tämän hetkistä ajankäytön tilastoa painamalla Statistics-nappia.

Lisäksi käyttäjä voi kirjautua ulos painamalla Log out -nappia.

## Allokoi projektille tunteja -näkymä

Näkymässä on tyhjä ruutu, johon käyttäjän on tarkoitus syöttää se tuntimäärä, jonka hän aikoo käyttää kyseiseen projektiin tarkastelujaksollaan. 

## Toteutuneet tunnit -näkymä

Näkymässä on ruutu, johon käyttäjän on tarkoitus syöttää toteutuneiden tuntien määrä.         

Käyttäjä itse määrittelee, kuinka pitkän seurantajakson hän aikoo toteuttaa. Se voi olla esimerkiksi kaksi viikkoa. Seurantajakson alussa käyttäjä määrittelee projektille allokoimansa ajan ja kirjaa sen ohjelmaan. Seurantajakson aikana käyttäjä voi päivittäin kirjata toteutuneita tuntejaan sovellukseen. 


