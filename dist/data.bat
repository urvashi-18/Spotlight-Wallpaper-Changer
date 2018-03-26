@echo off                           //Turn off screen text messages
:loop                               //Set marker called loop, to return to
java -jar SpotLightWallpaper.jar -r
timeout /t 300 /nobreak
if exist x goto loop                           //Return to loop marker
