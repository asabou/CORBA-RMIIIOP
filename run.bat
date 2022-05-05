set JAVA_HOME=D:\tools\jdk-8u262-b10
set PATH=%JAVA_HOME%\bin;%PATH%

set GRADLE_HOME=D:\tools\gradle-7.3.3
set PATH=%GRADLE_HOME%\bin;%PATH%

set ROOT_PATH=D:\Master\An1\Sem2\WSMT\Middleware\com.wsmt.middleware.students
set BASE_PACKAGE=com.wsmt.middleware.students

idlj -td %ROOT_PATH%\src\main\java -pkgPrefix %BASE_PACKAGE%.service.abstracts %BASE_PACKAGE%.service.abstracts -fall ExecIdlInte.idl
start orbd -ORBInitialPort 3000

set COMP=%1

if "%COMP%" == "serv-idl" (
    gradle clean build -Dserv=idl & java -jar %ROOT_PATH%\build-%COMP%\students.jar
)

if "%COMP%" == "clie-idl" (
    gradle clean build -Dclie=idl & java -jar %ROOT_PATH%\build-%COMP%\students.jar
)

if "%COMP%" == "clie-idl-rmiiiop" (
    gradle clean build -Dclie=idl-rmiiiop
    cd %ROOT_PATH%\build\classes\java\main
    rmic -iiop %BASE_PACKAGE%.service.impl.JavaRMIIIOPServiceImpl -d %ROOT_PATH%\src\main\java\
    cd %ROOT_PATH%\src\main\java
    jar uvf %ROOT_PATH%\build-%COMP%\students.jar com\wsmt\middleware\students\service\impl\_ExecRmiInte_Stub.class
    jar uvf %ROOT_PATH%\build-%COMP%\students.jar com\wsmt\middleware\students\service\impl\_JavaRMIIIOPServiceImpl_Tie.class
    cd %ROOT_PATH%
    java -jar %ROOT_PATH%\build-%COMP%\students.jar
)

if "%COMP%" == "serv-rmiiiop" (
@REM     https://stackoverflow.com/questions/36923887/how-to-add-your-own-class-into-a-compiled-jar-file
    gradle clean build -Dserv=rmiiiop
    cd %ROOT_PATH%\build\classes\java\main
    rmic -iiop %BASE_PACKAGE%.service.impl.JavaRMIIIOPServiceImpl -d %ROOT_PATH%\src\main\java\
    cd %ROOT_PATH%\src\main\java
    jar uvf %ROOT_PATH%\build-%COMP%\students.jar com\wsmt\middleware\students\service\impl\_ExecRmiInte_Stub.class
    jar uvf %ROOT_PATH%\build-%COMP%\students.jar com\wsmt\middleware\students\service\impl\_JavaRMIIIOPServiceImpl_Tie.class
    cd %ROOT_PATH%
    java -jar %ROOT_PATH%\build-%COMP%\students.jar
)

if "%COMP%" == "clie-rmiiiop" (
    gradle clean build -Dclie=rmiiiop & java -jar %ROOT_PATH%\build-%COMP%\students.jar
)
