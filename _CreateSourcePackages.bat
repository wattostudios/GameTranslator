echo off
cls

echo ==== Copying Files ====

mkdir _JarRelease

xcopy *.java _JarRelease /I

xcopy c:\classes\classpath\org "_JarRelease/org" /I /E

cd _JarRelease

del *.class /Q /S
del *.txt /Q /S

echo ==== Copy Complete ====
echo ==== Making Archives ====

mkdir _release

"C:\Program Files\Java\jdk1.6.0\bin\jar.exe" cfM _release/source.zip *.java org

echo ==== Archive Creation Complete ====
echo ==== Starting Clean Up ====

cd..
mkdir _release
cd _release
mkdir Source
cd..
cd _JarRelease
cd _release

move *.* ../../_release/Source/

cd ..


del *.* /Q
rmdir org /S /Q
rmdir _release

cd..

rmdir _JarRelease

echo ==== Clean Up Complete ====
pause