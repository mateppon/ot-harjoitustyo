# Arkkitehtuurikuvaus

## Rakenne

![packages_and_classes](https://github.com/mateppon/ot-harjoitustyo/blob/master/pictures/packages_and_classes.png)

## Päätoiminnallisuudet

### Uuden käyttäjän luominen
![sekvenssikaavio](https://github.com/mateppon/ot-harjoitustyo/blob/master/pictures/sekvenssikaavio.png)

Tapahtumakäsittelijä kutsuu sovelluslogiikan metodia createNewUser, ja antaa samalla parametriksi uuden käyttäjän tiedot. Sovelluslogikka selvittää UserDaon avulla, onko käyttäjätunnus jo olemassa. Jos käyttäjätunnus ei ole käytössä, sovelluslogiikka luo User-olion ja tallentaa sen SQL-tietokantaan.
