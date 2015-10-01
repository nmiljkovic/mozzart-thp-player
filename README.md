# mozzart-thp-player

Projekat za testiranje botova. 

Radi tako sto se kreira u paketu Bots novi Bot, implementiraju se sve metode koje su relavantne za Bot-a i pokrene Main klasa sa argumentima koji predstavljaju putanju do klase.

Koraci za testiranje

`mvn clean compile assembly:single`

Ovim buildamo exe jar koji pokrecemo na sledeci nacin


`cd target/`

`java -jar player-1.0-SNAPSHOT-jar-with-dependencies.jar org.mozzartbet.hackathon.gui.bots.DummyBot org.mozzartbet.hackathon.gui.bots.DummyBot org.mozzartbet.hackathon.gui.bots.DummyBot org.mozzartbet.hackathon.gui.bots.DummyBot`

