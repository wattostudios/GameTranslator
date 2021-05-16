echo off
cls

echo ==== Copying Files ====

mkdir _JarRelease

xcopy *.class _JarRelease /I
xcopy *.txt _JarRelease /I
xcopy help "_JarRelease/help" /I /E
xcopy images "_JarRelease/images" /I /E
xcopy language "_JarRelease/language" /I /E
xcopy settings "_JarRelease/settings" /I /E

xcopy c:\classes\classpath\org "_JarRelease/org" /I /E

xcopy _installationExtras _JarRelease /I /E

cd _JarRelease

cd settings
del settings.wsd
cd ..

mkdir logs
mkdir temp
mkdir updates

del *.java /S /Q

echo ==== Copy Complete ====
echo ==== Making Archives ====

mkdir _release

"C:\Program Files\Java\jdk1.6.0\bin\jar.exe" cmf _JarManifest.txt GameTranslator.jar *.class images org
"C:\Program Files\Java\jdk1.6.0\bin\jar.exe" cfM _release/trans.zip _Readme_GameTranslator.txt *.jar *.ico GameTranslator.bat openFile.bat help language logs settings temp updates *.exe
move GameTranslator.jar _release

echo ==== Archive Creation Complete ====
echo ==== Starting Initial Clean Up ====

cd ..
mkdir _release
cd _JarRelease

cd _release
move *.zip ../../_release
move *.* ..
cd ..
rmdir _release

del *.class /Q
del _Jar*.txt /Q

rmdir images /S /Q
rmdir org /S /Q


echo ==== Clean Up Complete ====
echo ==== Making Installer Package ====

cd ..
cd _installMaker
call installerScript2.nsi
cd ..
cd _JarRelease

echo ==== Installer Package Created ====
echo ==== Starting Final Clean Up ====

del *.* /Q
rmdir help /S /Q
rmdir language /S /Q
rmdir settings /S /Q
rmdir logs /S /Q
rmdir temp /S /Q
rmdir updates /S /Q

cd ..
cd _installMaker
move *.exe ../_release

cd ..
rmdir _JarRelease

cd _release
mkdir Full
move *.* Full
cd..

echo ==== Final Clean Up Complete ====
pause