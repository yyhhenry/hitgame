@echo off
if "%1" == "h" goto begin
mshta vbscript:createobject("wscript.shell").run("%~nx0 h",0)(window.close)&&exit
:begin
g++ map.cpp -o map.exe 
map.exe
del map.exe
java -jar ghit.jar 