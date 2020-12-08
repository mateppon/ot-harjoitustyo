# Ajanhallintasovellus

Sovelluksen avulla käyttäjä voi allokoida haluamansa määrän aikaa eri projekteille sekä seurata, miten ajankäyttösuunnitelma on toteutunut tarkastelujakson aikana.

## Releaset

[Viikko5](https://github.com/mateppon/ot-harjoitustyo/releases/tag/viikko5)


## Dokumentaatio

[Arkkitehtuuri](https://github.com/mateppon/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Alustava vaatimusmäärittely](https://github.com/mateppon/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittelu.md)

[Työaikakirjanpito](https://github.com/mateppon/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)


## Komentorivitoiminnot

### Testaus


Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla selaimella tiedoston target/site/jacoco/index.html

## Suoritettavan jarin generointi

Alla oleva komento generoi hakemistoon target suoritettavan jar-tiedoston TimeManagementApp-1.0-SNAPSHOT.jar
```
mvn package
```


## Checkstyle

```
mvn jxr:jxr checkstyle:checkstyle
```
Mahdolliset virheilmoitukset selviävät avaamalla tiedosto target/site/checkstyle.html

## Javadoc

```
mvn javadoc:javadoc
```
Synytynyt dokumentti löytyy sijainnista target/site/apidocs/index.html
