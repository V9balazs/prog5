Projekt Leírása

1. Technológiai Stack:

Backend: Java Spring Boot
Adatbázis: MongoDB
Frontend: Vaadin
Konténerizáció: Docker

2. Felhasználói Szerepek:

Manager: Új munkalehetőségek létrehozására, törlésére képes, valamint kezelheti az Employee-k jelentkezéseit (elfogadás vagy elutasítás). Ha egy jelentkezést elfogad, a munkalehetőség már nem lesz látható mások számára.
Employee: Megtekintheti a rendelkezésre álló munkalehetőségeket és jelentkezhet rájuk. Megtekintheti a jelenlegi aktív, elfogadott munkáit.

3. Főbb Funkciók:

Munkalehetőségek Kezelése: A Manager létrehozhat, szerkeszthet és törölhet munkalehetőségeket.
Jelentkezések Kezelése: Az Employee-k jelentkezhetnek a munkalehetőségekre, a Manager pedig kezelheti ezeket a jelentkezéseket.
Elfogadott Munkák Megjelenítése: Az Employee látja az elfogadott munkáit a saját felületén.
Dinamikus Frissítések: Amikor a Manager elfogad egy jelentkezést, a munkalehetőség automatikusan eltűnik a többi Employee számára.

4. Biztonság és Hitelesítés:

Spring Security: Biztosítja a felhasználók hitelesítését és az autorizációt a különböző szerepkörök alapján.
