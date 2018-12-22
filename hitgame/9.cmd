@echo off
if "%1" == "h" goto begin
mshta vbscript:createobject("wscript.shell").run("%~nx0 h",0)(window.close)&&exit
:begin
del 0.hg
copy 9.hg 0.hg 
java -jar ghit.jar

