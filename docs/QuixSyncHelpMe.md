## QuixSync


![Quix](https://raw.githubusercontent.com/BuckUbel/QuixSync/master/pictures/logo/Quix3D.png) 

Herzlich Willkommen im QuixSync Nutzungshandbuch!


### Über QuixSync
QuixSync ist eine in Java geschriebene Software zur Synchronisation von Verzeichnissen und Dateien. Hierfür werden zunächst zwei Verzeichnisse analaysiert, verglichen und letztendlich miteinader synchronisiert. 
&nbsp;
&nbsp;
&nbsp;

### 1 Gesamtsynchronisierung
Die Gesamtsynchronisierung automatisiert den gesamten Synchronisierungsprozess. Die angegebenen Verzeichnisse werden automatisch analysiert, verglichen und synchronisiert.
###### Quellverzeichnis:  
An dieser Stelle wird der absolute Pfad des Quellverzeichnisses für die Synchronisierung angegeben.
###### Zielverzeichnis:
An dieser Stelle wird der absolute Pfad des Zielverzeichnisses für die Synchronisierung angegeben. Dieses Verzeichnis enthält am Ende den Inhalt des angegebenen Quellverzeichnisses.
###### Button "Synchronisierung" 
Startet die Verzeichnisanalyse, -vergleich und -synchronisierung.
###### Button "Als Auto-Synchronisierung speichern"
Speichert die eingestellte Gesamtsynchronisierung als "Auto-Synchronisierung". Dies ermöglicht eine automatisierte Synchronisierung in einem festzulegenden Zeitintervall.
> **Anmerkung:** Diese Funktionalität ist in der aktuellen Version noch nicht implementiert.

&nbsp;
### 2 Indexierung
Neben der Gesamtsynchronisation kann auch jeder Programmschritt einzeln durchgeführt werden. Indexierung ist der Prozess der Verzeichnisanalyse.
###### Verzeichnis
An dieser Stelle ist das zu analysierende Verzeichnis anzugeben. 
###### Button "Index erstellen"
Startet die Erstellung der Indexdatei zum angegebenen Verzeichnis. Sie werden in Form einer .index.quix-Datei innerhalb des in den Einstellungen bestimmten temporären Verzeichnisses abgelegt.
> **Anmerkung:** für den folgenden Vergleich werden zwei Index-Dateien benötigt (Quelle und Ziel).

&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;

### 3 Vergleich
Beim Vergleich werden zwei Index-Dateien miteinander verglichen. So wird in diesem Prozess festgestellt welche Dateien bzw. Unterverzeichnisse der Quelle im Ziel fehlen und umgekehrt.

Für den Vergleich müssen zwei Index-Dateien aus den aufgeführten Listen ausgewählt werden. Die beiden Listen enthalten alle im temporären Verzeichnis (in den Einstellungen bestimmt) vorhandenen Index-Dateien. In den Listen selbst wird das Erstelldatum, sowie der Pfad zum indizierten Verzeichnis angezeigt. Der Pfad zur Index-Datei (.index.quix-Datei) wird nach dem Auswählen einer Datei oberhalb der Liste ausgegeben. 
In der linken Liste wird die Index-Datei des Quellverzeichnisses und in der rechten Liste die Index-Datei des Zielverzeichnisses ausgewählt.

> **Anmerkung:** Das Auflisten aller Indexdateien kann einige Zeit in Anspruch nehmen. Es wird empfohlen den Cache in regelmäßigen Abständen zu leeren.  

###### Button "Vergleichen"
Startet den Vergleichsprozess. Es wird eine Vergleichsdatei (.compare.quix) im eingestellten temporären Verzeichnis, mit den Unterschieden zwischen Quelle und Ziel als Inhalt, erstellt. 
&nbsp;
&nbsp;

### 4 Synchronsieren
Beim Synchronisieren wird aus dem Inhalt einer zuvor erstellten Vergleichsdatei heraus eine Synchronisierung durchgeführt.
Alle erstellten Vergleichsdateien werden aufgelistet unter Angabe des Zeitpunktes des Vergleichs, sowie des jeweiligen Quell- und Zielverzeichnisses. Wird eine Vergleichsdatei aus der Liste ausgewählt, so wird der absolute Pfad zur Vergleichsdatei selbst oberhalb der Liste ausgegeben.
> **Anmerkung:** Das Auflisten aller Vergleichsdateien kann einige Zeit in Anspruch nehmen. Es wird empfohlen den Cache in regelmäßigen Abständen zu leeren.  
###### Button "Synchronisieren"
Startet den Synchronisierungsprozess. Der Inhalt der Vergleichsdatei dient hierbei als Grundlage. Dateien und Unterverzeichnisse aus der Quelle, welche im Ziel nicht vorhanden sind, werden Diesem hinzugefügt. 
###### Button "Anzeigen"
Gibt den Inhalt der ausgewählten Vergleichsdatei aus. So kann nachvollzogen werden, welche Schritte bei der Synchronisierung durchgeführt werden. Die grün markierten Dateien und Unterverzeichnisse werden dem Ziel hinzugefügt. Bei harter Synchronisation werden die im Zielverzeichnis zu entfernenden Dateien und Unterverzeichnisse rot markiert.

### 5 Einstellungen
In diesem Fenster können die Rahmenbedingungen des Programms festgelegt werden.
###### Temporäres Verzeichnis
An dieser Stelle wird der absolute Pfad des temporären Verzeichnisses angegeben, in dem die erstellten Index- und Vergleichsdateien (.quix-Dateien) abgelegt werden.
###### Harte Synchronisierung
Bei aktivierter Harter Synchronisierung wird der Zustand des Quellverzeichnisses eins-zu-eins auf das Zielverzeichnis übertragen. Alle Dateien und Unterverzeichnisse, die im Ziel, aber nicht in der Quelle vorhanden sind, werden gelöscht.
###### Daemon-Betrieb
Aktiviert den Daemonbetrieb des Programms. Mithilfe dessen werden die als "Auto-Synchronisation" gespeicherten Einstellungen  in einem definierten zeitlichen Abstand durchgeführt. 
[siehe 1 Gesamtsynchronisierung - "Als Auto-Synchronisierung speichern"]
> **Anmerkung:** Diese Funktionalität ist in der aktuellen Version noch nicht implementiert.

&nbsp;
###### Fast Modus
Aktiviert Nested-Sets, welches den Vergleich zweier Indexdateien beschleunigt.
> **Anmerkung:** Ist dieser Modus aktiviert, werden keine Umbenennungen berücksichtigt.

###### Button "Cache leeren"
Löscht den Inhalt des temporären Verzeichnisses (alle Index- und Vergleichsdateien).
###### Button "Log anzeigen"
Gibt den Inhalt aller vorhandenen Log-Dateien in einem neuen Fenster wieder. 

###### Button "FTP-Verbindung hinzufügen"
Ermöglicht das Einrichten einer FileTransferProtocol (FTP) - Verbindung um eine Synchronisierung über das Netzwerk durchführen zu können.
> **Anmerkung:** Diese Funktionalität ist in der aktuellen Version noch nicht implementiert.
###### Button "Speichern"
Übernimmt die getroffenen Einstellungen.

&nbsp;
### 6 Programm-Header
Im Programm-Header befindet sich der Fortschrittsbalken für die aktuell ausgeführte Aktion. Die aktuell laufende Aktion wird in der linken unteren Ecke des Headers angezeigt. Solange wie eine Aktion läuft, dreht sich Quix. 
> **Anmerkung:** Der Programm-Header passt sich dem Programmablauf an. Das bedeutet: Werden bspw. zwei Verzeichnisse vom Nutzer im Indexierungs-Tab indiziert, so wechselt die aktuelle Aktion im Header automatisch von "Indexierung" auf "Vergleichen". Die neue Aktion kann mit einem Klick auf den Aktionsbutton gestartet werden, ohne dafür den Tab wechseln zu müssen. Nach dem Durchlaufen der "Vergleichen"-Aktion wechselt der Programm-Header auf die "Synchronisierung"-Aktion.
###### Button "*Aktion*"
Startet die aktuelle Aktion (zu finden in der linken unteren Ecke oder der Beschriftung des Buttons zu entnehmen).

###### Button "Stop"
Wird aktiviert, sowie eine Aktion gestartet wird. Das Betätigen des Buttons bricht die laufende Aktion ab. 

