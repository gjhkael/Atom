@REM 关闭回显
@echo off
set "CURRENT_DIR=%~dp0"
set "SERVER=10.8.92.190"
set "USERNAME=hotelApp"
set "PASSWORD=ctrip,OTA,2015.hotelApp"
set "JAR=%CURRENT_DIR%..\target\onlineRankingV2*.jar"
set "REMOTEDIR=/home/hotelApp/bizsh/scripts/rank/online/jars/onlineRankingV2.jar"
echo "current path:%CURRENT_DIR%"
cd /d %CURRENT_DIR%
@REM % --> 要会自动转义 %% --> % 
java -jar tools/myutils.jar %SERVER% %USERNAME% %PASSWORD% %JAR% %REMOTEDIR%
@REM cmd /C exit /B 0