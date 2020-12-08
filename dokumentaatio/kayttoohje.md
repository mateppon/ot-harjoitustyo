Konfigurointi

Ohjelma vaatii toimiakseen, että käynnistyshakemistossa on  config.properties -tiedosto, jossa määritellään tietokannnan nimi. Tiedosto on seuraavaa muotoa, josta käyttäjä voi halutessaan vaihtaa sanan projects.

sqlFile=projects.db


Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla:

Ohjelma käynnistyy näkymään, jossa käyttäjällä on kaksi vaihtoehtoa: 
* käyttäjä voi  kirjautua olemassa olevalla käyttäjänimellään tai
* käyttäjä voi luoda uuden käyttäjätunnuksen

Uuden käyttäjän luominen

Mikäli käyttäjä on valinnut, että haluaa luoda uuden käyttäjätunnuksen, avautuu uusi ikkuna, johon käyttäjä voi syöttää nimensä ja valitsemansa käyttäjätunnuksen. Mikäli käyttäjätunnus on vapaana, käyttäjätunnuksen luominen onnistuu ja uusi käyttäjä siirtyy login-näkymään.

Kirjautuneen käyttäjän näkymä eli login-näkymä

Login-näkymässä käyttäjä näkee kaikki omat projetinsa vetovalikosta. 
Käyttäjä voi lisätä omaan listaansa projekteja näkymän alalaidassa olevasta kohdasta. 

Käyttäjä voi aloittaa uuden jakson, eli valita haluamansa projektit ja asettaa arvioinsa siitä, kuinka paljon hän varaa aikaa kyseisille projekteille.

Käyttäjä itse määrittelee, kuinka pitkän seurantajakson hän aikoo toteuttaa. Se voi olla esimerkiksi kaksi viikkoa. Seurantajakson aikana käyttäjä kirjaa sovellukseen kuhunkin projektiin käyttämänsä ajan. 

Jakson aikana toteutunutta ja suunniteltua ajankäyttöä voi tarkastella ja vertailla valitsemalla näytöltä “Statistcs”.

Tarkastelujakson voi päättää “Quit and save this sprint” -komennolla tai “Quit and delete this sprint".
