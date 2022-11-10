# GDFR-App
An Android application to have access and manage data for the GDFR (Geometry Dash FRance) Community

## What can you do with it ?

You can actually do 2 things :

- See players' data by searching a player in the app
- Add a player in the database

## How does it work ?

Do you have Android Studio ? Perfect, you call run the application

Do you have Spring Boot installed ? Perfect, you can run the API to get all the data !!

Do you have a SQL database ? Incredible, you will be able to load all the players' data into a database

You don't have one of the software said before ? Ouch... because the application will depend on that to work properly. Just install all the softwares said before in order to make the app work efficiently

## Ok, I have all of this, what do have to do next ?

So, to make all the things work, you have to :

- Make the database work and import the SQL file in the folder "sql". It will load all the players' data in the database. Be careful to use a specific user for the access :
    - User : Atlas
    - Mot de passe : GeometryDashFR2021!
- Then, you have to make the API work, so you just have to open the folder "gdfr-api" in IntelliJ for instance (a Java Editor), and execute de main of GdfrApiApplication class, (with SpinngBoot installed) and the API will work
- Finally, you can open the application in Android Studio, and make the app work

## I have done it, and it work !!! Thanks ^^

You welcome my dear, but make sure everything work properly and that you have no errors. If you do, please contact me by email : <mail> lucio.luraschi@eleve.isep.fr </mail>

## That is all ?

Oh, I almost forgot an important detail : the version of the applications
- Spring Boot : v.2.7.5
- Android : Android Pie (v9)
